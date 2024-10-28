package co.nz.tsb.interview.bankrecmatchmaker.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import co.nz.tsb.interview.bankrecmatchmaker.R
import co.nz.tsb.interview.bankrecmatchmaker.ui.adapter.MatchAdapter
import co.nz.tsb.interview.bankrecmatchmaker.ui.viewmodel.FindMatchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindMatchActivity : AppCompatActivity() {
  private val viewModel: FindMatchViewModel by viewModels()
  private lateinit var matchText: TextView
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_find_match)
    
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    matchText = findViewById(R.id.match_text)
    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
    
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setTitle(R.string.title_find_match)
    
    getRemainingTotal()
    viewModel.autoSelectItem()
    
    val adapter = MatchAdapter { item -> viewModel.selectItem(item) }
    recyclerView.adapter = adapter
    
    lifecycleScope.launch {
      viewModel.matchItems.collect { items ->
        adapter.updateItems(items)
      }
    }
    
    lifecycleScope.launch {
      viewModel.remainingTotal.collect { total ->
        matchText.text = getString(R.string.select_matches, total.toInt())
      }
    }
    
    lifecycleScope.launch {
      viewModel.errorMessage.collect { error ->
        error?.let {
          Snackbar.make(findViewById(R.id.find_match_layout), it, Snackbar.LENGTH_LONG).show()
          viewModel.clearErrorMessage()
        }
      }
    }
  }
  
  private fun getRemainingTotal() {
    val remainingTotal = intent.getFloatExtra(TARGET_MATCH_VALUE, DEFAULT_VALUE)
    viewModel.setRemainingTotalWhenStart(remainingTotal)
  }
  
  companion object {
    private const val DEFAULT_VALUE = 10000f
    const val TARGET_MATCH_VALUE: String = "co.nz.tsb.interview.target_match_value"
  }
}
