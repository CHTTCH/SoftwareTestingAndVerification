package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;

import it.feio.android.omninotes.R;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.models.Category;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC01AddCategoryTest extends BaseEspressoTest {

    @Test
    public void addNewCategory() {
        String categoryName = "Cat_" + Calendar.getInstance().getTimeInMillis();

        onView(Matchers.allOf(ViewMatchers.withId(R.id.fab_expand_menu_button),
                withParent(withId(R.id.fab)),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.fab_note),
                withParent(withId(R.id.fab)),
                isDisplayed())).perform(click());

        onView(
                allOf(withId(R.id.menu_category), withContentDescription(R.string.category), isDisplayed()))
                .perform(click());

        // Materialdialog "Add Category"
        onView(isRoot()).perform(waitId(R.id.md_buttonDefaultPositive, 5000));

        onView((withText(R.string.add_category))).perform(click());

        onView(withId(R.id.category_title)).perform(replaceText(categoryName), closeSoftKeyboard());

        onView(allOf(withId(R.id.save), withText("Ok"), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.detail_title),
                withParent(allOf(withId(R.id.title_wrapper),
                        withParent(withId(R.id.detail_tile_card)))),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.detail_title),
                withParent(allOf(withId(R.id.title_wrapper),
                        withParent(withId(R.id.detail_tile_card)))),
                isDisplayed())).perform(replaceText("Note with new category"), closeSoftKeyboard());

        onView(allOf(withContentDescription(R.string.drawer_open),
                withParent(withId(R.id.toolbar)),
                isDisplayed())).perform(click());

        ArrayList<Category> categories = DbHelper.getInstance().getCategories();

        assertEquals(1, categories.size());
        assertEquals(categoryName, categories.get(0).getName());
        assertEquals(categories.get(0), dbHelper.getNotesActive().get(0).getCategory());
    }

}
