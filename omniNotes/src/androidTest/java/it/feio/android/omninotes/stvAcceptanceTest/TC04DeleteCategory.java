package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static java.lang.Thread.sleep;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC04DeleteCategory extends BaseEspressoTest {

    @Test
    public void categoryDeletion() {
        String categoryName = "Cat_" + Calendar.getInstance().getTimeInMillis();
        List<String> noteTitleList = new ArrayList<>();
        noteTitleList.add("title1");

        createNoteByUI(noteTitleList.get(0), "");
        categorizeSelectedNotes(categoryName, noteTitleList);

        onView(allOf(withContentDescription(R.string.drawer_open),
                withParent(withId(R.id.toolbar)),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.title), withText(categoryName))).perform(longClick());

        onView(allOf(withId(R.id.delete), withText(R.string.delete), isDisplayed())).perform(click());

        onView(withText(R.string.confirm)).perform(click());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.title), withText(categoryName))).check(doesNotExist());
    }

}
