package br.com.helpdev.githubers.ui

import android.view.Gravity
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import br.com.helpdev.githubers.R
import br.com.helpdev.githubers.util.childAtPosition
import br.com.helpdev.githubers.util.getToolbarNavigationContentDescription
import org.hamcrest.Matchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GithubersActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(GithubersActivity::class.java)


    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("br.com.helpdev.githubers", appContext.packageName)
    }

    @Test
    fun clickOnAndroidHomeIcon_OpensAndClosesNavigation() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.START)))

        clickOnHomeIconToOpenNavigationDrawer()
        checkDrawerIsOpen()
    }

    @Test
    fun onRotate_NavigationStaysOpen() {
        clickOnHomeIconToOpenNavigationDrawer()

        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            // Rotate device to landscape
            setOrientationLeft()
            checkDrawerIsOpen()

            // Rotate device back to portrait
            setOrientationRight()
            checkDrawerIsOpen()
        }
    }

    @Test
    fun clickOnAndroidHome_OpenDrawer_Then_CheckElementsPositions() {
        clickOnAndroidHomeIcon_OpensAndClosesNavigation()

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.design_menu_item_text),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.design_navigation_view),
                        1
                    ),
                    0
                ),
                ViewMatchers.isDisplayed(),
                ViewMatchers.withText(R.string.nav_item_title_users_list)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.design_menu_item_text),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.design_navigation_view),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed(),
                ViewMatchers.withText(R.string.nav_item_title_favorites_users)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.design_menu_item_text),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.design_navigation_view),
                        3
                    ),
                    0
                ),
                ViewMatchers.isDisplayed(),
                ViewMatchers.withText(R.string.nav_item_title_favorites_repos)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun pressDeviceBack_CloseDrawer_Then_PressBack_Close_App() {
        clickOnHomeIconToOpenNavigationDrawer()
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        checkDrawerIsNotOpen()
        assertEquals(activityTestRule.activity.isFinishing, false)
        assertEquals(activityTestRule.activity.isDestroyed, false)
    }

    private fun clickOnHomeIconToOpenNavigationDrawer() {
        Espresso.onView(
            ViewMatchers.withContentDescription(
                getToolbarNavigationContentDescription(
                    activityTestRule.activity, R.id.toolbar
                )
            )
        ).perform(ViewActions.click())
    }

    private fun checkDrawerIsOpen() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isOpen(Gravity.START)))
    }

    private fun checkDrawerIsNotOpen() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.START)))
    }

}
