package com.delighted2wins.souqelkhorda.core.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delighted2wins.souqelkhorda.core.model.Scrap

@Database(
    entities = [Scrap::class],
    version = 1,
    exportSchema = false
)
abstract class ScrapeDatabase : RoomDatabase(){
    abstract fun scrapDao(): ScrapDao
}