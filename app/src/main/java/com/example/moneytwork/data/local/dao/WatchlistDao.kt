package com.example.moneytwork.data.local.dao

import androidx.room.*
import com.example.moneytwork.data.local.entity.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun getWatchlist(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE coinId = :coinId")
    suspend fun getWatchlistItem(coinId: String): WatchlistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchlist(item: WatchlistEntity)

    @Delete
    suspend fun removeFromWatchlist(item: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE coinId = :coinId")
    suspend fun removeFromWatchlistById(coinId: String)
}

