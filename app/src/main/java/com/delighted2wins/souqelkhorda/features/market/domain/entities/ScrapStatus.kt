package com.delighted2wins.souqelkhorda.features.market.domain.entities

import androidx.compose.ui.graphics.Color

sealed class ScrapStatus(val labelAr: String, val labelEn: String, val color: Color) {
    object Waiting : ScrapStatus("في انتظار", "Waiting", Color(0xFFFFA000))
    object Available : ScrapStatus("متاح", "Available", Color(0xFF388E3C))
    object Reserved : ScrapStatus("محجوز", "Reserved", Color(0xFF1976D2))
    object Sold : ScrapStatus("تم البيع", "Sold", Color(0xFFD32F2F))
}