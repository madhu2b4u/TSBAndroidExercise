package co.nz.tsb.interview.bankrecmatchmaker.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository

class MockMatchRepositoryForSuccessTest : TransactionRepository {
  private val mockItems = listOf(
    Transaction("1", "City Limousines", "30 Aug", 249.00f, "Sales Invoice", isChecked = false),
    Transaction("2", "Ridgeway University", "12 Sep", 618.50f, "Sales Invoice", isChecked = false),
    Transaction("3", "Cube Land", "22 Sep", 495.00f, "Sales Invoice", isChecked = false)
  )
  
  override fun transactions(): List<Transaction> = mockItems
}
