package co.nz.tsb.interview.bankrecmatchmaker.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.data.model.MatchItem

class MockMatchRepositoryForSuccessTest : MatchRepository {
  private val mockItems = listOf(
    MatchItem("1", "Success Item 1", "01 Jan", 100f, "Success Description 1"),
    MatchItem("2", "Success Item 2", "02 Jan", 200f, "Success Description 2"),
    MatchItem("3", "Success Item 3", "03 Jan", 300f, "Success Description 3"),
    MatchItem("4", "Success Item 4", "04 Jan", 400f, "Success Description 4")
  
  )
  
  override suspend fun getMatchItems(): List<MatchItem> = mockItems
}
