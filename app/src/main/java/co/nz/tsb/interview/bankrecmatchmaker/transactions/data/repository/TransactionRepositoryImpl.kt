package co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor() : TransactionRepository {
    override fun transactions(): List<Transaction> {
        return getTransactionList()
    }
}

private fun getTransactionList() = listOf(
    Transaction(
        UUID.randomUUID().toString(),
        "City Limousines",
        "30 Aug",
        249.00f,
        "Sales Invoice"
    ),
    Transaction(
        UUID.randomUUID().toString(),
        "Ridgeway University",
        "12 Sep",
        618.50f,
        "Sales Invoice"
    ),
    Transaction(UUID.randomUUID().toString(), "Cube Land", "22 Sep", 495.00f, "Sales Invoice"),
    Transaction(UUID.randomUUID().toString(), "Bayside Club", "23 Sep", 234.00f, "Sales Invoice"),
    Transaction(UUID.randomUUID().toString(), "SMART Agency", "12 Sep", 250f, "Sales Invoice"),
    Transaction(UUID.randomUUID().toString(), "PowerDirect", "11 Sep", 108.60f, "Sales Invoice"),
    Transaction(UUID.randomUUID().toString(), "PC Complete", "17 Sep", 216.99f, "Sales Invoice"),
    Transaction(UUID.randomUUID().toString(), "Restaurant", "17 Sep", 315.5f, "Bill"),
    Transaction(UUID.randomUUID().toString(), "Supermarket", "18 Sep", 215.99f, "Bill"),
    Transaction(
        UUID.randomUUID().toString(),
        "Truxton Properties",
        "17 Sep",
        181.25f,
        "Sales Invoice"
    ),
    Transaction(
        UUID.randomUUID().toString(),
        "MCO Cleaning Services",
        "17 Sep",
        170.50f,
        "Sales Invoice"
    ),
    Transaction(UUID.randomUUID().toString(), "Gateway Motors", "18 Sep", 411.35f, "Sales Invoice")
)

