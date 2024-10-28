package co.nz.tsb.interview.bankrecmatchmaker.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.nz.tsb.interview.bankrecmatchmaker.R
import co.nz.tsb.interview.bankrecmatchmaker.data.repository.MatchRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FindMatchActivityTest {
  
  @get:Rule
  var hiltRule = HiltAndroidRule(this)
  
  @get:Rule
  val activityRule = ActivityScenarioRule(FindMatchActivity::class.java)
  
  @Inject
  lateinit var matchRepository: MatchRepository
  
  @Before
  fun setUp() {
    hiltRule.inject()
  }
  
  @Test
  fun testLoadDataSuccess() {
    
    // Check if the RecyclerView is displayed
    onView(withId(R.id.recycler_view))
      .check(matches(isDisplayed()))
    
    // Check if the first item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 1"))
      .check(matches(isDisplayed()))
    
    // Check if the second item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 2"))
      .check(matches(isDisplayed()))
    
    // Check if the third item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 3"))
      .check(matches(isDisplayed()))
    
    // Check if the fourth item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 4"))
      .check(matches(isDisplayed()))
    
    // Check if the remaining total is displayed with correct data
    onView(withId(R.id.match_text))
      .check(matches(withText("Select matches: 10000 to match")))
  }
  
  @Test
  fun testLoadDataSuccessAndCheckSomeItems() {
    
    // Check if the RecyclerView is displayed
    onView(withId(R.id.recycler_view))
      .check(matches(isDisplayed()))
    
    // Check if the first item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 1"))
      .check(matches(isDisplayed()))
    
    // Check if the second item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 2"))
      .check(matches(isDisplayed()))
    
    // Check if the third item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 3"))
      .check(matches(isDisplayed()))
    
    // Check if the fourth item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 4"))
      .check(matches(isDisplayed()))
    
    // Check if the remaining total is displayed with correct data
    onView(withId(R.id.match_text))
      .check(matches(withText("Select matches: 10000 to match")))
    
    
    onView(withText("Success Item 1"))
      .perform(click())
    
    onView(withText("Success Item 3"))
      .perform(click())
    
    
    onView(withId(R.id.match_text))
      .check(matches(withText("Select matches: 9600 to match")))
  }
  
  @Test
  fun testLoadDataSuccessAndCheckSomeItemsAndUncheck() {
    
    // Check if the RecyclerView is displayed
    onView(withId(R.id.recycler_view))
      .check(matches(isDisplayed()))
    
    // Check if the first item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 1"))
      .check(matches(isDisplayed()))
    
    // Check if the second item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 2"))
      .check(matches(isDisplayed()))
    
    // Check if the third item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 3"))
      .check(matches(isDisplayed()))
    
    // Check if the fourth item in the RecyclerView is displayed with correct data
    onView(withText("Success Item 4"))
      .check(matches(isDisplayed()))
    
    // Check if the remaining total is displayed with correct data
    onView(withId(R.id.match_text))
      .check(matches(withText("Select matches: 10000 to match")))
    
    
    onView(withText("Success Item 1"))
      .perform(click())
    
    onView(withText("Success Item 2"))
      .perform(click())
    
    onView(withText("Success Item 3"))
      .perform(click())
    
    onView(withText("Success Item 4"))
      .perform(click())
    
    
    
    onView(withId(R.id.match_text))
      .check(matches(withText("Select matches: 9000 to match")))
    
    
    onView(withText("Success Item 2"))
      .perform(click())
    
    onView(withText("Success Item 3"))
      .perform(click())
    
    onView(withId(R.id.match_text))
      .check(matches(withText("Select matches: 9500 to match")))
    
  }
}