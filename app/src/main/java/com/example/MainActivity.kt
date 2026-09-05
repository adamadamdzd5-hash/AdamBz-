package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.GpuViewModel
import com.example.ui.NavigationTab
import com.example.ui.components.GpuDetailBottomSheet
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.CompanyEvolutionScreen
import com.example.ui.screens.CompareScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.theme.GeoBorder
import com.example.ui.theme.GeoCard
import com.example.ui.theme.GeoPrimary
import com.example.ui.theme.GeoPrimaryContainer
import com.example.ui.theme.GeoSurface
import com.example.ui.theme.GeoTextPrimary
import com.example.ui.theme.GeoTextSecondary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.util.AppStrings
import com.example.ui.util.LocalIsEnglish

class MainActivity : ComponentActivity() {

    private val viewModel: GpuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()
            MyApplicationTheme(darkTheme = uiState.isDarkMode) {
                val layoutDirection = if (uiState.isEnglish) LayoutDirection.Ltr else LayoutDirection.Rtl
                CompositionLocalProvider(
                    LocalLayoutDirection provides layoutDirection,
                    LocalIsEnglish provides uiState.isEnglish
                ) {
                    GpuSpecsApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun GpuSpecsApp(viewModel: GpuViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Surface(
                color = GeoSurface,
                border = BorderStroke(1.dp, GeoBorder.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // App Identity
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(GeoPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Memory,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = AppStrings.appTitle(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GeoTextPrimary
                            )
                            Text(
                                text = AppStrings.appSubtitle(uiState.totalCount),
                                style = MaterialTheme.typography.labelSmall,
                                color = GeoTextSecondary
                            )
                        }
                    }

                    // Quick Control Action Buttons: Language & Dark Mode
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Language Toggle Button (زر لغة انجليزي / عربي)
                        Surface(
                            onClick = { viewModel.toggleLanguage() },
                            shape = RoundedCornerShape(50),
                            color = if (uiState.isEnglish) GeoPrimaryContainer else GeoCard,
                            border = BorderStroke(1.dp, if (uiState.isEnglish) GeoPrimary else GeoBorder),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = AppStrings.languageTooltip(),
                                    tint = if (uiState.isEnglish) GeoPrimary else GeoTextPrimary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = if (uiState.isEnglish) "EN" else "عربي",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (uiState.isEnglish) GeoPrimary else GeoTextPrimary
                                )
                            }
                        }

                        // Dark Mode Toggle Button (زر دارك مود)
                        Surface(
                            onClick = { viewModel.toggleDarkMode() },
                            shape = CircleShape,
                            color = if (uiState.isDarkMode) GeoPrimaryContainer else GeoCard,
                            border = BorderStroke(1.dp, if (uiState.isDarkMode) GeoPrimary else GeoBorder),
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = if (uiState.isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = AppStrings.darkModeTooltip(uiState.isDarkMode),
                                    tint = if (uiState.isDarkMode) GeoPrimary else GeoTextPrimary,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = GeoSurface,
                contentColor = GeoTextPrimary,
                tonalElevation = 4.dp
            ) {
                // Tab 1: Catalog
                NavigationBarItem(
                    selected = uiState.currentTab == NavigationTab.CATALOG,
                    onClick = { viewModel.setNavigationTab(NavigationTab.CATALOG) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = AppStrings.tabTitle(NavigationTab.CATALOG),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.tabTitle(NavigationTab.CATALOG),
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == NavigationTab.CATALOG) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GeoPrimary,
                        selectedTextColor = GeoPrimary,
                        indicatorColor = GeoPrimaryContainer,
                        unselectedIconColor = GeoTextSecondary,
                        unselectedTextColor = GeoTextSecondary
                    )
                )

                // Tab 2: Evolution
                NavigationBarItem(
                    selected = uiState.currentTab == NavigationTab.EVOLUTION,
                    onClick = { viewModel.setNavigationTab(NavigationTab.EVOLUTION) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = AppStrings.tabTitle(NavigationTab.EVOLUTION),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.tabTitle(NavigationTab.EVOLUTION),
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == NavigationTab.EVOLUTION) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GeoPrimary,
                        selectedTextColor = GeoPrimary,
                        indicatorColor = GeoPrimaryContainer,
                        unselectedIconColor = GeoTextSecondary,
                        unselectedTextColor = GeoTextSecondary
                    )
                )

                // Tab 3: Leaderboard
                NavigationBarItem(
                    selected = uiState.currentTab == NavigationTab.LEADERBOARD,
                    onClick = { viewModel.setNavigationTab(NavigationTab.LEADERBOARD) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = AppStrings.tabTitle(NavigationTab.LEADERBOARD),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.tabTitle(NavigationTab.LEADERBOARD),
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == NavigationTab.LEADERBOARD) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GeoPrimary,
                        selectedTextColor = GeoPrimary,
                        indicatorColor = GeoPrimaryContainer,
                        unselectedIconColor = GeoTextSecondary,
                        unselectedTextColor = GeoTextSecondary
                    )
                )

                // Tab 4: Compare
                NavigationBarItem(
                    selected = uiState.currentTab == NavigationTab.COMPARE,
                    onClick = { viewModel.setNavigationTab(NavigationTab.COMPARE) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CompareArrows,
                            contentDescription = AppStrings.tabTitle(NavigationTab.COMPARE),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.tabTitle(NavigationTab.COMPARE),
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == NavigationTab.COMPARE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GeoPrimary,
                        selectedTextColor = GeoPrimary,
                        indicatorColor = GeoPrimaryContainer,
                        unselectedIconColor = GeoTextSecondary,
                        unselectedTextColor = GeoTextSecondary
                    )
                )

                // Tab 5: Favorites
                NavigationBarItem(
                    selected = uiState.currentTab == NavigationTab.FAVORITES,
                    onClick = { viewModel.setNavigationTab(NavigationTab.FAVORITES) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = AppStrings.tabTitle(NavigationTab.FAVORITES),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.tabTitle(NavigationTab.FAVORITES),
                            fontSize = 11.sp,
                            fontWeight = if (uiState.currentTab == NavigationTab.FAVORITES) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GeoPrimary,
                        selectedTextColor = GeoPrimary,
                        indicatorColor = GeoPrimaryContainer,
                        unselectedIconColor = GeoTextSecondary,
                        unselectedTextColor = GeoTextSecondary
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                NavigationTab.CATALOG -> {
                    CatalogScreen(
                        uiState = uiState,
                        onSearchChange = viewModel::onSearchQueryChanged,
                        onBrandSelected = viewModel::onBrandSelected,
                        onFamilySelected = viewModel::onFamilySelected,
                        onTierSelected = viewModel::onTierSelected,
                        onToggleRayTracing = viewModel::toggleRayTracingFilter,
                        onSortChanged = viewModel::onSortOptionChanged,
                        onResetFilters = viewModel::resetFilters,
                        onChipClick = viewModel::selectChipForDetail,
                        onBookmarkClick = viewModel::toggleBookmark,
                        onCompareClick = { chip ->
                            viewModel.selectComparisonChip1(chip)
                            viewModel.setNavigationTab(NavigationTab.COMPARE)
                        },
                        onNavigateToEvolution = {
                            viewModel.setNavigationTab(NavigationTab.EVOLUTION)
                        }
                    )
                }

                NavigationTab.EVOLUTION -> {
                    CompanyEvolutionScreen(
                        onChipClick = viewModel::selectChipForDetail,
                        onCompareBoth = { id1, id2 ->
                            viewModel.compareTwoChips(id1, id2)
                        },
                        allChips = viewModel.getAllChipsRaw()
                    )
                }

                NavigationTab.LEADERBOARD -> {
                    LeaderboardScreen(
                        rankedChips = viewModel.getLeaderboardChips(),
                        onChipClick = viewModel::selectChipForDetail
                    )
                }

                NavigationTab.COMPARE -> {
                    CompareScreen(
                        chip1 = uiState.comparisonChip1,
                        chip2 = uiState.comparisonChip2,
                        allChips = viewModel.getAllChipsRaw(),
                        onSelectChip1 = viewModel::selectComparisonChip1,
                        onSelectChip2 = viewModel::selectComparisonChip2,
                        onSwapChips = {
                            val c1 = uiState.comparisonChip1
                            val c2 = uiState.comparisonChip2
                            if (c1 != null && c2 != null) {
                                viewModel.selectComparisonChip1(c2)
                                viewModel.selectComparisonChip2(c1)
                            }
                        }
                    )
                }

                NavigationTab.FAVORITES -> {
                    FavoritesScreen(
                        bookmarkedChips = viewModel.getBookmarkedChips(),
                        onChipClick = viewModel::selectChipForDetail,
                        onBookmarkClick = viewModel::toggleBookmark,
                        onCompareClick = { chip ->
                            viewModel.selectComparisonChip1(chip)
                            viewModel.setNavigationTab(NavigationTab.COMPARE)
                        }
                    )
                }
            }

            // Detail Bottom Sheet
            uiState.selectedChipForDetail?.let { chip ->
                GpuDetailBottomSheet(
                    chip = chip,
                    isBookmarked = uiState.bookmarkedChipIds.contains(chip.id),
                    onDismiss = { viewModel.selectChipForDetail(null) },
                    onToggleBookmark = { viewModel.toggleBookmark(chip.id) },
                    onCompareAsSlot1 = {
                        viewModel.selectComparisonChip1(chip)
                        viewModel.selectChipForDetail(null)
                        viewModel.setNavigationTab(NavigationTab.COMPARE)
                    },
                    onCompareAsSlot2 = {
                        viewModel.selectComparisonChip2(chip)
                        viewModel.selectChipForDetail(null)
                        viewModel.setNavigationTab(NavigationTab.COMPARE)
                    }
                )
            }
        }
    }
}
