package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static java.lang.Thread.sleep;

import android.content.Intent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC21AccessOmniNoteWithPassword extends BaseEspressoTest {

    public void createPasswordForApp(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction press_setting = onView(
                allOf(withId(R.id.settings_view),
                        childAtPosition(
                                allOf(withId(R.id.left_drawer),
                                        childAtPosition(
                                                withId(R.id.navigation_drawer),
                                                0)),
                                2)));
        press_setting.perform(scrollTo(), click());

        ViewInteraction press_data = onView(
                childAtPosition(
                        withId(android.R.id.list_container),
                        0));
        press_data.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction press_password = onView(
                childAtPosition(
                        withId(android.R.id.list_container),
                        0));
        press_password.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("aa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                2)));
        appCompatEditText2.perform(scrollTo(), replaceText("aa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.question),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                4)));
        appCompatEditText3.perform(scrollTo(), replaceText("aa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.answer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                5)));
        appCompatEditText4.perform(scrollTo(), replaceText("aa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.answer_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                6)));
        appCompatEditText5.perform(scrollTo(), replaceText("aa"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.password_confirm), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                2)));
        materialButton.perform(scrollTo(), click());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction enable_request_password = onView(
                childAtPosition(
                        withId(android.R.id.list_container),
                        0));
        enable_request_password.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction enter_password = onView(withId(R.id.password_request));
        enter_password.perform(replaceText("aa"), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());

        pressBackUnconditionally();

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activityRule.finishActivity();
        activityRule.launchActivity(new Intent());
    }

    @Test
    public void accessAppWithPasswordTest() {
        createPasswordForApp();

        onView(withId(R.id.password_request)).check(matches(isDisplayed()));

        ViewInteraction enter_app_password = onView(withId(R.id.password_request));
        enter_app_password.perform(replaceText("aa"), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}