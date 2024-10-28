package co.nz.tsb.interview.bankrecmatchmaker.data.repository

import co.nz.tsb.interview.bankrecmatchmaker.data.model.MatchItem

interface MatchRepository {
  suspend fun getMatchItems(): List<MatchItem>
}
