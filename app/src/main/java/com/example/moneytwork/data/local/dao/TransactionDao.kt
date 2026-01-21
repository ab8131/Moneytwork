package com.example.moneytwork.data.local.dao

import androidx.room.*
import com.example.moneytwork.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE assetId = :assetId ORDER BY timestamp DESC")
    fun getTransactionsByAsset(assetId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE assetType = :assetType ORDER BY timestamp DESC")
    fun getTransactionsByType(assetType: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Query("DELETE FROM transactions")
    suspend fun clearAllTransactions()
}

