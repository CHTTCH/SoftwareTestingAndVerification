package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class TC06ModifyNoteTest  extends BaseEspressoTest {
    @Test
    public void modifyNote() {
        createNoteByUI("TestTitle", "TestContent");
        onView(withText("TestTitle")).perform(click());
        onView(withText("TestContent")).perform(typeText("Modified"));

        navigateUp();
        onView(withText("TestTitle")).perform(click());
        onView(withId(R.id.detail_content)).check(matches(withText("TestContentModified")));
        onView(withId(R.id.detail_content)).check(matches(not(withText("TestContent"))));
    }
}
