package co.nz.tsb.interview.bankrecmatchmaker.data.model

data class MatchItem(
  val id: String,
  val paidTo: String,
  val transactionDate: String,
  val total: Float,
  val docType: String,
  val isChecked: Boolean = false
)