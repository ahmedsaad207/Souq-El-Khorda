package com.delighted2wins.souqelkhorda.core.enums

import android.content.Context
import com.delighted2wins.souqelkhorda.R

enum class MeasurementType(val labelRes: Int) {
    Weight(R.string.weight_kg),
    Pieces(R.string.pieces);

    fun getLabel(context: Context): String {
        return context.getString(labelRes)
    }
}