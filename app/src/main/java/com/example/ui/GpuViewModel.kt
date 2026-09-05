package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.model.GpuChip
import com.example.data.model.GpuFamily
import com.example.data.model.PerformanceTier
import com.example.data.model.SocBrand
import com.example.data.repository.GpuChipRepository
import com.example.data.repository.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GpuUiState(
    val chips: List<GpuChip> = emptyList(),
    val totalCount: Int = 0,
    val searchQuery: String = "",
    val selectedBrand: SocBrand? = null,
    val selectedFamily: GpuFamily? = null,
    val selectedTier: PerformanceTier? = null,
    val onlyRayTracing: Boolean = false,
    val sortOption: SortOption = SortOption.PERFORMANCE_DESC,
    val selectedChipForDetail: GpuChip? = null,
    val comparisonChip1: GpuChip? = null,
    val comparisonChip2: GpuChip? = null,
    val bookmarkedChipIds: Set<String> = emptySet(),
    val currentTab: NavigationTab = NavigationTab.CATALOG,
    val isDarkMode: Boolean = false,
    val isEnglish: Boolean = false
)

enum class NavigationTab(val titleAr: String, val titleEn: String) {
    CATALOG("الشرائح", "Catalog"),
    EVOLUTION("أول وآخر معالج", "Evolution"),
    LEADERBOARD("الترتيب", "Ranking"),
    COMPARE("المقارنة", "Compare"),
    FAVORITES("المفضلة", "Favorites")
}

class GpuViewModel(
    private val repository: GpuChipRepository = GpuChipRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GpuUiState())
    val uiState: StateFlow<GpuUiState> = _uiState.asStateFlow()

    init {
        val all = repository.getAllChips()
        val defaultChip1 = all.find { it.id == "qc_8_elite" } ?: all.firstOrNull()
        val defaultChip2 = all.find { it.id == "mtk_dim_9400" } ?: all.getOrNull(1)

        _uiState.update {
            it.copy(
                totalCount = repository.totalCount,
                chips = repository.filterChips(),
                comparisonChip1 = defaultChip1,
                comparisonChip2 = defaultChip2
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    fun onBrandSelected(brand: SocBrand?) {
        _uiState.update {
            it.copy(selectedBrand = if (it.selectedBrand == brand) null else brand)
        }
        applyFilters()
    }

    fun onFamilySelected(family: GpuFamily?) {
        _uiState.update {
            it.copy(selectedFamily = if (it.selectedFamily == family) null else family)
        }
        applyFilters()
    }

    fun onTierSelected(tier: PerformanceTier?) {
        _uiState.update {
            it.copy(selectedTier = if (it.selectedTier == tier) null else tier)
        }
        applyFilters()
    }

    fun toggleRayTracingFilter() {
        _uiState.update { it.copy(onlyRayTracing = !it.onlyRayTracing) }
        applyFilters()
    }

    fun onSortOptionChanged(sortOption: SortOption) {
        _uiState.update { it.copy(sortOption = sortOption) }
        applyFilters()
    }

    fun resetFilters() {
        _uiState.update {
            it.copy(
                searchQuery = "",
                selectedBrand = null,
                selectedFamily = null,
                selectedTier = null,
                onlyRayTracing = false,
                sortOption = SortOption.PERFORMANCE_DESC
            )
        }
        applyFilters()
    }

    private fun applyFilters() {
        val current = _uiState.value
        val filtered = repository.filterChips(
            query = current.searchQuery,
            selectedBrand = current.selectedBrand,
            selectedFamily = current.selectedFamily,
            selectedTier = current.selectedTier,
            onlyRayTracing = current.onlyRayTracing,
            sortOption = current.sortOption
        )
        _uiState.update { it.copy(chips = filtered) }
    }

    fun selectChipForDetail(chip: GpuChip?) {
        _uiState.update { it.copy(selectedChipForDetail = chip) }
    }

    fun selectComparisonChip1(chip: GpuChip) {
        _uiState.update { it.copy(comparisonChip1 = chip) }
    }

    fun selectComparisonChip2(chip: GpuChip) {
        _uiState.update { it.copy(comparisonChip2 = chip) }
    }

    fun compareTwoChips(chipId1: String, chipId2: String) {
        val all = repository.getAllChips()
        val c1 = all.find { it.id == chipId1 } ?: all.firstOrNull()
        val c2 = all.find { it.id == chipId2 } ?: all.getOrNull(1)
        _uiState.update {
            it.copy(
                comparisonChip1 = c1,
                comparisonChip2 = c2,
                currentTab = NavigationTab.COMPARE
            )
        }
    }

    fun toggleBookmark(chipId: String) {
        _uiState.update { state ->
            val updated = if (state.bookmarkedChipIds.contains(chipId)) {
                state.bookmarkedChipIds - chipId
            } else {
                state.bookmarkedChipIds + chipId
            }
            state.copy(bookmarkedChipIds = updated)
        }
    }

    fun setNavigationTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun getLeaderboardChips(): List<GpuChip> {
        return repository.getAllChips().sortedByDescending { it.gflops }
    }

    fun getBookmarkedChips(): List<GpuChip> {
        val ids = _uiState.value.bookmarkedChipIds
        return repository.getAllChips().filter { ids.contains(it.id) }
    }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun toggleLanguage() {
        _uiState.update { it.copy(isEnglish = !it.isEnglish) }
    }

    fun getAllChipsRaw(): List<GpuChip> = repository.getAllChips()
}
