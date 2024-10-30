/*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.FakeTransactionUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.viewmodel.FindMatchViewModel
import co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.viewmodel.TransactionViewModel
import co.nz.tsb.interview.bankrecmatchmaker.ui.activity.FindMatchScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FindMatchScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    // Mock ViewModel with fake data for testing
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        // Create a fake ViewModel with fake data
        viewModel = TransactionViewModel(FakeTransactionUseCase())
    }

    @Test
    fun testTransactionListDisplayed() {
        composeTestRule.setContent {
            FindMatchScreen(viewModel = viewModel)
        }

        // Verify that the transaction items are displayed in the list
        composeTestRule.onNodeWithText("City Limousines").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ridgeway University").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cube Land").assertIsDisplayed()
    }

    @Test
    fun testSelectTransactionUpdatesRemainingTotal() {
        composeTestRule.setContent {
            FindMatchScreen(viewModel = viewModel)
        }

        // Initially, check the remaining total
        composeTestRule.onNodeWithText("Remaining Total: 618").assertExists()

        // Select "City Limousines" and check if remaining total is updated
        composeTestRule.onNodeWithText("City Limousines").performClick()
        composeTestRule.onNodeWithText("Remaining Total: 369").assertExists()  // 618 - 249 = 369
    }

    @Test
    fun testAutoSelectMatchingSubset() {
        composeTestRule.setContent {
            FindMatchScreen(viewModel = viewModel)
        }

        // Initially, no item is selected
        composeTestRule.onNodeWithText("City Limousines").assertIsNotChecked()

        // Click on the auto-select button
        composeTestRule.onNodeWithText("Auto Select Matching Subset").performClick()

        // Verify that appropriate items are selected
        composeTestRule.onNodeWithText("City Limousines").assertIsChecked()
        composeTestRule.onNodeWithText("Ridgeway University").assertIsChecked()
    }

    @Test
    fun testErrorMessageDisplayedWhenFailedToSelectItem() {
        runBlocking {
            viewModel._errorMessage.emit("Failed to select item")
        }

        composeTestRule.setContent {
            FindMatchScreen(viewModel = viewModel)
        }

        // Verify that the error message is displayed using a Snackbar
        composeTestRule.onNodeWithText("Failed to select item").assertExists()
    }
}

*/
