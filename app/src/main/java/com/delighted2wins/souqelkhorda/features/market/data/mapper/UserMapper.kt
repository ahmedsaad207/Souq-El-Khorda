package com.delighted2wins.souqelkhorda.features.market.data.mapper

import com.delighted2wins.souqelkhorda.core.model.MainUserDto
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

fun MainUserDto.toMarketUser(): MarketUser {
    return MarketUser(
        id = id.hashCode(),
        name = name,
        location = "$governorate, $address",
        imageUrl = userImage
    )
}
