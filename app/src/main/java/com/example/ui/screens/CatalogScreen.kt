package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GpuChip
import com.example.data.model.GpuFamily
import com.example.data.model.PerformanceTier
import com.example.data.model.SocBrand
import com.example.data.repository.SortOption
import com.example.ui.GpuUiState
import com.example.ui.components.GpuChipCard
import com.example.ui.theme.GeoAmber
import com.example.ui.theme.GeoBackground
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
import com.example.ui.util.AppStrings
import com.example.ui.util.LocalIsEnglish

@Composable
fun CatalogScreen(
    uiState: GpuUiState,
    onSearchChange: (String) -> Unit,
    onBrandSelected: (SocBrand?) -> Unit,
    onFamilySelected: (GpuFamily?) -> Unit,
    onTierSelected: (PerformanceTier?) -> Unit,
    onToggleRayTracing: () -> Unit,
    onSortChanged: (SortOption) -> Unit,
    onResetFilters: () -> Unit,
    onChipClick: (GpuChip) -> Unit,
    onBookmarkClick: (String) -> Unit,
    onCompareClick: (GpuChip) -> Unit,
    onNavigateToEvolution: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showSortMenu by remember { mutableStateOf(false) }
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
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rounded Capsule Search Bar
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(AppStrings.searchPlaceholder(), color = GeoTextMuted, fontSize = 12.sp)
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = GeoTextSecondary, modifier = Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = GeoTextMuted, modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(percent = 50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = GeoCard,
                        unfocusedContainerColor = GeoCard,
                        focusedBorderColor = GeoPrimary,
                        unfocusedBorderColor = GeoBorder,
                        focusedTextColor = GeoTextPrimary,
                        unfocusedTextColor = GeoTextPrimary
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Sort Dropdown Button
                Box {
                    IconButton(
                        onClick = { showSortMenu = true },
                        modifier = Modifier
                            .size(44.dp)
                            .background(GeoCard, CircleShape)
                            .border(1.dp, GeoBorder, CircleShape)
                    ) {
                        Icon(Icons.Default.Sort, contentDescription = AppStrings.sortTooltip(), tint = GeoPrimary)
                    }

                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false },
                        modifier = Modifier.background(GeoCard)
                    ) {
                        SortOption.values().forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = if (isEnglish) option.titleEn else option.titleAr,
                                        color = if (uiState.sortOption == option) GeoPrimary else GeoTextPrimary,
                                        fontWeight = if (uiState.sortOption == option) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    onSortChanged(option)
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Simplified Single Filter Row (English Brand names on the front)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = uiState.selectedBrand == null,
                onClick = { onBrandSelected(null) },
                shape = RoundedCornerShape(percent = 50),
                label = { Text(AppStrings.all(), fontSize = 12.sp, fontWeight = if (uiState.selectedBrand == null) FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GeoPrimary,
                    selectedLabelColor = Color.White,
                    containerColor = GeoPrimaryContainer,
                    labelColor = GeoOnPrimaryContainer
                ),
                border = null
            )

            // Brand Chips in English on the front interface
            SocBrand.values().forEach { brand ->
                val isSelected = uiState.selectedBrand == brand
                FilterChip(
                    selected = isSelected,
                    onClick = { onBrandSelected(brand) },
                    shape = RoundedCornerShape(percent = 50),
                    label = { Text(brand.displayNameEn, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GeoPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = GeoPrimaryContainer,
                        labelColor = GeoOnPrimaryContainer
                    ),
                    border = null
                )
            }

            // Ray Tracing Filter Chip
            FilterChip(
                selected = uiState.onlyRayTracing,
                onClick = onToggleRayTracing,
                shape = RoundedCornerShape(percent = 50),
                leadingIcon = {
                    Icon(
                        Icons.Default.FlashOn,
                        contentDescription = null,
                        tint = if (uiState.onlyRayTracing) GeoOnMint else GeoMint,
                        modifier = Modifier.size(14.dp)
                    )
                },
                label = { Text(AppStrings.rayTracing(), fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GeoMint,
                    selectedLabelColor = GeoOnMint,
                    containerColor = GeoCard,
                    labelColor = GeoTextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = uiState.onlyRayTracing,
                    borderColor = GeoBorder
                )
            )
        }

        // Active count & Reset Filters bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEnglish) {
                    "Showing ${uiState.chips.size} of ${uiState.totalCount} chips"
                } else {
                    "النتائج المعروضة: ${uiState.chips.size} من أصل ${uiState.totalCount} شريحة"
                },
                style = MaterialTheme.typography.labelSmall,
                color = GeoTextMuted
            )

            val hasFilters = uiState.searchQuery.isNotEmpty() ||
                    uiState.selectedBrand != null ||
                    uiState.selectedFamily != null ||
                    uiState.selectedTier != null ||
                    uiState.onlyRayTracing

            if (hasFilters) {
                TextButton(
                    onClick = onResetFilters,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(AppStrings.resetFilters(), style = MaterialTheme.typography.labelSmall, color = GeoPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Chips LazyColumn
        if (uiState.chips.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = null,
                        tint = GeoTextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = AppStrings.noResults(),
                        style = MaterialTheme.typography.titleMedium,
                        color = GeoTextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = AppStrings.noResultsSub(),
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
                items(uiState.chips, key = { it.id }) { chip ->
                    GpuChipCard(
                        chip = chip,
                        isBookmarked = uiState.bookmarkedChipIds.contains(chip.id),
                        onCardClick = { onChipClick(chip) },
                        onBookmarkClick = { onBookmarkClick(chip.id) },
                        onCompareClick = { onCompareClick(chip) }
                    )
                }
            }
        }
    }
}
