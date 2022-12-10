package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@RunWith(AndroidJUnit4.class)
public class TC09DiscardNoteChangesTest extends BaseEspressoTest {
    @Test
    public void discardNoteChanges() {
        createNoteByUI("TestTitle", "TestContent");
        onView(withText("TestTitle")).perform(click());
        onView(withText("TestContent")).perform(typeText("ModifiedTest"));
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Discard changes")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withText("TestTitle")).perform(click());

        onView(withId(R.id.detail_content)).check(matches(withText("TestContent")));
        onView(withId(R.id.detail_content)).check(matches(not(withText("TestContentModifiedTest"))));
    }
}
