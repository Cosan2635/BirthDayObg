package com.example.birthdayobg

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.birthdayobg", appContext.packageName)


        // @Test
        //fun TestLogin() {
        onView(withId(R.id.emailEditText)).perform(typeText("test123@live.dk"))
        Thread.sleep(5000)
        onView(withId(R.id.passwordEditText)).perform(typeText("123456"))
        onView(withId(R.id.btn_Login)).perform(click())


       // onView(withId(R.id.edittext_filter_name)).perform(typeText(""))
        //onView(withId(R.id.button_filter)).perform(click())

        activityScenario.close()
    }
}