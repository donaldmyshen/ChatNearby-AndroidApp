package com.example.chatnearby.account

import android.app.ActivityManager
import android.content.Context
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.example.chatnearby.R
import com.example.chatnearby.messages.MessageMenuActivity
import junit.framework.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class LoginActivityTest{
    @Rule
    var mainActivityTestRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun testClearTextbox() {
        Espresso.onView(ViewMatchers.withId(R.id.email_edittext_login))
            .perform(ViewActions.typeText("test@test.com")).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.password_edittext_login)).perform(ViewActions.typeText("123456")).perform(
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(ViewMatchers.withId(R.id.login_button_login)).perform(ViewActions.click())
        Assert.assertTrue(isActivityTop(MessageMenuActivity(), "MessageMenuActivity"))
    }

    private fun isActivityTop(context: Context, activityName: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfos = am.getRunningTasks(1)
        var task: String? = null
        if (runningTaskInfos != null) task = runningTaskInfos[0].topActivity.toString()
        return if (task == null) {
            false
        } else task == activityName
    }
}