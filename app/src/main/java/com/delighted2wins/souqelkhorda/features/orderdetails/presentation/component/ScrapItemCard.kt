package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.delighted2wins.souqelkhorda.core.enums.MeasurementType
import com.delighted2wins.souqelkhorda.core.enums.ScrapType
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.presentation.components.ScrapItem

@Composable
fun ScrapItemCard(
    scrap: Scrap,
) {
    val scrapType = ScrapType.fromCategory(scrap.category)
    val categoryLabel = scrapType.getLabel(context = LocalContext.current)
    val measurementEnum = MeasurementType.entries.firstOrNull { it.name.equals(scrap.unit, true) }
    val measurementLabel = measurementEnum?.getLabel(LocalContext.current) ?: ""
    ScrapItem(
        scrap = scrap,
        isMyOrderScreen = true,
        isLoading = null,
        description = scrap.description,
        urls = scrap.images
    )
}
