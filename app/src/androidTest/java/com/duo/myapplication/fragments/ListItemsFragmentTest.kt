package com.duo.myapplication.fragments


import android.content.Intent
import android.os.SystemClock
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.duo.myapplication.R
import com.duo.myapplication.home.MainActivity
import kotlinx.coroutines.Dispatchers
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class ListItemsFragmentTest {



    @Before
    fun setUp() {        // launch desired activity
        var firstActivity: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)
        firstActivity.launchActivity(Intent())
    }



    @Test
    fun test_visibility_fragment(){




        // test if the fragment is launch or not
        onView(withId(R.id.test_container_fragmentitemlist)).check(matches(isDisplayed()))
    }

    @After
    fun onAfter() {
        Intents.release()
    }


    @Test
    fun test_select_first_element_from_recycler(){


        SystemClock.sleep(5000)


        onView(withId(R.id.recyclerview)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                customActionClickOnItemEvent(R.id.cardviewitem)
            )
        )
    }


    fun customActionClickOnItemEvent(
        @NonNull targetViewId: Int
    ): ViewAction {

        return object : ViewAction {

            val click = ViewActions.click()

            override fun getDescription(): String = "Item clicked"

            override fun getConstraints(): Matcher<View> = click.constraints

            override fun perform(uiController: UiController?, view: View?) {
                click.perform(uiController,view?.findViewById(targetViewId))
            }
        }
    }

    @After
    fun tearDown()  {

    }
}