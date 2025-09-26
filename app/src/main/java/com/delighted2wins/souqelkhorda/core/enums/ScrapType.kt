package com.delighted2wins.souqelkhorda.core.enums

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import com.delighted2wins.souqelkhorda.R

enum class ScrapType(@SuppressLint("SupportAnnotationUsage") @StringRes val labelRes: Int) {
    Aluminum(R.string.aluminum),
    Paper(R.string.paper),
    Glass(R.string.glass),
    Plastic(R.string.plastic),
    CustomScrap(R.string.custom_item);

    fun getLabel(context: Context): String {
        return context.getString(labelRes)
    }
}