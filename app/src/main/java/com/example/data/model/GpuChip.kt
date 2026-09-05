package com.example.data.model

enum class SocBrand(val displayNameAr: String, val displayNameEn: String) {
    QUALCOMM("كوالكوم سنابدراجون", "Qualcomm Snapdragon"),
    MEDIATEK("ميدياتك ديمنسيتي وهيلو", "MediaTek Dimensity / Helio"),
    APPLE("آبل سلسلة A و M", "Apple A / M Series"),
    SAMSUNG("سامسونج إكسينوس", "Samsung Exynos"),
    GOOGLE("جوجل تنسور", "Google Tensor"),
    HISILICON("هواوي هاي سيليكون كيرين", "HiSilicon Kirin"),
    UNISOC("يونيسوك سبريدترم", "UNISOC"),
    NVIDIA("نيفيديا تيجرا", "Nvidia Tegra"),
    TEXAS_INSTRUMENTS("تكساس إنسترومنتس OMAP", "Texas Instruments OMAP"),
    INTEL("إنتل أتوم", "Intel Atom Mobile"),
    ST_ERICSSON("إس تي إريكسون نوفاثور", "ST-Ericsson NovaThor"),
    LEADCORE("ليدكاور", "Leadcore"),
    ROCKCHIP("روك تشيب", "Rockchip"),
    ALLWINNER("أول وينر", "Allwinner"),
    BROADCOM("برودكوم فيديو كور", "Broadcom VideoCore"),
    MARVELL("مارفيل أرمادا", "Marvell ARMADA"),
    OTHER("شركات أخرى", "Other Vendors");

    val displayName: String get() = displayNameAr
}

enum class GpuFamily(val displayNameAr: String, val displayNameEn: String) {
    ADRENO("أدرينو (Adreno)", "Qualcomm Adreno"),
    MALI("مالي (ARM Mali)", "ARM Mali"),
    IMMORTALIS("إمورتاليس (ARM Immortalis)", "ARM Immortalis"),
    APPLE_GPU("معالج آبل الرسومي (Apple GPU)", "Apple Custom GPU"),
    XCLIPSE("إكسليبس سامسونج (Xclipse AMD RDNA)", "Samsung Xclipse (AMD RDNA)"),
    POWERVR("باور في آر (PowerVR)", "Imagination PowerVR"),
    MALEOON("ماليون هواوي (Maleoon)", "Huawei Maleoon"),
    NVIDIA_MAXWELL("نيفيديا ماكسويل (GeForce Maxwell)", "Nvidia Maxwell"),
    NVIDIA_KEPLER("نيفيديا كيبلر (GeForce Kepler)", "Nvidia Kepler"),
    GEFORCE("نيفيديا جيفورس (GeForce Tegra)", "Nvidia GeForce"),
    OTHER("معمارية مخصصة أخرى", "Other Architecture");

    val familyArabic: String get() = displayNameAr
    val displayName: String get() = displayNameEn
}

enum class PerformanceTier(val tierLabel: String, val tierNameAr: String, val tierNameEn: String) {
    TIER_S_PLUS("S+", "فئة النخبة الخارقة", "Ultra Flagship"),
    TIER_S("S", "فئة رائدة عليا", "Flagship"),
    TIER_A("A", "فئة متوسطة عليا", "Upper Mid-Range"),
    TIER_B("B", "فئة متوسطة", "Mid-Range"),
    TIER_C("C", "فئة اقتصادية", "Budget"),
    TIER_D("D", "فئة كلاسيكية / ابتدائية", "Entry-Level");

    val titleAr: String get() = tierNameAr
    val code: String get() = tierLabel
}

data class GpuChip(
    val id: String,
    val socName: String,
    val brand: SocBrand,
    val gpuName: String,
    val gpuFamily: GpuFamily,
    val architecture: String,
    val processNode: String,
    val gpuClockMhz: Int,
    val executionUnits: String,
    val gflops: Double,
    val hasRayTracing: Boolean,
    val vulkanVersion: String,
    val openGlEsVersion: String,
    val memoryType: String,
    val memoryBandwidth: String,
    val maxDisplay: String,
    val tier: PerformanceTier,
    val releaseYear: Int,
    val popularDevices: String,
    val descriptionAr: String = ""
)
