package co.nz.tsb.interview.bankrecmatchmaker

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.screens.TransactionScreen
import co.nz.tsb.interview.bankrecmatchmaker.ui.theme.TSBAndroidExerciseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun transactionScreenDisplaysCorrectly() {
        composeTestRule.setContent {
            TSBAndroidExerciseTheme {
                TransactionScreen()
            }
        }

        // Assuming TransactionScreen has a Text composable with "Transaction Details"
        composeTestRule.onNodeWithText("Transaction Details").assertIsDisplayed()
    }
}
