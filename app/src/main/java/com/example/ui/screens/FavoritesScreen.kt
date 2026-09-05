package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.GpuChip
import com.example.ui.components.GpuChipCard
import com.example.ui.theme.GeoPrimary
import com.example.ui.theme.GeoSurface
import com.example.ui.theme.GeoTextMuted
import com.example.ui.theme.GeoTextPrimary
import com.example.ui.theme.GeoTextSecondary
import com.example.ui.util.AppStrings
import com.example.ui.util.LocalIsEnglish

@Composable
fun FavoritesScreen(
    bookmarkedChips: List<GpuChip>,
    onChipClick: (GpuChip) -> Unit,
    onBookmarkClick: (String) -> Unit,
    onCompareClick: (GpuChip) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEnglish = LocalIsEnglish.current

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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(GeoPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = AppStrings.favoritesTitle(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = if (isEnglish) {
                            "Your personal list of saved processors and GPUs (${bookmarkedChips.size})"
                        } else {
                            "قائمتك الخاصة بالمعالجات وكروت الشاشة التي اخترت متابعتها (${bookmarkedChips.size})"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (bookmarkedChips.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = GeoTextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = AppStrings.noFavorites(),
                        style = MaterialTheme.typography.titleMedium,
                        color = GeoTextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = AppStrings.noFavoritesSub(),
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextMuted
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookmarkedChips, key = { it.id }) { chip ->
                    GpuChipCard(
                        chip = chip,
                        isBookmarked = true,
                        onCardClick = { onChipClick(chip) },
                        onBookmarkClick = { onBookmarkClick(chip.id) },
                        onCompareClick = { onCompareClick(chip) }
                    )
                }
            }
        }
    }
}
