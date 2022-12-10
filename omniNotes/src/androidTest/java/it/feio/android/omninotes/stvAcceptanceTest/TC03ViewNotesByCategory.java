package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static java.lang.Thread.sleep;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC03ViewNotesByCategory extends BaseEspressoTest {

    @Test
    public void viewNotesByCategory() {
        List<String> noteTitleList = new ArrayList<>();
        noteTitleList.add("title1");
        noteTitleList.add("title2");
        noteTitleList.add("title3");

        createNoteByUI(noteTitleList.get(0), "test");
        createNoteByUI(noteTitleList.get(1), "test");
        createNoteByUI(noteTitleList.get(2), "test");

        categorizeSelectedNotes("Cate1", noteTitleList.subList(0, 2));
        categorizeSelectedNotes("Cate2", noteTitleList.subList(2, 3));

        onView(allOf(withParent(withId(R.id.toolbar)), withParentIndex(0))).perform(click());

        onView(allOf(withId(R.id.title), withText("Cate1"))).perform(click());

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(0)))).check(matches(withText("title1")));
        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(1)))).check(matches(withText("title2")));

        onView(allOf(withParent(withId(R.id.toolbar)), withParentIndex(0))).perform(click());

        onView(allOf(withId(R.id.title), withText("Cate2"))).perform(click());

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(2)))).check(matches(withText("title3")));
    }

}
