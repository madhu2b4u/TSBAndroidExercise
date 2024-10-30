package co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.MatchItemsUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val matchItemsUseCase: MatchItemsUseCase
) : ViewModel() {

    private val _matchItems = MutableStateFlow<List<Transaction>>(emptyList())
    val matchItems: StateFlow<List<Transaction>> = _matchItems

    private val _remainingTotal = MutableStateFlow(0f)
    val remainingTotal: StateFlow<Float> = _remainingTotal

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val transactionTotal: Float = 499.0f

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            try {
                val items = transactionUseCase.getTransactions()
                _matchItems.value = items
                _remainingTotal.value = transactionTotal
                autoSelectExactMatch()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data"
            }
        }
    }

    fun onItemSelected(matchItem: Transaction) {
        _matchItems.update { currentItems ->
            matchItemsUseCase.matchSelectedItem(currentItems, matchItem)
        }
        _remainingTotal.value =
            matchItemsUseCase.calculateRemainingTotal(_matchItems.value, transactionTotal)
    }

    private fun autoSelectExactMatch() {
        _matchItems.value.forEach { item ->
            if (item.total == _remainingTotal.value) {
                onItemSelected(item)
            }
        }
    }

    fun autoSelectMatchingSubset() {
        val items = _matchItems.value
        val subset =
            matchItemsUseCase.findSubsetThatMatchesRemainingTotal(items, _remainingTotal.value)
        subset?.forEach { onItemSelected(it) }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearSelections() {
        _matchItems.update { currentItems ->
            currentItems.map { it.copy(isChecked = false) }
        }
        _remainingTotal.value = transactionTotal
    }
}
