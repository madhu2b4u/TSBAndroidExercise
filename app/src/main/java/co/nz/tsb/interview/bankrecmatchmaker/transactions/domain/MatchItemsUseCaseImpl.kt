package co.nz.tsb.interview.bankrecmatchmaker.transactions.domain

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class MatchItemsUseCaseImpl @Inject constructor(private val repository: TransactionRepository) :
    MatchItemsUseCase {
    override fun matchSelectedItem(
        matchItems: List<Transaction>,
        selectedItem: Transaction
    ): List<Transaction> {
        return matchItems.map {
            if (it.id == selectedItem.id) it.copy(isChecked = !it.isChecked) else it
        }
    }

    override fun calculateRemainingTotal(
        matchItems: List<Transaction>,
        transactionTotal: Float
    ): Float {
        val selectedAmount =
            matchItems.filter { it.isChecked }.sumOf { it.total.toDouble() }.toFloat()
        return transactionTotal - selectedAmount
    }

    /**
     * This method finds a subset of transactions from the given list (`matchItems`) that adds up to a specified target total (`transactionTotal`).
     * It uses a recursive backtracking approach to explore all possible subsets of transactions, returning the first valid subset that matches the target total.
     * If no such subset is found, the method returns null.
     * */

    override fun findSubsetThatMatchesRemainingTotal(
        matchItems: List<Transaction>,
        transactionTotal: Float
    ): List<Transaction>? {
        val result = mutableListOf<Transaction>()
        fun findSubset(index: Int, currentSum: Float): Boolean {
            if (currentSum == transactionTotal) return true
            if (currentSum > transactionTotal || index >= matchItems.size) return false

            result.add(matchItems[index])
            if (findSubset(index + 1, currentSum + matchItems[index].total)) return true

            result.removeAt(result.size - 1)
            return findSubset(index + 1, currentSum)
        }

        return if (findSubset(0, 0f)) result else null
    }
}