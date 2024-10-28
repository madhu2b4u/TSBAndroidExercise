package co.nz.tsb.interview.bankrecmatchmaker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.nz.tsb.interview.bankrecmatchmaker.data.model.MatchItem
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FindMatchViewModel @Inject constructor(
  private val matchRepository: MatchRepository
) : ViewModel() {
  
  private val _remainingTotal = MutableStateFlow(10000f)
  val remainingTotal: StateFlow<Float> = _remainingTotal
  
  private val _matchItems = MutableStateFlow<List<MatchItem>>(emptyList())
  val matchItems: StateFlow<List<MatchItem>> = _matchItems
  
  private val _errorMessage = MutableStateFlow<String?>(null)
  val errorMessage: StateFlow<String?> = _errorMessage
  
  init {
    loadMockData()
  }
  
  private fun loadMockData() {
    viewModelScope.launch {
      try {
        val items = matchRepository.getMatchItems()
        _matchItems.value = items
      } catch (e: Exception) {
        _errorMessage.value = "Failed to load data"
      }
    }
  }
  
  fun selectItem(item: MatchItem) {
    viewModelScope.launch {
      if (item.isChecked) {
        if (_remainingTotal.value < item.total) {
          _errorMessage.value = "Failed to select item"
          return@launch
        } else {
          _remainingTotal.value -= item.total
        }
      } else {
        _remainingTotal.value += item.total
      }
      selectItemInItems(item)
    }
  }
  
  fun autoSelectItem() {
    viewModelScope.launch {
      _matchItems.value.find {
        it.total == _remainingTotal.value
      }?.let {
        selectItem(it.copy(isChecked = true))
      } ?: run {
        _errorMessage.value = "Failed to auto select item"
      }
    }
  }
  
  private fun selectItemInItems(item: MatchItem) {
    _matchItems.value = _matchItems.value.map {
      if (it.id == item.id) {
        it.copy(isChecked = item.isChecked)
      } else {
        it
      }
    }
  }
  
  fun setRemainingTotalWhenStart(total: Float) {
    _remainingTotal.value = total
  }
  
  fun clearErrorMessage() {
    _errorMessage.value = null
  }
}
