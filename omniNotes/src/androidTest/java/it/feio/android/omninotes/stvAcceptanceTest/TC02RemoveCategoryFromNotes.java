package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.Category;
import it.feio.android.omninotes.models.Note;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC02RemoveCategoryFromNotes extends BaseEspressoTest {

    @Test
    public void removeCategoryFromSelectedNotes() {
        String categoryName = "Cat_" + Calendar.getInstance().getTimeInMillis();
        List<String> noteTitleList = new ArrayList<>();
        noteTitleList.add("title1");
        noteTitleList.add("title2");
        noteTitleList.add("title3");

        createNoteByUI(noteTitleList.get(0), "test");
        createNoteByUI(noteTitleList.get(1), "test");
        createNoteByUI(noteTitleList.get(2), "test");

        categorizeSelectedNotes(categoryName, noteTitleList);

        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(0)))).perform(longClick());
        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(1)))).perform(longClick());
        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(2)))).perform(longClick());

        onView(allOf(withId(R.id.menu_category), withContentDescription(R.string.category), isDisplayed()))
                .perform(click());

        onView((withText(R.string.remove_category))).perform(click());

        onView(allOf(withId(R.id.note_title), withText(noteTitleList.get(0)))).perform(click());
        onView(allOf(withContentDescription(R.string.drawer_open),
                withParent(withId(R.id.toolbar)),
                isDisplayed())).perform(click());

        Category category = dbHelper.getCategories().get(0);
        List<Note> notes = dbHelper.getNotesActive();
        assertEquals(0, getCategorizedCountByCategory(category));
        assertNull(getNoteById(notes.get(0).get_id()).getCategory());
        assertNull(getNoteById(notes.get(1).get_id()).getCategory());
        assertNull(getNoteById(notes.get(2).get_id()).getCategory());
    }

}
