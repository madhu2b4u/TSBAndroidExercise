package co.nz.tsb.interview.bankrecmatchmaker.ui.viewmodel

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCase
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.TransactionUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TransactionUseCaseTest {

    private lateinit var transactionUseCase: TransactionUseCase
    private lateinit var repository: TransactionRepository

    @Before
    fun setUp() {
        repository = mockk()
        transactionUseCase = TransactionUseCaseImpl(repository) // Injecting mocked repository
    }

    @Test
    fun `getTransactions should return list of transactions`() = runTest {
        val mockTransactions = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Sales Invoice"),
            Transaction("2", "Item 2", "02 Jan", 200f, "Sales Invoice")
        )
        coEvery { repository.transactions() } returns mockTransactions

        val result = transactionUseCase.getTransactions()

        assertEquals(mockTransactions, result)
        coVerify(exactly = 1) { repository.transactions() }
    }

    @Test
    fun `getTransactions should return empty list when no transactions`() = runTest {

        val emptyTransactions = emptyList<Transaction>()
        coEvery { repository.transactions() } returns emptyTransactions

        val result = transactionUseCase.getTransactions()

        assertEquals(emptyTransactions, result)
        coVerify(exactly = 1) { repository.transactions() }
    }
}
