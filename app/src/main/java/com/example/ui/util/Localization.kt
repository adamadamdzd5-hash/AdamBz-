package com.example.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.example.data.model.GpuFamily
import com.example.data.model.PerformanceTier
import com.example.data.model.SocBrand
import com.example.data.repository.SortOption
import com.example.ui.NavigationTab

val LocalIsEnglish = compositionLocalOf { false }

object AppStrings {
    @Composable
    fun appTitle(): String = "AdamBz-GPU"

    @Composable
    fun appSubtitle(totalCount: Int): String = if (LocalIsEnglish.current) {
        "$totalCount Chips • Database"
    } else {
        "$totalCount شريحة • قاعدة بيانات"
    }

    // Tabs
    @Composable
    fun tabTitle(tab: NavigationTab): String = if (LocalIsEnglish.current) tab.titleEn else tab.titleAr

    // Catalog Screen
    @Composable
    fun catalogTitle(): String = if (LocalIsEnglish.current) "Processor Specifications" else "مواصفات المعالجات"

    @Composable
    fun catalogSubtitle(totalCount: Int): String = if (LocalIsEnglish.current) {
        "Database of $totalCount mobile SoCs & GPUs"
    } else {
        "قاعدة بيانات لـ $totalCount شريحة معالج ورسوميات"
    }

    @Composable
    fun searchPlaceholder(): String = if (LocalIsEnglish.current) {
        "Search SoC, GPU, or Brand..."
    } else {
        "بحث عن معالج، كارت شاشة، أو شركة..."
    }

    @Composable
    fun evolutionBannerTitle(): String = if (LocalIsEnglish.current) {
        "Brand Evolution: First & Latest SoCs"
    } else {
        "مسيرة الشركات: أول وآخر معالج"
    }

    @Composable
    fun evolutionBannerSub(): String = if (LocalIsEnglish.current) {
        "Explore the first and latest SoC & GPU of each brand"
    } else {
        "استعرض أول وآخر معالج وكارت شاشة لكل شركة"
    }

    @Composable
    fun sortTooltip(): String = if (LocalIsEnglish.current) "Sort" else "ترتيب"

    @Composable
    fun all(): String = if (LocalIsEnglish.current) "All" else "الكل"

    @Composable
    fun rayTracing(): String = if (LocalIsEnglish.current) "Ray Tracing" else "تتبع الأشعة"

    @Composable
    fun resetFilters(): String = if (LocalIsEnglish.current) "Reset Filters" else "إعادة ضبط الفلاتر"

    @Composable
    fun chipsCount(count: Int): String = if (LocalIsEnglish.current) "$count Chips" else "$count شريحة"

    @Composable
    fun noResults(): String = if (LocalIsEnglish.current) "No matching chips found" else "لم يتم العثور على شرائح مطابقة"

    @Composable
    fun noResultsSub(): String = if (LocalIsEnglish.current) "Try changing search terms or filters" else "جرب تغيير مصطلحات البحث أو ضبط الفلاتر"

    // Sort Options
    @Composable
    fun sortLabel(option: SortOption): String = if (LocalIsEnglish.current) option.titleEn else option.titleAr

    // Detail Bottom Sheet
    @Composable
    fun cpuSpecs(): String = if (LocalIsEnglish.current) "CPU Specifications" else "المعالج المركزي (CPU)"

    @Composable
    fun gpuSpecs(): String = if (LocalIsEnglish.current) "GPU Specifications" else "معالج الرسوميات (GPU)"

    @Composable
    fun memoryAndRelease(): String = if (LocalIsEnglish.current) "Memory & Release" else "الذاكرة وتاريخ الإصدار"

    @Composable
    fun keyDevices(): String = if (LocalIsEnglish.current) "Key Powered Devices" else "أبرز الهواتف المزودة بهذا المعالج"

    @Composable
    fun addToCompare(): String = if (LocalIsEnglish.current) "Add to Compare" else "إضافة للمقارنة"

    @Composable
    fun close(): String = if (LocalIsEnglish.current) "Close" else "إغلاق"

    @Composable
    fun clockSpeed(): String = if (LocalIsEnglish.current) "Clock Speed" else "التردد"

    @Composable
    fun architecture(): String = if (LocalIsEnglish.current) "Architecture" else "المعمارية"

    @Composable
    fun cores(): String = if (LocalIsEnglish.current) "Cores" else "الأنوية"

