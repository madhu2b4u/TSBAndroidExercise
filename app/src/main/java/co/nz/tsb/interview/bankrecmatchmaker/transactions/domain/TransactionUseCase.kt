package co.nz.tsb.interview.bankrecmatchmaker.transactions.domain

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction

interface TransactionUseCase {

    fun getTransactions(): List<Transaction>
}