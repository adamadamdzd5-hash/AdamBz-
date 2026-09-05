package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GpuChip
import com.example.ui.theme.GeoAmber
import com.example.ui.theme.GeoBorder
import com.example.ui.theme.GeoCard
import com.example.ui.theme.GeoMint
import com.example.ui.theme.GeoOnAmber
import com.example.ui.theme.GeoOnPrimaryContainer
import com.example.ui.theme.GeoPrimary
import com.example.ui.theme.GeoPrimaryContainer
import com.example.ui.theme.GeoSurface
import com.example.ui.theme.GeoTextMuted
import com.example.ui.theme.GeoTextPrimary
import com.example.ui.theme.GeoTextSecondary
import com.example.ui.util.AppStrings

@Composable
fun LeaderboardScreen(
    rankedChips: List<GpuChip>,
    onChipClick: (GpuChip) -> Unit,
    modifier: Modifier = Modifier
) {
    val topScore = rankedChips.firstOrNull()?.gflops ?: 5500.0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Geometric Balance Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GeoSurface)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(GeoPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = AppStrings.leaderboardTitle(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = AppStrings.leaderboardSubtitle(),
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(rankedChips, key = { _, chip -> chip.id }) { index, chip ->
                val rank = index + 1

                val (badgeBg, badgeText) = when (rank) {
                    1 -> Color(0xFFFFD700) to Color(0xFF3E2D00) // Gold
                    2 -> Color(0xFFC0C0C0) to Color(0xFF2C2C2C) // Silver
                    3 -> Color(0xFFE0A878) to Color(0xFF422100) // Bronze
                    else -> GeoPrimaryContainer to GeoOnPrimaryContainer
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChipClick(chip) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = GeoCard),
                    border = BorderStroke(1.dp, if (rank <= 3) badgeBg else GeoBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rank Number Badge
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = badgeBg,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "$rank",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = badgeText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        // Chip info & Progress
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = chip.socName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = GeoTextPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = if (chip.gflops >= 1000) {
                                        String.format("%.2f TFLOPS", chip.gflops / 1000.0)
                                    } else {
                                        "${chip.gflops.toInt()} GFLOPS"
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = GeoPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = chip.gpuName,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = GeoPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "• ${chip.processNode.split(" ").first()}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GeoTextMuted
                                )
                                if (chip.hasRayTracing) {
                                    Icon(
                                        imageVector = Icons.Default.FlashOn,
                                        contentDescription = "RT",
                                        tint = GeoMint,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Relative ratio against top score
                            val progress = (chip.gflops / topScore).coerceIn(0.01, 1.0).toFloat()
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(percent = 50)),
                                color = GeoPrimary,
                                trackColor = GeoSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
