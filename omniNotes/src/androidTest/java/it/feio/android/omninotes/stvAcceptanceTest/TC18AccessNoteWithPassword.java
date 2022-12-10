package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static java.lang.Thread.sleep;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC18AccessNoteWithPassword extends BaseEspressoTest {

    public void createNoteWithPassword(String title, String content){
        createNoteByUI(title, content);

        onView(withText("Note Title")).perform(click());

        onView(withContentDescription("More options")).perform(click());

        onView(withText("Lock")).perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("abc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                2)));
        appCompatEditText2.perform(scrollTo(), replaceText("abc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.question),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                4)));
        appCompatEditText3.perform(scrollTo(), replaceText("ab"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.answer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                5)));
        appCompatEditText4.perform(scrollTo(), replaceText("ab"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.answer_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                6)));
        appCompatEditText5.perform(scrollTo(), replaceText("ab"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withText("Ok"));
        materialButton.perform(scrollTo(), click());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withContentDescription(R.string.drawer_open)).perform(click());
    }

    @Test
    public void accessNoteWithNewPassword(){
        createNoteWithPassword("Note Title", "This is the content.");

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Note Title")).perform(click());

        onView(withId(R.id.password_request)).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withId(R.id.password_request)).inRoot(isDialog()).perform(replaceText("abc"), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());

        onView(withId(R.id.detail_title)).check(matches(withText("Note Title")));
        onView(withId(R.id.detail_content)).check(matches(withText("This is the content.")));
    }

    @Test
    public void accessNoteWithNewPasswordConfirmFailed(){
        createNoteByUI("Note Title", "This is the content.");

        onView(withText("Note Title")).perform(click());

        onView(withContentDescription("More options")).perform(click());

        onView(withText("Lock")).perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                1)));
        appCompatEditText.perform(scrollTo(), replaceText("abc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                2)));
        appCompatEditText2.perform(scrollTo(), replaceText("abcd"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.question),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                4)));
        appCompatEditText3.perform(scrollTo(), replaceText("ab"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.answer),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                5)));
        appCompatEditText4.perform(scrollTo(), replaceText("ab"), closeSoftKeyboard());

        // Re-enter confirm the correct password and answer
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.answer_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                6)));
        appCompatEditText5.perform(scrollTo(), replaceText("abc"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withText("Ok"));
        materialButton.perform(scrollTo(), click());

        onView(withId(R.id.password_root)).check(matches(isDisplayed()));

        ViewInteraction appCompatEditText2Correct = onView(
                allOf(withId(R.id.password_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                2)));
        appCompatEditText2Correct.perform(scrollTo(), replaceText("abc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5Correct = onView(
                allOf(withId(R.id.answer_check),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.password_root),
                                        0),
                                6)));
        appCompatEditText5Correct.perform(scrollTo(), replaceText("ab"), closeSoftKeyboard());

        materialButton.perform(scrollTo(), click());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withContentDescription(R.string.drawer_open)).perform(click());

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Note Title")).perform(click());

        onView(withId(R.id.password_request)).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withId(R.id.password_request)).inRoot(isDialog()).perform(replaceText("abc"), closeSoftKeyboard());
        onView(withText("Ok")).perform(click());

        onView(withId(R.id.detail_title)).check(matches(withText("Note Title")));
        onView(withId(R.id.detail_content)).check(matches(withText("This is the content.")));
    }
}
