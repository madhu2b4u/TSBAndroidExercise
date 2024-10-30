package co.nz.tsb.interview.bankrecmatchmaker.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCase

class FakeTransactionUseCase : TransactionUseCase {
    override fun getTransactions(): List<Transaction> {
        return listOf(
            Transaction("1", "City Limousines", "30 Aug", 249.00f, "Sales Invoice", isChecked = false),
            Transaction("2", "Ridgeway University", "12 Sep", 618.50f, "Sales Invoice", isChecked = false),
            Transaction("3", "Cube Land", "22 Sep", 495.00f, "Sales Invoice", isChecked = false)
        )
    }
}