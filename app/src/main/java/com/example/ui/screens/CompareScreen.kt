package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GpuChip
import com.example.ui.components.ChipSelectionDialog
import com.example.ui.theme.GeoBorder
import com.example.ui.theme.GeoCard
import com.example.ui.theme.GeoOnPrimaryContainer
import com.example.ui.theme.GeoPrimary
import com.example.ui.theme.GeoPrimaryContainer
import com.example.ui.theme.GeoSurface
import com.example.ui.theme.GeoTextMuted
import com.example.ui.theme.GeoTextPrimary
import com.example.ui.theme.GeoTextSecondary
import com.example.ui.util.AppStrings
import com.example.ui.util.LocalIsEnglish
import kotlin.math.abs

@Composable
fun CompareScreen(
    chip1: GpuChip?,
    chip2: GpuChip?,
    allChips: List<GpuChip>,
    onSelectChip1: (GpuChip) -> Unit,
    onSelectChip2: (GpuChip) -> Unit,
    onSwapChips: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isSelectingForSlot by remember { mutableStateOf<Int?>(null) }
    val isEnglish = LocalIsEnglish.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Header
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
                        imageVector = Icons.Default.CompareArrows,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = AppStrings.compareTitle(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = AppStrings.compareSubtitle(),
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Dual Selection Headers with Swap button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Slot 1
                ChipSelectSlot(
                    chip = chip1,
                    label = if (isEnglish) "First Chip" else "الشريحة الأولى",
                    onClick = { isSelectingForSlot = 1 },
                    modifier = Modifier.weight(1f),
                    accentColor = GeoPrimary,
                    isEnglish = isEnglish
                )

                // Swap Button
                IconButton(
                    onClick = onSwapChips,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(36.dp)
                        .background(GeoSurface, CircleShape)
                        .border(1.dp, GeoBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = AppStrings.swap(),
                        tint = GeoPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Slot 2
                ChipSelectSlot(
                    chip = chip2,
                    label = if (isEnglish) "Second Chip" else "الشريحة الثانية",
                    onClick = { isSelectingForSlot = 2 },
                    modifier = Modifier.weight(1f),
                    accentColor = GeoPrimary,
                    isEnglish = isEnglish
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (chip1 != null && chip2 != null) {
                // Winner Banner for GFLOPS
                val diffGflops = chip1.gflops - chip2.gflops
                val percentDiff = if (chip2.gflops > 0) {
                    ((abs(diffGflops) / chip2.gflops) * 100).toInt()
                } else 0

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = GeoPrimaryContainer),
                    border = BorderStroke(1.dp, GeoBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val winnerName = if (diffGflops >= 0) chip1.socName else chip2.socName

                        Text(
                            text = if (isEnglish) "Graphical Compute Power Analysis" else "تحليل فارق القوة الرسومية",
                            style = MaterialTheme.typography.labelMedium,
                            color = GeoOnPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isEnglish) {
                                "$winnerName is faster in graphics computation by ~$percentDiff%"
                            } else {
                                "$winnerName أسرع في الحوسبة الرسومية بنسبة تقارب $percentDiff%"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GeoOnPrimaryContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Comparison Metrics Table
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = GeoCard),
                    border = BorderStroke(1.dp, GeoBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ComparisonMetricRow(
                            title = if (isEnglish) "Raw Compute Power (GFLOPS)" else "القوة الرسومية الخام (GFLOPS)",
                            val1 = "${chip1.gflops.toInt()} GFLOPS",
                            val2 = "${chip2.gflops.toInt()} GFLOPS",
                            winner = if (chip1.gflops > chip2.gflops) 1 else if (chip2.gflops > chip1.gflops) 2 else 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "GPU Clock Frequency" else "تردد كارت الشاشة",
                            val1 = "${chip1.gpuClockMhz} MHz",
                            val2 = "${chip2.gpuClockMhz} MHz",
                            winner = if (chip1.gpuClockMhz > chip2.gpuClockMhz) 1 else if (chip2.gpuClockMhz > chip1.gpuClockMhz) 2 else 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Hardware Ray Tracing" else "تتبع الأشعة العتادي (Ray Tracing)",
                            val1 = if (chip1.hasRayTracing) (if (isEnglish) "Supported ⚡" else "مدعوم ⚡") else (if (isEnglish) "Not Supported" else "غير متوفر"),
                            val2 = if (chip2.hasRayTracing) (if (isEnglish) "Supported ⚡" else "مدعوم ⚡") else (if (isEnglish) "Not Supported" else "غير متوفر"),
                            winner = if (chip1.hasRayTracing && !chip2.hasRayTracing) 1 else if (!chip1.hasRayTracing && chip2.hasRayTracing) 2 else 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "GPU Architecture" else "المعمارية الرسومية",
                            val1 = chip1.architecture,
                            val2 = chip2.architecture,
                            winner = 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Process Node" else "دقة التصنيع (Process)",
                            val1 = chip1.processNode,
                            val2 = chip2.processNode,
                            winner = 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Execution Units / ALUs" else "وحدات التنفيذ / ALUs",
                            val1 = chip1.executionUnits,
                            val2 = chip2.executionUnits,
                            winner = 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Performance Tier" else "فئة الأداء",
                            val1 = if (isEnglish) chip1.tier.tierNameEn else chip1.tier.tierNameAr,
                            val2 = if (isEnglish) chip2.tier.tierNameEn else chip2.tier.tierNameAr,
                            winner = 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Vulkan API" else "إصدار Vulkan API",
                            val1 = chip1.vulkanVersion,
                            val2 = chip2.vulkanVersion,
                            winner = 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Memory Bandwidth" else "نطاق الذاكرة",
                            val1 = chip1.memoryBandwidth,
                            val2 = chip2.memoryBandwidth,
                            winner = 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                        HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))

                        ComparisonMetricRow(
                            title = if (isEnglish) "Release Year" else "سنة الإصدار",
                            val1 = "${chip1.releaseYear}",
                            val2 = "${chip2.releaseYear}",
                            winner = if (chip1.releaseYear > chip2.releaseYear) 1 else if (chip2.releaseYear > chip1.releaseYear) 2 else 0,
                            vsText = if (isEnglish) "vs" else "مقابل"
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isEnglish) "Please select two chips to begin comparison" else "يرجى اختيار شريحتين للبدء بالمقارنة",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GeoTextMuted
                    )
                }
            }
        }
    }

    // Modal chip selector dialog
    if (isSelectingForSlot != null) {
        ChipSelectionDialog(
            allChips = allChips,
            onSelectChip = { selected ->
                if (isSelectingForSlot == 1) {
                    onSelectChip1(selected)
                } else {
                    onSelectChip2(selected)
                }
            },
            onDismiss = { isSelectingForSlot = null }
        )
    }
}

