package com.delighted2wins.souqelkhorda.features.sell.data.mapper

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.model.ScrapEntity

fun ScrapEntity.toDomain(): Scrap {
    return Scrap(
        id = id,
        category = category,
        unit = unit,
        amount = amount,
        description = description,
        images = images
    )
}

fun Scrap.toEntity(): ScrapEntity {
    return ScrapEntity(
        category = category,
        unit = unit,
        amount = amount,
        description = description,
        images = images
    )
}