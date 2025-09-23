package com.delighted2wins.souqelkhorda.features.sell.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delighted2wins.souqelkhorda.features.sell.data.model.ScrapEntity

@Database(
    entities = [ScrapEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ScrapDatabase : RoomDatabase(){
    abstract fun scrapDao(): ScrapDao
}