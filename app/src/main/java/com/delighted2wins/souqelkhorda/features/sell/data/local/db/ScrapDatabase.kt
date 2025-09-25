package com.delighted2wins.souqelkhorda.features.sell.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.delighted2wins.souqelkhorda.features.sell.data.model.ScrapEntity

@Database(
    entities = [ScrapEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ScrapDatabase : RoomDatabase(){
    abstract fun scrapDao(): ScrapDao
}