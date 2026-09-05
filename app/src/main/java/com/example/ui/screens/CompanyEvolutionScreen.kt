package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.datasource.CompanyEvolutionData
import com.example.data.datasource.CompanyEvolutionItem
import com.example.data.datasource.ProcessorSpec
import com.example.data.model.GpuChip
import com.example.data.model.SocBrand
import com.example.ui.theme.GeoAmber
import com.example.ui.theme.GeoBackground
import com.example.ui.theme.GeoBorder
import com.example.ui.theme.GeoCard
import com.example.ui.theme.GeoMint
import com.example.ui.theme.GeoOnMint
import com.example.ui.theme.GeoPrimary
import com.example.ui.theme.GeoPrimaryContainer
import com.example.ui.theme.GeoSurface
import com.example.ui.theme.GeoTextPrimary
import com.example.ui.theme.GeoTextSecondary
import com.example.ui.util.AppStrings
import com.example.ui.util.LocalIsEnglish

@Composable
fun CompanyEvolutionScreen(
    onChipClick: (GpuChip) -> Unit,
    onCompareBoth: (chipId1: String, chipId2: String) -> Unit,
    allChips: List<GpuChip>,
    modifier: Modifier = Modifier
) {
    var selectedBrandFilter by remember { mutableStateOf<SocBrand?>(null) }
    val isEnglish = LocalIsEnglish.current

    val companies = remember(selectedBrandFilter) {
        if (selectedBrandFilter == null) {
            CompanyEvolutionData.companies
        } else {
            CompanyEvolutionData.companies.filter { it.brand == selectedBrandFilter }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(GeoBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Banner
        item {
            EvolutionHeaderBanner(totalCompanies = CompanyEvolutionData.companies.size, isEnglish = isEnglish)
        }

        // Brand Filter Chips
        item {
            CompanyFilterRow(
                selectedBrand = selectedBrandFilter,
                onBrandSelected = { selectedBrandFilter = it },
                isEnglish = isEnglish
            )
        }

        // Company Evolution Cards
        items(companies, key = { it.id }) { companyItem ->
            CompanyEvolutionCard(
                company = companyItem,
                onCompareBoth = onCompareBoth,
                onChipClick = onChipClick,
                allChips = allChips,
                isEnglish = isEnglish
            )
        }

        // Footer note
        item {
            Text(
                text = if (isEnglish) {
                    "All historical data and specifications are authentic and match the commercial releases worldwide."
                } else {
                    "جميع البيانات التاريخية والمواصفات موثقة ومطابقة للأجهزة الأصلية التي أُطلقت بالأسواق عالمياً."
                },
                style = MaterialTheme.typography.bodySmall,
                color = GeoTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }
    }
}

@Composable
private fun EvolutionHeaderBanner(totalCompanies: Int, isEnglish: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GeoSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GeoBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(GeoPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = null,
                        tint = GeoPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isEnglish) "Brand Evolution: First & Latest SoCs" else "مسيرة الشركات: أول وآخر معالج",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = if (isEnglish) "$totalCompanies Global Mobile Chipmakers" else "$totalCompanies شركة عالمية في هواتف المحمول",
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextSecondary
                    )
                }
            }

            Text(
                text = if (isEnglish) {
                    "A comprehensive historical guide tracing each brand's very first smartphone SoC and its GPU, contrasted with its newest technological powerhouse in modern phones."
                } else {
                    "دليل تاريخي شامل يستعرض من كل شركة أول معالج هواتف ومواصفات المعالج وكارت الشاشة الخاص به، مقابل أحدث معالج وكارت شاشة في هواتف اليوم، مع مقارنة قفزة التطور التاريخية."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = GeoTextPrimary.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun CompanyFilterRow(
    selectedBrand: SocBrand?,
    onBrandSelected: (SocBrand?) -> Unit,
    isEnglish: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedBrand == null,
            onClick = { onBrandSelected(null) },
            label = {
                Text(
                    if (isEnglish) "All Brands (${CompanyEvolutionData.companies.size})"
                    else "جميع الشركات (${CompanyEvolutionData.companies.size})"
                )
            },
            shape = RoundedCornerShape(50),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = GeoPrimary,
                selectedLabelColor = Color.White
            )
        )

        CompanyEvolutionData.companies.map { it.brand }.distinct().forEach { brand ->
            val brandItem = CompanyEvolutionData.companies.find { it.brand == brand }
            FilterChip(
                selected = selectedBrand == brand,
                onClick = { onBrandSelected(if (selectedBrand == brand) null else brand) },
                label = {
                    Text(
                        if (isEnglish) (brandItem?.companyNameEn ?: brand.displayNameEn)
                        else (brandItem?.companyNameAr ?: brand.displayNameAr)
                    )
                },
                shape = RoundedCornerShape(50),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GeoPrimary,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}

@Composable
private fun CompanyEvolutionCard(
    company: CompanyEvolutionItem,
    onCompareBoth: (chipId1: String, chipId2: String) -> Unit,
    onChipClick: (GpuChip) -> Unit,
    allChips: List<GpuChip>,
    isEnglish: Boolean
) {
    val chipFirst = remember(company.firstProcessor.chipId) {
        allChips.find { it.id == company.firstProcessor.chipId }
    }
    val chipLatest = remember(company.latestProcessor.chipId) {
        allChips.find { it.id == company.latestProcessor.chipId }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GeoSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GeoBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Company Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(company.brandColorHex).copy(alpha = 0.12f))
                            .border(1.dp, Color(company.brandColorHex).copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = null,
                            tint = Color(company.brandColorHex),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = if (isEnglish) company.companyNameEn else company.companyNameAr,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GeoTextPrimary
                        )
                        Text(
                            text = if (isEnglish) company.companyNameAr else company.companyNameEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = GeoTextSecondary
                        )
                    }
                }

                // Active Era Badge
                Surface(
                    shape = RoundedCornerShape(50),
                    color = GeoCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, GeoBorder)
                ) {
                    Text(
                        text = company.activeYears,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = GeoTextPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            // Company Role Summary
            Text(
                text = company.companyRoleAr,
                style = MaterialTheme.typography.bodyMedium,
                color = GeoTextSecondary,
                lineHeight = 19.sp
            )

            // Evolution Metrics Leap Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Leap GFLOPS
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    color = GeoMint.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GeoMint.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = GeoMint, modifier = Modifier.size(16.dp))
                            Text(
                                text = if (isEnglish) "GPU Leap" else "قفزة الأداء الرسومي",
                                fontSize = 10.sp,
                                color = GeoMint,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(company.gflopsMultiplier, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GeoTextPrimary)
                    }
                }

                // Process Node Shrink
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    color = GeoPrimaryContainer.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GeoPrimary.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.FlashOn, contentDescription = null, tint = GeoPrimary, modifier = Modifier.size(16.dp))
                            Text(
                                text = if (isEnglish) "Node Shrink" else "انكماش دقة التصنيع",
                                fontSize = 10.sp,
                                color = GeoPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(company.processNodeShrink, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = GeoTextPrimary)
                    }
                }
            }

            // Dual Evolution Cards: First vs Latest
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // First Processor Card
                ProcessorSpecCard(
                    isFirst = true,
                    spec = company.firstProcessor,
                    matchingChip = chipFirst,
                    onChipClick = onChipClick,
                    isEnglish = isEnglish
                )

                // Arrow Divider
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = GeoBorder)
                    Surface(
                        shape = CircleShape,
                        color = GeoPrimary,
                        border = androidx.compose.foundation.BorderStroke(2.dp, GeoCard),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Latest Processor Card
                ProcessorSpecCard(
                    isFirst = false,
                    spec = company.latestProcessor,
                    matchingChip = chipLatest,
                    onChipClick = onChipClick,
                    isEnglish = isEnglish
                )
            }

            // Historical Story
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = GeoCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, GeoBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = GeoPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = if (isEnglish) "Journey of Evolution" else "مسيرة التحول والتطور",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = GeoPrimary
                        )
                    }

                    Text(
                        text = company.evolutionStoryAr,
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }

            // Action: Compare both chips in CompareScreen
            Button(
                onClick = {
                    onCompareBoth(company.firstProcessor.chipId, company.latestProcessor.chipId)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GeoPrimaryContainer,
                    contentColor = GeoPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CompareArrows,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEnglish) "Head-to-head comparison in Compare table" else "مقارنة أول وآخر معالج وجهاً لوجه في جدول المقارنة",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProcessorSpecCard(
    isFirst: Boolean,
    spec: ProcessorSpec,
    matchingChip: GpuChip?,
    onChipClick: (GpuChip) -> Unit,
    isEnglish: Boolean
) {
    val containerBg = if (isFirst) GeoAmber.copy(alpha = 0.12f) else GeoPrimaryContainer.copy(alpha = 0.35f)
    val borderColor = if (isFirst) GeoAmber.copy(alpha = 0.5f) else GeoPrimary.copy(alpha = 0.4f)
    val badgeBg = if (isFirst) GeoAmber else GeoPrimary
    val badgeTitle = if (isFirst) {
        if (isEnglish) "First Mobile SoC (${spec.releaseYear})" else "أول معالج للهواتف (${spec.releaseYear})"
    } else {
        if (isEnglish) "Latest Mobile SoC (${spec.releaseYear})" else "أحدث معالج للهواتف (${spec.releaseYear})"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = containerBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Card Title + Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = badgeBg
                ) {
                    Text(
                        text = badgeTitle,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(50),
                    color = GeoCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
                ) {
                    Text(
                        text = spec.processNode,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = GeoTextPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // SoC Name
            Text(
                text = spec.socName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GeoTextPrimary
            )

            // Section 1: CPU Specs
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GeoCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = GeoPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isEnglish) "CPU Specs:" else "مواصفات المعالج (CPU):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = GeoTextPrimary
                        )
                    }

                    Text(
                        text = if (isEnglish) "• Cores: ${spec.cpuCores}" else "• الأنوية: ${spec.cpuCores}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = if (isEnglish) "• Arch: ${spec.cpuArchitecture}" else "• المعمارية: ${spec.cpuArchitecture}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = if (isEnglish) "• Max Clock: ${spec.cpuMaxClock}" else "• أقصى تردد: ${spec.cpuMaxClock}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextPrimary
                    )
                }
            }

            // Section 2: GPU Specs
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = GeoCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = null,
                            tint = if (isFirst) GeoAmber else GeoPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isEnglish) "GPU Specs:" else "كارت الشاشة الرسومي (GPU):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = GeoTextPrimary
                        )
                    }

                    Text(
                        text = if (isEnglish) "• GPU: ${spec.gpuName}" else "• اسم الكارت: ${spec.gpuName}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = GeoTextPrimary
                    )
                    Text(
                        text = if (isEnglish) "• Arch: ${spec.gpuArchitecture}" else "• معمارية الرسوميات: ${spec.gpuArchitecture}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextPrimary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEnglish) "• Clock: ${spec.gpuClock}" else "• تردد الكارت: ${spec.gpuClock}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GeoTextPrimary
                        )
                        Text(
                            text = if (isEnglish) "• Power: ${spec.gflopsDisplay}" else "• القوة: ${spec.gflopsDisplay}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = if (spec.gflops > 1000) GeoMint else GeoTextPrimary
                        )
                    }

                    if (spec.hasRayTracing) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = GeoAmber,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = if (isEnglish) "Hardware Ray Tracing Supported" else "دعم تسريع تتبع الأشعة العتادي (Hardware Ray Tracing)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GeoAmber
                            )
                        }
                    }
                }
            }

            // Notable phones & Highlights
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = GeoTextSecondary,
                        modifier = Modifier
                            .size(15.dp)
                            .padding(top = 2.dp)
                    )
                    Text(
                        text = if (isEnglish) "Key Devices: ${spec.notablePhones}" else "أبرز الهواتف: ${spec.notablePhones}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GeoTextSecondary,
                        lineHeight = 17.sp
                    )
                }

                Text(
                    text = "💡 ${spec.keyHighlightAr}",
                    style = MaterialTheme.typography.bodySmall,
                    color = GeoTextPrimary.copy(alpha = 0.85f),
                    lineHeight = 17.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // If chip is in repository, provide a direct button to view bottom sheet
            if (matchingChip != null) {
                OutlinedButton(
                    onClick = { onChipClick(matchingChip) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GeoPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GeoPrimary.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = if (isEnglish) "View Full GPU Specs for ${spec.gpuName}" else "عرض كامل المواصفات التفصيلية لكارت ${spec.gpuName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
