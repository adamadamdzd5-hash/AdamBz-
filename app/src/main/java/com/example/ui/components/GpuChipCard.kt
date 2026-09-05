package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GpuChip
import com.example.data.model.PerformanceTier
import com.example.ui.theme.GeoAmber
import com.example.ui.theme.GeoBorder
import com.example.ui.theme.GeoCard
import com.example.ui.theme.GeoCoral
import com.example.ui.theme.GeoMint
import com.example.ui.theme.GeoOnAmber
import com.example.ui.theme.GeoOnCoral
import com.example.ui.theme.GeoOnMint
import com.example.ui.theme.GeoOnPrimaryContainer
import com.example.ui.theme.GeoOnSky
import com.example.ui.theme.GeoPrimary
import com.example.ui.theme.GeoPrimaryContainer
import com.example.ui.theme.GeoSky
import com.example.ui.theme.GeoSurface
import com.example.ui.theme.GeoTextMuted
import com.example.ui.theme.GeoTextPrimary
import com.example.ui.theme.GeoTextSecondary
import com.example.ui.util.LocalIsEnglish

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GpuChipCard(
    chip: GpuChip,
    isBookmarked: Boolean,
    onCardClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onCompareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isEnglish = LocalIsEnglish.current
    val (tierBg, tierOnBg) = when (chip.tier) {
        PerformanceTier.TIER_S_PLUS -> GeoCoral to GeoOnCoral
        PerformanceTier.TIER_S -> GeoPrimaryContainer to GeoOnPrimaryContainer
        PerformanceTier.TIER_A -> GeoSky to GeoOnSky
        PerformanceTier.TIER_B -> GeoMint to GeoOnMint
        PerformanceTier.TIER_C -> GeoAmber to GeoOnAmber
        PerformanceTier.TIER_D -> GeoSurface to GeoTextSecondary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GeoCard),
        border = BorderStroke(1.dp, GeoBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top Row: Brand & Year + Tier Badge & Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = GeoPrimaryContainer,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = chip.brand.displayNameEn,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = GeoOnPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "${chip.releaseYear}",
                        style = MaterialTheme.typography.labelSmall,
                        color = GeoTextSecondary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Tier Badge Pill
                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = tierBg
                    ) {
                        Text(
                            text = if (isEnglish) chip.tier.tierNameEn else chip.tier.tierNameAr,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = tierOnBg,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isEnglish) "Bookmark" else "حفظ في المفضلة",
                            tint = if (isBookmarked) GeoPrimary else GeoTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Geometric Balanced Item Row: 48dp Icon Box + Names + Node
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Geometric Icon Container (w-12 h-12 rounded-2xl)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = tierBg,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = null,
                            tint = tierOnBg,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chip.socName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "GPU: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = GeoTextSecondary
                        )
                        Text(
                            text = chip.gpuName,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = GeoPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Node tag (e.g. 4nm) in bold GeoPrimary
                Text(
                    text = chip.processNode.split(" ").firstOrNull() ?: chip.processNode,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = GeoPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Performance Bar & Metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = GeoPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (chip.gflops >= 1000) {
                            String.format("%.2f TFLOPS", chip.gflops / 1000.0)
                        } else {
                            "${chip.gflops.toInt()} GFLOPS"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary
                    )
                }

                Text(
                    text = "${chip.gpuClockMhz} MHz",
                    style = MaterialTheme.typography.labelMedium,
                    color = GeoTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Relative Progress bar
            val progress = (chip.gflops / 5500.0).coerceIn(0.01, 1.0).toFloat()
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(percent = 50)),
                color = GeoPrimary,
                trackColor = GeoSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Feature tags & Devices
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (chip.hasRayTracing) {
                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = GeoMint
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = GeoOnMint,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = if (isEnglish) "Ray Tracing RT" else "تتبع أشعة RT",
                                style = MaterialTheme.typography.labelSmall,
                                color = GeoOnMint,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = GeoSurface
                ) {
                    Text(
                        text = if (isEnglish) chip.gpuFamily.displayNameEn else chip.gpuFamily.familyArabic,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = GeoTextSecondary,
                        fontSize = 10.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = GeoSurface
                ) {
                    Text(
                        text = "Vulkan ${chip.vulkanVersion}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = GeoTextSecondary,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Popular devices preview
            Text(
                text = if (isEnglish) "Devices: ${chip.popularDevices}" else "الهواتف: ${chip.popularDevices}",
                style = MaterialTheme.typography.bodySmall,
                color = GeoTextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
