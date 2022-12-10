package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import it.feio.android.omninotes.R;

public class TC23SortNotesBySelectedFilter extends BaseEspressoTest {

    @Test
    public void sortNotesBySelectedFilter() {
        createNoteByUI("TestTitle1", "TestContent1");
        createNoteByUI("TestTitle2", "TestContent2");

        onView(withId(R.id.menu_sort)).perform(click());
        onView(withText("Last modification date")).perform(click());

        onView(allOf(
                withId(R.id.note_title),
                isDescendantOfA(allOf(
                        withParent(withId(R.id.list)),
                        withParentIndex(0))
                ),
                isDisplayed()
        )).check(matches(withText("TestTitle2")));

        onView(allOf(
                withId(R.id.note_title),
                isDescendantOfA(allOf(
                        withParent(withId(R.id.list)),
                        withParentIndex(1))
                ),
                isDisplayed()
        )).check(matches(withText("TestTitle1")));
    }
}
