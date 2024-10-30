package co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.nz.tsb.interview.bankrecmatchmaker.R
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.viewmodel.TransactionViewModel

@Composable
fun TransactionScreen() {

    val viewModel: TransactionViewModel = hiltViewModel()
    val matchItems by viewModel.matchItems.collectAsState()
    val remainingTotal by viewModel.remainingTotal.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Select matches: $remainingTotal to match",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(matchItems.size) { index ->
                val item = matchItems[index]
                TransactionItem(item = item, onClick = { viewModel.onItemSelected(item) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.autoSelectMatchingSubset() }) {
            Text("Auto Select Matching Subset")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.clearSelections() }) {
            Text(stringResource(R.string.clear_selections))
        }

        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                action = {
                    Button(onClick = { viewModel.clearErrorMessage() }) {
                        Text(stringResource(R.string.dismiss))
                    }
                }
            ) {
                Text(it)
            }
        }
    }
}

@Composable
fun TransactionItem(item: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { onClick() }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = item.paidTo,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$" + item.total.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.transactionDate,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = item.docType,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = androidx.compose.ui.text.style.TextAlign.End
                )
            }
        }
    }
}
