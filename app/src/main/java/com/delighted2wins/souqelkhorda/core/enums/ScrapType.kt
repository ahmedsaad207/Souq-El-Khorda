package com.delighted2wins.souqelkhorda.core.enums

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.delighted2wins.souqelkhorda.R

enum class ScrapType(
    @SuppressLint("SupportAnnotationUsage") @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
    val tint: Color
) {
    Aluminum(R.string.aluminum, R.drawable.aluminum, Color(0xFF9E9E9E)),
    Paper(R.string.paper, R.drawable.paper, Color(0xFF4CAF50)),
    Glass(R.string.glass, R.drawable.glass, Color(0xFF2196F3)),
    Plastic(R.string.plastic, R.drawable.plastic, Color(0xFFFF9800)),
    CustomScrap(R.string.custom_item, R.drawable.custom, Color(0xFF9C27B0));

    fun getLabel(context: Context): String {
        return context.getString(labelRes)
    }
}