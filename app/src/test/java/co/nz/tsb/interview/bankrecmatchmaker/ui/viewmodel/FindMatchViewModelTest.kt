package co.nz.tsb.interview.bankrecmatchmaker.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.nz.tsb.interview.bankrecmatchmaker.data.model.MatchItem
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MatchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class FindMatchViewModelTest {
  
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()
  
  private val testDispatcher = UnconfinedTestDispatcher()
  private val testScope = TestScope(testDispatcher)
  
  private lateinit var matchRepository: MatchRepository
  
  private lateinit var viewModel: FindMatchViewModel
  
  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
    matchRepository = mockk()
  }
  
  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }
  
  @Test
  fun `load data success`() = testScope.runTest {
    val mockItems = listOf(MatchItem("1", "Item 1", "01 Jan", 100f, "Description"))
    coEvery { matchRepository.getMatchItems() } returns mockItems
    
    viewModel = FindMatchViewModel(matchRepository)
    
    advanceUntilIdle()
    
    assertEquals(mockItems, viewModel.matchItems.value)
    assertNull(viewModel.errorMessage.value)
    coVerify { matchRepository.getMatchItems() }
  }
  
  @Test
  fun `load data error`() = testScope.runTest {
    coEvery { matchRepository.getMatchItems() } throws Exception("Error")
    
    viewModel = FindMatchViewModel(matchRepository)
    
    advanceUntilIdle()
    
    assertEquals(emptyList<MatchItem>(), viewModel.matchItems.value)
    assertEquals("Failed to load data", viewModel.errorMessage.value)
    coVerify { matchRepository.getMatchItems() }
  }
  
  @Test
  fun `setRemainingTotalWhenStart should update remainingTotal`() = testScope.runTest {
    
    val mockItems = listOf(
      MatchItem("1", "Item 1", "01 Jan", 100f, "Description"),
      MatchItem("2", "Item 2", "02 Jan", 200f, "Description")
    )
    coEvery { matchRepository.getMatchItems() } returns mockItems
    
    viewModel = FindMatchViewModel(matchRepository)
    
    viewModel.setRemainingTotalWhenStart(5000f)
    
    advanceUntilIdle()
    
    assertEquals(5000f, viewModel.remainingTotal.value)
  }
  
  @Test
  fun `autoSelectItem should select item with total equal to remainingTotal`() = testScope.runTest {
    val mockItems = listOf(
      MatchItem("1", "Item 1", "01 Jan", 100f, "Description"),
      MatchItem("2", "Item 2", "02 Jan", 200f, "Description")
    )
    coEvery { matchRepository.getMatchItems() } returns mockItems
    
    viewModel = FindMatchViewModel(matchRepository)
    viewModel.setRemainingTotalWhenStart(200f)
    
    
    viewModel.autoSelectItem()
    
    advanceUntilIdle()
    
    assertEquals(0f, viewModel.remainingTotal.value)
    assertEquals(
      mockItems.map { it.copy(isChecked = it == mockItems[1]) },
      viewModel.matchItems.value
    )
    coVerify { matchRepository.getMatchItems() }
  }
  
  @Test
  fun `autoSelectItem should select item with total not equal to remainingTotal`() =
    testScope.runTest {
      val mockItems = listOf(
        MatchItem("1", "Item 1", "01 Jan", 100f, "Description"),
        MatchItem("2", "Item 2", "02 Jan", 200f, "Description")
      )
      coEvery { matchRepository.getMatchItems() } returns mockItems
      
      viewModel = FindMatchViewModel(matchRepository)
      viewModel.setRemainingTotalWhenStart(10000f)
      
      
      viewModel.autoSelectItem()
      
      advanceUntilIdle()
      
      assertEquals(10000f, viewModel.remainingTotal.value)
      assertEquals(mockItems, viewModel.matchItems.value)
      assertEquals("Failed to auto select item", viewModel.errorMessage.value)
      coVerify { matchRepository.getMatchItems() }
      
      viewModel.clearErrorMessage()
      
      advanceUntilIdle()
      
      assertNull(viewModel.errorMessage.value)
    }
  
  @Test
  fun `selectItem should update remainingTotal and matchItems`() = testScope.runTest {
    val mockItems = listOf(
      MatchItem("1", "Item 1", "01 Jan", 100f, "Description"),
      MatchItem("2", "Item 2", "02 Jan", 200f, "Description")
    )
    coEvery { matchRepository.getMatchItems() } returns mockItems
    
    viewModel = FindMatchViewModel(matchRepository)
    
    viewModel.selectItem(mockItems[0].copy(isChecked = true))
    
    advanceUntilIdle()
    
    assertEquals(9900f, viewModel.remainingTotal.value)
    assertEquals(
      mockItems.map { it.copy(isChecked = it == mockItems[0]) },
      viewModel.matchItems.value
    )
  }
  
  @Test
  fun `selectItem should set errorMessage if remainingTotal is insufficient`() = testScope.runTest {
    val mockItems = listOf(
      MatchItem("1", "Item 1", "01 Jan", 100f, "Description"),
      MatchItem("2", "Item 2", "02 Jan", 200f, "Description")
    )
    coEvery { matchRepository.getMatchItems() } returns mockItems
    
    viewModel = FindMatchViewModel(matchRepository)
    viewModel.setRemainingTotalWhenStart(100f)
    
    viewModel.selectItem(mockItems[1].copy(isChecked = true))
    
    advanceUntilIdle()
    
    assertEquals(100f, viewModel.remainingTotal.value)
    assertEquals("Failed to select item", viewModel.errorMessage.value)
    coVerify { matchRepository.getMatchItems() }
  }
  
  @Test
  fun `deSelectItem should update remainingTotal and matchItems`() = testScope.runTest {
    val mockItems = listOf(
      MatchItem("1", "Item 1", "01 Jan", 100f, "Description"),
      MatchItem("2", "Item 2", "02 Jan", 200f, "Description")
    )
    coEvery { matchRepository.getMatchItems() } returns mockItems
    
    viewModel = FindMatchViewModel(matchRepository)
    
    viewModel.selectItem(mockItems[0].copy(isChecked = true))
    viewModel.selectItem(mockItems[1].copy(isChecked = true))
    
    advanceUntilIdle()
    
    viewModel.selectItem(mockItems[0].copy(isChecked = false))
    
    advanceUntilIdle()
    
    assertEquals(9800f, viewModel.remainingTotal.value)
    assertEquals(
      mockItems.map { it.copy(isChecked = it == mockItems[1]) },
      viewModel.matchItems.value
    )
    
    coVerify { matchRepository.getMatchItems() }
  }
}