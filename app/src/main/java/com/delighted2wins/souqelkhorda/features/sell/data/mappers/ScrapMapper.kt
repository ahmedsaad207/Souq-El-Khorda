package com.delighted2wins.souqelkhorda.features.sell.data.mappers

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.local.entities.ScrapDatabase

fun ScrapDatabase.toDomain(): Scrap {
    return Scrap(
        category = category,
        unit = unit,
        amount = amount,
        description = description,
        images = images
    )
}

fun Scrap.toEntity(): ScrapDatabase {
    return ScrapDatabase(
        category = category,
        unit = unit,
        amount = amount,
        description = description,
        images = images
    )
}