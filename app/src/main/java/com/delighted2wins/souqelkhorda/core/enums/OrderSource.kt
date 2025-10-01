package com.delighted2wins.souqelkhorda.core.enums

import androidx.annotation.StringRes
import com.delighted2wins.souqelkhorda.R

enum class OrderSource(@StringRes val labelRes: Int) {
    MARKET(R.string.order_source_market),
    COMPANY(R.string.order_source_company),
    SALES(R.string.sales_label),
    OFFERS(R.string.offers_label)
}
