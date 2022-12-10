package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@RunWith(AndroidJUnit4.class)
public class TC08SearchNoteTest extends BaseEspressoTest {
    @Test
    public void SearchNote() {
        createNoteByUI("NoteTitleX", "NoteContentX");
        createNoteByUI("NoteTitleY", "TestContentY");
        createNoteByUI("NoteTitleZ", "TestContentZ");
        onView(allOf(withId(R.id.menu_search),
                withContentDescription("Search"),
                isDisplayed())).perform(click());
        //onView(withId(R.id.note_content)).check(matches(withText("TestContent")));
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("ContentX"), closeSoftKeyboard());
        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text), withText("ContentX"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());
        ViewInteraction textView = onView(
                allOf(withId(R.id.note_title), withText("NoteTitleX"),
                        withParent(withParent(withId(R.id.card_layout))),
                        isDisplayed()));
        textView.check(matches(withText("NoteTitleX")));
        textView.check(matches(not(withText("NoteTitleY"))));
        textView.check(matches(not(withText("NoteTitleZ"))));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.note_content), withText("NoteContentX"),
                        withParent(withParent(withId(R.id.card_layout))),
                        isDisplayed()));
        textView2.check(matches(withText("NoteContentX")));
        textView2.check(matches(not(withText("NoteContentY"))));
        textView2.check(matches(not(withText("NoteContentZ"))));
        //onView(withId(R.id.detail_content)).check(matches(not(withText("TestContentModifiedTest"))));
    }
}
