//package it.feio.android.omninotes.ui;
//public class TC14DeleteNotesInTrash {
//}
package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@RunWith(AndroidJUnit4.class)
public class TC14DeleteNotesInTrash extends BaseEspressoTest {

    @Test
    public void deleteNotesInTrash() {
        createNoteByUI("TestTitle1", "TestContent1");
        createNoteByUI("TestTitle2", "TestContent2");

        onView(withText("TestTitle1")).perform(longClick());
        onView(withText("TestTitle2")).perform(click());
        onView(allOf(
                withContentDescription("More options"),
                allOf(
                        withParent(
                                allOf(
                                        withParent(withId(R.id.action_mode_bar)),
                                        withParentIndex(2)
                                )
                        ),
                        withParentIndex(2)
                ),
                isDisplayed()
        )).perform(click());
        onView(withText("Trash")).perform(click());
//        onView(withText(R.string.note_trashed)).perform(click());
        onView(allOf(
                withContentDescription("drawer open"),
                allOf(
                        withParent(withId(R.id.toolbar)),
                        withParentIndex(0)
                ),
                isDisplayed()
        )).perform(click());
        onView(withText("Trash")).perform(click());

        onView(withText("TestTitle1")).perform(longClick());
//        onView(withText("TestTitle2")).perform(click());
        onView(allOf(
                withContentDescription("Delete"),
                allOf(
                        withParent(
                                allOf(
                                        withParent(withId(R.id.action_mode_bar)),
                                        withParentIndex(2)
                                )
                        ),
                        withParentIndex(2)
                ),
                isDisplayed()
        )).perform(click());
        onView(withText("OK")).perform(click());

        onView(withId(R.id.note_title)).check(matches(not(withText("TestTitle1"))));
        onView(withId(R.id.note_title)).check(matches(withText("TestTitle2")));
    }
}