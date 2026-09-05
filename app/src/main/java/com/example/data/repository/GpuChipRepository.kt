package com.example.data.repository

import com.example.data.datasource.AppleChips
import com.example.data.datasource.ExtendedCatalogChips
import com.example.data.datasource.KirinUnisocChips
import com.example.data.datasource.MediaTekChipsPart1
import com.example.data.datasource.MediaTekChipsPart2
import com.example.data.datasource.QualcommChips
import com.example.data.datasource.QualcommChipsPart2
import com.example.data.datasource.SamsungGoogleChips
import com.example.data.datasource.VintageSpecialtyChips
import com.example.data.model.GpuChip
import com.example.data.model.GpuFamily
import com.example.data.model.PerformanceTier
import com.example.data.model.SocBrand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SortOption(val titleAr: String, val titleEn: String) {
    PERFORMANCE_DESC("الأعلى أداءً (GFLOPS)", "Highest Performance (GFLOPS)"),
    PERFORMANCE_ASC("الأقل أداءً (GFLOPS)", "Lowest Performance (GFLOPS)"),
    NEWEST("الأحدث إصداراً", "Newest Released"),
    OLDEST("الأقدم إصداراً", "Oldest Released"),
    CLOCK_SPEED("الأعلى تردد (MHz)", "Highest Clock (MHz)"),
    NAME_ASC("الاسم (أ-ي)", "Name (A-Z)")
}

class GpuChipRepository {

    private val _allChips: List<GpuChip> by lazy {
        val raw = listOf(
            QualcommChips.chips,
            QualcommChipsPart2.chips,
            MediaTekChipsPart1.chips,
            MediaTekChipsPart2.chips,
            SamsungGoogleChips.chips,
            AppleChips.chips,
            KirinUnisocChips.chips,
            VintageSpecialtyChips.chips,
            ExtendedCatalogChips.chips
        ).flatten()
        // Deduplicate by ID just in case
        raw.distinctBy { it.id }
    }

    val totalCount: Int
        get() = _allChips.size

    fun getAllChips(): List<GpuChip> = _allChips

    fun getChipById(id: String): GpuChip? = _allChips.find { it.id == id }

    fun filterChips(
        query: String = "",
        selectedBrand: SocBrand? = null,
        selectedFamily: GpuFamily? = null,
        selectedTier: PerformanceTier? = null,
        onlyRayTracing: Boolean = false,
        sortOption: SortOption = SortOption.PERFORMANCE_DESC
    ): List<GpuChip> {
        val trimmedQuery = query.trim().lowercase()

        return _allChips.filter { chip ->
            val matchesQuery = if (trimmedQuery.isEmpty()) {
                true
            } else {
                chip.socName.lowercase().contains(trimmedQuery) ||
                        chip.gpuName.lowercase().contains(trimmedQuery) ||
                        chip.architecture.lowercase().contains(trimmedQuery) ||
                        chip.popularDevices.lowercase().contains(trimmedQuery) ||
                        chip.brand.displayName.lowercase().contains(trimmedQuery) ||
                        chip.gpuFamily.familyArabic.lowercase().contains(trimmedQuery) ||
                        chip.descriptionAr.lowercase().contains(trimmedQuery)
            }

            val matchesBrand = selectedBrand == null || chip.brand == selectedBrand
            val matchesFamily = selectedFamily == null || chip.gpuFamily == selectedFamily
            val matchesTier = selectedTier == null || chip.tier == selectedTier
            val matchesRayTracing = !onlyRayTracing || chip.hasRayTracing

            matchesQuery && matchesBrand && matchesFamily && matchesTier && matchesRayTracing
        }.let { list ->
            when (sortOption) {
                SortOption.PERFORMANCE_DESC -> list.sortedByDescending { it.gflops }
                SortOption.PERFORMANCE_ASC -> list.sortedBy { it.gflops }
                SortOption.NEWEST -> list.sortedWith(compareByDescending<GpuChip> { it.releaseYear }.thenByDescending { it.gflops })
                SortOption.OLDEST -> list.sortedWith(compareBy<GpuChip> { it.releaseYear }.thenBy { it.gflops })
                SortOption.CLOCK_SPEED -> list.sortedByDescending { it.gpuClockMhz }
                SortOption.NAME_ASC -> list.sortedBy { it.socName }
            }
        }
    }
}
