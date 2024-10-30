package co.nz.tsb.interview.bankrecmatchmaker.transactions.domain

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction

interface MatchItemsUseCase {

    fun matchSelectedItem(
        matchItems: List<Transaction>,
        selectedItem: Transaction
    ): List<Transaction>

    fun calculateRemainingTotal(matchItems: List<Transaction>, transactionTotal: Float): Float

    fun findSubsetThatMatchesRemainingTotal(
        matchItems: List<Transaction>,
        transactionTotal: Float
    ): List<Transaction>?
}