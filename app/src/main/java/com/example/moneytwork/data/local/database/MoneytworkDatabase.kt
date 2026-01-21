package com.example.moneytwork.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytwork.data.local.dao.CoinDao
import com.example.moneytwork.data.local.dao.StockDao
import com.example.moneytwork.data.local.dao.TransactionDao
import com.example.moneytwork.data.local.dao.WatchlistDao
import com.example.moneytwork.data.local.entity.CoinEntity
import com.example.moneytwork.data.local.entity.StockEntity
import com.example.moneytwork.data.local.entity.TransactionEntity
import com.example.moneytwork.data.local.entity.WatchlistEntity

@Database(
    entities = [CoinEntity::class, WatchlistEntity::class, TransactionEntity::class, StockEntity::class],
    version = 4,
    exportSchema = false
)
abstract class MoneytworkDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
    abstract val watchlistDao: WatchlistDao
    abstract val transactionDao: TransactionDao
    abstract val stockDao: StockDao
}