    @Composable
    fun computePower(): String = if (LocalIsEnglish.current) "Compute Power" else "قوة الحوسبة"

    @Composable
    fun shaders(): String = if (LocalIsEnglish.current) "Shaders" else "وحدات التظليل"

    @Composable
    fun rayTracingStatus(supported: Boolean): String = if (LocalIsEnglish.current) {
        if (supported) "Hardware Accelerated" else "Not Supported"
    } else {
        if (supported) "مدعوم عتادياً" else "غير مدعوم"
    }

    // Evolution Screen
    @Composable
    fun evolutionTitle(): String = if (LocalIsEnglish.current) "Brand Evolution: First vs Latest" else "مسيرة الشركات: أول وآخر معالج"

    @Composable
    fun evolutionSubtitle(): String = if (LocalIsEnglish.current) {
        "A historical comparison between each brand's first smartphone SoC and its latest flagship powerhouse"
    } else {
        "مقارنة تاريخية شاملة بين الانطلاقة الأولى وأحدث قمة تكنولوجية لكل شركة مصنعة"
    }

    @Composable
    fun firstSoc(): String = if (LocalIsEnglish.current) "First SoC" else "أول معالج"

    @Composable
    fun latestSoc(): String = if (LocalIsEnglish.current) "Latest SoC" else "أحدث معالج"

    @Composable
    fun performanceLeap(): String = if (LocalIsEnglish.current) "Performance Leap" else "طفرة الأداء"

    @Composable
    fun processShrink(): String = if (LocalIsEnglish.current) "Process Shrink" else "انكماش التصنيع"

    @Composable
    fun compareBoth(): String = if (LocalIsEnglish.current) "Compare First vs Latest" else "مقارنة مباشرة بين الاثنين"

    // Leaderboard Screen
    @Composable
    fun leaderboardTitle(): String = if (LocalIsEnglish.current) "GPU Performance Ranking" else "ترتيب أقوى كروت الشاشة للهواتف"

    @Composable
    fun leaderboardSubtitle(): String = if (LocalIsEnglish.current) {
        "Ranked descending by raw graphical compute power (GFLOPS)"
    } else {
        "مرتبة تنازلياً حسب قوة الحوسبة الرسومية الخام (GFLOPS)"
    }

    // Compare Screen
    @Composable
    fun compareTitle(): String = if (LocalIsEnglish.current) "Mobile SoC Comparison" else "مقارنة معالجات الهواتف"

    @Composable
    fun compareSubtitle(): String = if (LocalIsEnglish.current) {
        "Head-to-head architectural and graphical specification battle"
    } else {
        "مقارنة وجهاً لوجه لأدق التفاصيل العتادية والمعمارية الرسومية"
    }

    @Composable
    fun winner(): String = if (LocalIsEnglish.current) "Winner" else "الفائز"

    @Composable
    fun tie(): String = if (LocalIsEnglish.current) "Tie" else "تعادل"

    @Composable
    fun swap(): String = if (LocalIsEnglish.current) "Swap" else "تبديل"

    // Favorites Screen
    @Composable
    fun favoritesTitle(): String = if (LocalIsEnglish.current) "Bookmarked Chips" else "الشرائح المحفوظة"

    @Composable
    fun favoritesSubtitle(): String = if (LocalIsEnglish.current) {
        "Your saved list of mobile processors and GPUs"
    } else {
        "قائمتك الخاصة بالمعالجات وكروت الشاشة المحفوظة"
    }

    @Composable
    fun noFavorites(): String = if (LocalIsEnglish.current) "No bookmarked chips yet" else "لا توجد شرائح في المفضلة بعد"

    @Composable
    fun noFavoritesSub(): String = if (LocalIsEnglish.current) {
        "Tap the bookmark icon on any chip to save it here"
    } else {
        "اضغط على رمز الإشارة المرجعية لحفظ المعالجات هنا للوصول السريع"
    }

    // Language & Mode Buttons
    @Composable
    fun darkModeTooltip(isDark: Boolean): String = if (LocalIsEnglish.current) {
        if (isDark) "Switch to Light Mode" else "Switch to Dark Mode"
    } else {
        if (isDark) "التبديل إلى الوضع الفاتح" else "التبديل إلى الوضع الداكن"
    }

    @Composable
    fun languageTooltip(): String = if (LocalIsEnglish.current) "تبديل إلى العربية" else "Switch to English"
}
