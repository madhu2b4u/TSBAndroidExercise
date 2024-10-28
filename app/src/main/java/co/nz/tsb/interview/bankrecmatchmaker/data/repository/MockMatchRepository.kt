package co.nz.tsb.interview.bankrecmatchmaker.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.data.model.MatchItem
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockMatchRepository @Inject constructor() : MatchRepository {
  override suspend fun getMatchItems(): List<MatchItem> = listOf(
    MatchItem(
      UUID.randomUUID().toString(),
      "City Limousines",
      "30 Aug",
      249.00f,
      "Sales Invoice"
    ),
    MatchItem(
      UUID.randomUUID().toString(),
      "Ridgeway University",
      "12 Sep",
      618.50f,
      "Sales Invoice"
    ),
    MatchItem(UUID.randomUUID().toString(), "Cube Land", "22 Sep", 495.00f, "Sales Invoice"),
    MatchItem(UUID.randomUUID().toString(), "Bayside Club", "23 Sep", 234.00f, "Sales Invoice"),
    MatchItem(UUID.randomUUID().toString(), "SMART Agency", "12 Sep", 250f, "Sales Invoice"),
    MatchItem(UUID.randomUUID().toString(), "PowerDirect", "11 Sep", 108.60f, "Sales Invoice"),
    MatchItem(UUID.randomUUID().toString(), "PC Complete", "17 Sep", 216.99f, "Sales Invoice"),
    MatchItem(
      UUID.randomUUID().toString(),
      "Truxton Properties",
      "17 Sep",
      181.25f,
      "Sales Invoice"
    ),
    MatchItem(
      UUID.randomUUID().toString(),
      "MCO Cleaning Services",
      "17 Sep",
      170.50f,
      "Sales Invoice"
    ),
    MatchItem(UUID.randomUUID().toString(), "Gateway Motors", "18 Sep", 411.35f, "Sales Invoice")
  )
}