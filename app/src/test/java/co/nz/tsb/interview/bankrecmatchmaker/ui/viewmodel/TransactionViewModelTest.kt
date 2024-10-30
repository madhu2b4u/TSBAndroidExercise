package co.nz.tsb.interview.bankrecmatchmaker.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.MatchItemsUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.presentation.viewmodel.TransactionViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TransactionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var transactionUseCase: TransactionUseCase
    private lateinit var matchItemsUseCase: MatchItemsUseCase
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        transactionUseCase = mockk()
        matchItemsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load transactions success`() = testScope.runTest {
        val mockItems = listOf(Transaction("1", "Item 1", "01 Jan", 100f, "Description"))
        coEvery { transactionUseCase.getTransactions() } returns mockItems

        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)

        advanceUntilIdle()

        assertEquals(mockItems, viewModel.matchItems.value)
        assertNull(viewModel.errorMessage.value)
        coVerify { transactionUseCase.getTransactions() }
    }

    @Test
    fun `load transactions error`() = testScope.runTest {
        coEvery { transactionUseCase.getTransactions() } throws Exception("Error")

        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)

        advanceUntilIdle()

        assertEquals(emptyList<Transaction>(), viewModel.matchItems.value)
        assertEquals("Failed to load data", viewModel.errorMessage.value)
        coVerify { transactionUseCase.getTransactions() }
    }

    @Test
    fun `onItemSelected should update remainingTotal and matchItems`() = testScope.runTest {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 250f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 249f, "Description", isChecked = false)
        )

        coEvery { transactionUseCase.getTransactions() } returns mockItems

        coEvery { matchItemsUseCase.matchSelectedItem(any(), any()) } answers { call ->
            val currentItems = call.invocation.args[0] as List<Transaction>
            val selectedItem = call.invocation.args[1] as Transaction
            currentItems.map { it.copy(isChecked = it.id == selectedItem.id) }
        }

        coEvery { matchItemsUseCase.calculateRemainingTotal(any(), any()) } answers { call ->
            val currentItems = call.invocation.args[0] as List<Transaction>
            val total = call.invocation.args[1] as Float
            total - (currentItems.firstOrNull { it.isChecked }?.total ?: 0f)
        }

        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)

        viewModel.onItemSelected(mockItems[0].copy(isChecked = true))

        advanceUntilIdle()

        assertEquals(249f, viewModel.remainingTotal.value)

        assertEquals(
            mockItems.map { it.copy(isChecked = it.id == mockItems[0].id) },
            viewModel.matchItems.value
        )

        coVerify {
            matchItemsUseCase.matchSelectedItem(
                mockItems,
                mockItems[0].copy(isChecked = true)
            )
        }

        coVerify {
            matchItemsUseCase.calculateRemainingTotal(
                mockItems.map { it.copy(isChecked = it.id == mockItems[0].id) },
                499f
            )
        }
    }



   /* @Test
    fun `autoSelectMatchingSubset should select subset that matches remaining total`() = testScope.runTest {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 250f, "Description"),
            Transaction("2", "Item 2", "02 Jan", 249f, "Description")
        )
        coEvery { transactionUseCase.getTransactions() } returns mockItems
        coEvery { matchItemsUseCase.matchSelectedItem(any(), any()) } answers { call ->
            val currentItems = call.invocation.args[0] as List<Transaction>
            val selectedItem = call.invocation.args[1] as Transaction
            currentItems.map { it.copy(isChecked = it.id == selectedItem.id) }
        }
        coEvery { matchItemsUseCase.calculateRemainingTotal(any(), any()) } returns 0f

        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)
        viewModel.autoSelectMatchingSubset()

        advanceUntilIdle()

        assertEquals(0f, viewModel.remainingTotal.value)
        coVerify { matchItemsUseCase.matchSelectedItem(any(), any()) }
    }*/

    @Test
    fun `clearErrorMessage should clear error message`() = testScope.runTest {
        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)
        viewModel.clearErrorMessage()

        advanceUntilIdle()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `deSelectItem should update remainingTotal and matchItems`() = testScope.runTest {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 250f, "Description", isChecked = true), // Initially selected
            Transaction("2", "Item 2", "02 Jan", 249f, "Description", isChecked = false)
        )

        coEvery { transactionUseCase.getTransactions() } returns mockItems

        coEvery { matchItemsUseCase.matchSelectedItem(any(), any()) } answers { call ->
            val currentItems = call.invocation.args[0] as List<Transaction>
            val selectedItem = call.invocation.args[1] as Transaction
            currentItems.map { it.copy(isChecked = if (it.id == selectedItem.id) false else it.isChecked) }
        }

        coEvery { matchItemsUseCase.calculateRemainingTotal(any(), any()) } answers { call ->
            val currentItems = call.invocation.args[0] as List<Transaction>
            val total = call.invocation.args[1] as Float
            total + (currentItems.firstOrNull { it.id == "1" && it.isChecked == false }?.total ?: 0f)
        }

        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)

        viewModel.onItemSelected(mockItems[0].copy(isChecked = false))

        advanceUntilIdle()

        assertEquals(749f, viewModel.remainingTotal.value)

        assertEquals(
            mockItems.map { it.copy(isChecked = it.id == mockItems[0].id && it.isChecked == false) },
            viewModel.matchItems.value
        )

        coVerify {
            matchItemsUseCase.matchSelectedItem(
                mockItems,
                mockItems[0].copy(isChecked = false)
            )
        }

        coVerify {
            matchItemsUseCase.calculateRemainingTotal(
                mockItems.map { it.copy(isChecked = it.id == mockItems[0].id && it.isChecked == false) },
                499f
            )
        }
    }

    @Test
    fun `clearSelections should uncheck all items and reset total`() = testScope.runTest {

        val mockItems = listOf(
            Transaction("1", "Transaction 1", "01 Jan", 100f, "Invoice", isChecked = true),
            Transaction("2", "Transaction 2", "02 Jan", 150f, "Invoice", isChecked = true),
            Transaction("3", "Transaction 3", "03 Jan", 250f, "Invoice", isChecked = true)
        )
        coEvery { transactionUseCase.getTransactions() } returns mockItems


        viewModel = TransactionViewModel(transactionUseCase, matchItemsUseCase)
        viewModel.loadTransactions()

        advanceUntilIdle()


        viewModel.clearSelections()

        advanceUntilIdle()
        val updatedItems = viewModel.matchItems.first()
        updatedItems.forEach { assertEquals(false, it.isChecked) }

        assertEquals(499f, viewModel.remainingTotal.first())
    }
}
