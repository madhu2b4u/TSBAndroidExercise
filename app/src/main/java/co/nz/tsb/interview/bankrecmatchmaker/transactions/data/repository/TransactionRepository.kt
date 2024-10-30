package co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction

interface TransactionRepository {
    fun transactions(): List<Transaction>
}
