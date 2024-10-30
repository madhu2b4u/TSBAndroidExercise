package co.nz.tsb.interview.bankrecmatchmaker.transactions.domain

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TransactionUseCaseImpl @Inject constructor(private val repository: TransactionRepository) :
    TransactionUseCase {

    override fun getTransactions(): List<Transaction> {
        return repository.transactions()
    }
}