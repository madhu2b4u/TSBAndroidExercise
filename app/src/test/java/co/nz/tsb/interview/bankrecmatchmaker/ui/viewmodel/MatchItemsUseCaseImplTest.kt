package co.nz.tsb.interview.bankrecmatchmaker.ui.viewmodel

import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.model.Transaction
import co.nz.tsb.interview.bankrecmatchmaker.transactions.data.repository.TransactionRepository
import co.nz.tsb.interview.bankrecmatchmaker.transactions.domain.MatchItemsUseCaseImpl
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class MatchItemsUseCaseImplTest {

    private lateinit var matchItemsUseCase: MatchItemsUseCaseImpl
    private lateinit var repository: TransactionRepository

    @Before
    fun setUp() {
        repository = mockk()
        matchItemsUseCase = MatchItemsUseCaseImpl(repository)
    }

    @Test
    fun `matchSelectedItem should toggle isChecked for selected item`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 200f, "Description", isChecked = false),
            Transaction("3", "Item 3", "03 Jan", 300f, "Description", isChecked = false)
        )
        val selectedItem = mockItems[1] // Select the second item

        val result = matchItemsUseCase.matchSelectedItem(mockItems, selectedItem)

        assertEquals(false, result[0].isChecked)
        assertEquals(true, result[1].isChecked)
        assertEquals(false, result[2].isChecked)
    }

    @Test
    fun `matchSelectedItem should toggle isChecked off if item is already checked`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 200f, "Description", isChecked = true), // Initially checked
            Transaction("3", "Item 3", "03 Jan", 300f, "Description", isChecked = false)
        )
        val selectedItem = mockItems[1] // Select the second item (which is already checked)

        val result = matchItemsUseCase.matchSelectedItem(mockItems, selectedItem)

        assertEquals(false, result[0].isChecked)
        assertEquals(false, result[1].isChecked)
        assertEquals(false, result[2].isChecked)
    }

    @Test
    fun `calculateRemainingTotal should return correct remaining total when no items are checked`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 200f, "Description", isChecked = false),
            Transaction("3", "Item 3", "03 Jan", 300f, "Description", isChecked = false)
        )
        val transactionTotal = 1000f

        val result = matchItemsUseCase.calculateRemainingTotal(mockItems, transactionTotal)

        assertEquals(1000f, result, 0.0f)
    }

    @Test
    fun `calculateRemainingTotal should return correct remaining total when some items are checked`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = true), // Checked
            Transaction("2", "Item 2", "02 Jan", 200f, "Description", isChecked = false),
            Transaction("3", "Item 3", "03 Jan", 300f, "Description", isChecked = true)  // Checked
        )
        val transactionTotal = 1000f

        val result = matchItemsUseCase.calculateRemainingTotal(mockItems, transactionTotal)

        val expectedSelectedTotal = 100f + 300f
        assertEquals(1000f - expectedSelectedTotal, result, 0.0f)
    }

    @Test
    fun `calculateRemainingTotal should return correct remaining total when all items are checked`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = true),
            Transaction("2", "Item 2", "02 Jan", 200f, "Description", isChecked = true),
            Transaction("3", "Item 3", "03 Jan", 300f, "Description", isChecked = true)
        )
        val transactionTotal = 1000f

        val result = matchItemsUseCase.calculateRemainingTotal(mockItems, transactionTotal)

        val expectedSelectedTotal = 100f + 200f + 300f // Sum of all checked items
        assertEquals(1000f - expectedSelectedTotal, result, 0.0f)
    }

    @Test
    fun `findSubsetThatMatchesRemainingTotal should return subset that matches the total`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 150f, "Description", isChecked = false),
            Transaction("3", "Item 3", "03 Jan", 250f, "Description", isChecked = false),
            Transaction("4", "Item 4", "04 Jan", 300f, "Description", isChecked = false)
        )
        val total = 400f

        val result = matchItemsUseCase.findSubsetThatMatchesRemainingTotal(mockItems, total)

        val resultSum = result?.let { subset -> subset.sumOf { it.total.toDouble() } }?.toFloat() ?: 0f
        assertEquals(total, resultSum)
    }


    @Test
    fun `findSubsetThatMatchesRemainingTotal should return null if no subset matches the total`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 150f, "Description", isChecked = false),
            Transaction("3", "Item 3", "03 Jan", 250f, "Description", isChecked = false)
        )
        val total = 600f

        val result = matchItemsUseCase.findSubsetThatMatchesRemainingTotal(mockItems, total)

        assertNull(result)
    }

    @Test
    fun `findSubsetThatMatchesRemainingTotal should return entire list if sum matches the total`() {
        val mockItems = listOf(
            Transaction("1", "Item 1", "01 Jan", 100f, "Description", isChecked = false),
            Transaction("2", "Item 2", "02 Jan", 150f, "Description", isChecked = false),
            Transaction("3", "Item 3", "03 Jan", 250f, "Description", isChecked = false)
        )
        val total = 500f

        val result = matchItemsUseCase.findSubsetThatMatchesRemainingTotal(mockItems, total)

        assertEquals(mockItems, result)
    }
}
