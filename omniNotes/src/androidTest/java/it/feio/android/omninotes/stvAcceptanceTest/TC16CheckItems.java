package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import org.junit.Test;

public class TC16CheckItems extends BaseEspressoTest {
    @Test
    public void checkItemsInsideEditingPage() {
        createNoteByUI("TestTitle", "TestContent");
        onView(withText("TestTitle")).perform(click());
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Enable checklist")).perform(click());
        navigateUp();

        onView(withText("TestTitle")).perform(click());
        onView(allOf(
                withParent(
                        withChild(withText("TestContent"))
                ),
                withParentIndex(1)
        )).check(matches(isDisplayed()))
        .perform(click())
        .check(matches(isChecked()));
    }
}