@Composable
private fun ChipSelectSlot(
    chip: GpuChip?,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color,
    isEnglish: Boolean
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GeoCard),
        border = BorderStroke(1.dp, GeoBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = label, style = MaterialTheme.typography.labelSmall, color = GeoTextSecondary)
                Icon(
                    imageVector = Icons.Default.ChangeCircle,
                    contentDescription = "Change",
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (chip != null) {
                Text(
                    text = chip.socName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = GeoTextPrimary,
                    maxLines = 1
                )
                Text(
                    text = chip.gpuName,
                    style = MaterialTheme.typography.bodySmall,
                    color = accentColor,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            } else {
                Text(
                    text = if (isEnglish) "Tap to select" else "انقر لاختيار معالج",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GeoPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ComparisonMetricRow(
    title: String,
    val1: String,
    val2: String,
    winner: Int, // 0: none, 1: slot1, 2: slot2
    vsText: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = GeoTextMuted,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = val1,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (winner == 1) FontWeight.Bold else FontWeight.Normal,
                color = if (winner == 1) GeoPrimary else GeoTextPrimary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )

            Text(
                text = vsText,
                style = MaterialTheme.typography.labelSmall,
                color = GeoTextMuted,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = val2,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (winner == 2) FontWeight.Bold else FontWeight.Normal,
                color = if (winner == 2) GeoPrimary else GeoTextPrimary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}
