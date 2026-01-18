package com.example.moneytwork.domain.usecase

import com.example.moneytwork.domain.model.Transaction
import com.example.moneytwork.domain.repository.PortfolioRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: PortfolioRepository
) {
    suspend operator fun invoke(transaction: Transaction): Long {
        return repository.addTransaction(transaction)
    }
}

