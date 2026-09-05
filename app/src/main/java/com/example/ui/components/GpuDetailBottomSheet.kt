package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpuDetailBottomSheet(
    chip: GpuChip?,
    isBookmarked: Boolean,
    onDismiss: () -> Unit,
    onToggleBookmark: (String) -> Unit,
    onCompareAsSlot1: (GpuChip) -> Unit,
    onCompareAsSlot2: (GpuChip) -> Unit
) {
    if (chip == null) return

    val isEnglish = LocalIsEnglish.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val (tierBg, tierOnBg) = when (chip.tier) {
        PerformanceTier.TIER_S_PLUS -> GeoCoral to GeoOnCoral
        PerformanceTier.TIER_S -> GeoPrimaryContainer to GeoOnPrimaryContainer
        PerformanceTier.TIER_A -> GeoSky to GeoOnSky
        PerformanceTier.TIER_B -> GeoMint to GeoOnMint
        PerformanceTier.TIER_C -> GeoAmber to GeoOnAmber
        PerformanceTier.TIER_D -> GeoSurface to GeoTextSecondary
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = GeoSurface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = {
            Surface(
                modifier = Modifier.padding(vertical = 12.dp),
                color = GeoBorder,
                shape = RoundedCornerShape(percent = 50)
            ) {
                Box(modifier = Modifier.size(width = 36.dp, height = 4.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 36.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = GeoPrimaryContainer
                    ) {
                        Text(
                            text = if (isEnglish) chip.brand.displayNameEn else chip.brand.displayName,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = GeoOnPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(percent = 50),
                        color = tierBg
                    ) {
                        Text(
                            text = if (isEnglish) "${chip.tier.tierNameEn} (${chip.tier.code})" else "${chip.tier.tierNameAr} (${chip.tier.code})",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = tierOnBg,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Row {
                    IconButton(
                        onClick = { onToggleBookmark(chip.id) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) GeoPrimary else GeoTextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = GeoTextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Processor & GPU Titles with Geometric Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = tierBg,
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = null,
                            tint = tierOnBg,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = chip.socName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = chip.gpuName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = GeoPrimary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Text(
                text = chip.descriptionAr,
                style = MaterialTheme.typography.bodyMedium,
                color = GeoTextSecondary,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Highlight Performance Banner
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GeoCard),
                border = BorderStroke(1.dp, GeoBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpecHighlight(
                        label = if (isEnglish) "Compute Power" else "القوة الرسومية",
                        value = if (chip.gflops >= 1000) String.format("%.2f TFLOPS", chip.gflops / 1000.0) else "${chip.gflops.toInt()} GFLOPS",
                        color = GeoPrimary
                    )
                    Box(modifier = Modifier.size(width = 1.dp, height = 36.dp).background(GeoBorder))
                    SpecHighlight(
                        label = if (isEnglish) "Core Clock" else "تردد النواة",
                        value = "${chip.gpuClockMhz} MHz",
                        color = GeoTextPrimary
                    )
                    Box(modifier = Modifier.size(width = 1.dp, height = 36.dp).background(GeoBorder))
                    SpecHighlight(
                        label = if (isEnglish) "Ray Tracing" else "تتبع الأشعة",
                        value = if (chip.hasRayTracing) (if (isEnglish) "Supported" else "مدعوم عتادياً") else (if (isEnglish) "Not Available" else "غير متوفر"),
                        color = if (chip.hasRayTracing) GeoMint else GeoTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (isEnglish) "Detailed Technical Specifications" else "المواصفات الفنية الدقيقة",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GeoTextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Detailed Spec Table
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GeoCard),
                border = BorderStroke(1.dp, GeoBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(label = if (isEnglish) "GPU Architecture" else "المعمارية الرسومية", value = chip.architecture)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Process Node" else "دقة التصنيع (المسبك)", value = chip.processNode)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Execution Units / ALUs" else "وحدات التنفيذ / ALUs", value = chip.executionUnits)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "GPU Family" else "عائلة كارت الشاشة", value = if (isEnglish) chip.gpuFamily.name else chip.gpuFamily.familyArabic)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Vulkan API" else "دعم Vulkan API", value = "v${chip.vulkanVersion}")
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "OpenGL ES" else "دعم OpenGL ES", value = "v${chip.openGlEsVersion}")
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Memory Type" else "نوع الذاكرة الموصى به", value = chip.memoryType)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Memory Bandwidth" else "نطاق الذاكرة الترددي", value = chip.memoryBandwidth)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Max Display" else "أقصى دقة شاشة مدعومة", value = chip.maxDisplay)
                    HorizontalDivider(color = GeoBorder, modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = if (isEnglish) "Release Year" else "سنة الإصدار للأسواق", value = "${chip.releaseYear}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Popular Devices Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GeoCard),
                border = BorderStroke(1.dp, GeoBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = GeoPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEnglish) "Key Devices Powered by this Chip:" else "أشهر الهواتف التي تعمل بهذه الشريحة:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = GeoTextSecondary
                        )
                        Text(
                            text = chip.popularDevices,
                            style = MaterialTheme.typography.bodyMedium,
                            color = GeoTextPrimary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Comparison Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        onCompareAsSlot1(chip)
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(percent = 50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GeoPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.CompareArrows, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isEnglish) "Set Slot 1" else "تعيين في المقارنة 1", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        onCompareAsSlot2(chip)
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(percent = 50),
                    border = BorderStroke(1.dp, GeoBorder)
                ) {
                    Text(if (isEnglish) "Set Slot 2" else "تعيين في المقارنة 2", fontWeight = FontWeight.Bold, color = GeoPrimary)
                }
            }
        }
    }
}

@Composable
private fun SpecHighlight(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = GeoTextMuted)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = GeoTextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = GeoTextPrimary
        )
    }
}
