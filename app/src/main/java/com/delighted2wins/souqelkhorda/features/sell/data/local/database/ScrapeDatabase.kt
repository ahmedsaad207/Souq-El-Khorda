package com.delighted2wins.souqelkhorda.features.sell.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delighted2wins.souqelkhorda.features.sell.data.local.dao.ScrapDao
import com.delighted2wins.souqelkhorda.features.sell.data.local.entities.ScrapDatabase

@Database(
    entities = [ScrapDatabase::class],
    version = 1,
    exportSchema = false
)
abstract class ScrapeDatabase : RoomDatabase(){
    abstract fun scrapDao(): ScrapDao
}