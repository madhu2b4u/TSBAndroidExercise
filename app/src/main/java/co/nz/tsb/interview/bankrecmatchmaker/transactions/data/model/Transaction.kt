package co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model

data class Transaction(
    val id: String,
    val paidTo: String,
    val transactionDate: String,
    val total: Float,
    val docType: String,
    val isChecked: Boolean = false
)