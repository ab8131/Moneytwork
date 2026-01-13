package com.example.moneytwork.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytwork.data.local.dao.CoinDao
import com.example.moneytwork.data.local.dao.WatchlistDao
import com.example.moneytwork.data.local.entity.CoinEntity
import com.example.moneytwork.data.local.entity.WatchlistEntity

@Database(
    entities = [CoinEntity::class, WatchlistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoneytworkDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
    abstract val watchlistDao: WatchlistDao
}

