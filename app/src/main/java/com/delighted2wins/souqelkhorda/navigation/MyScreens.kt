package com.delighted2wins.souqelkhorda.navigation

import android.os.Parcelable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data object DirectSaleScreen:NavKey
@Serializable
data object NearestBuyersScreen:NavKey
@Serializable
data object MarketScreen:NavKey
@Serializable
data object SplashScreen:NavKey

@Serializable
data object LoginScreen:NavKey

@Serializable
data object SignUpScreen:NavKey

@Serializable
data class AddItemKey(val category: String):NavKey

@Parcelize
data class OrderDetailsKey(val order : ScrapOrder) : NavKey, Parcelable

