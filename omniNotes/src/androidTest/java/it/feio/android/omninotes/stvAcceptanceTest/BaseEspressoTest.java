/*
 * Copyright (C) 2013-2020 Federico Iosue (federico@iosue.it)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.feio.android.omninotes.stvAcceptanceTest;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;

import java.util.List;
import java.util.concurrent.TimeoutException;

import it.feio.android.omninotes.BaseAndroidTestCase;
import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.utils.ClickWithoutDisplayConstraint;


public class BaseEspressoTest extends BaseAndroidTestCase {

  @Rule
  public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class,
      false, false);

  static Matcher<View> childAtPosition(
      final Matcher<View> parentMatcher, final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }


      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
            && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }

  @Before
  public void setUp() {
    activityRule.launchActivity(null);
  }

  void selectNoteInList(int number) {
    onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(number, click()));
  }

  void navigateUp() {
    onView(allOf(childAtPosition(allOf(withId(R.id.toolbar),
        childAtPosition(withClassName(is("android.widget.RelativeLayout")), 0)
    ), 0), isDisplayed())).perform(click());
  }

  void navigateUPSearch() {
    onView(allOf(childAtPosition(allOf(withId(R.id.toolbar),
        childAtPosition(withClassName(is("android.widget.RelativeLayout")), 0)
    ), 1), isDisplayed())).perform(click());
  }

  void navigateUpSettings() {
    onView(allOf(withContentDescription(R.string.abc_action_bar_up_description),
        childAtPosition(allOf(withId(R.id.toolbar),
            childAtPosition(withClassName(is("android.widget.RelativeLayout")), 0)),
            1), isDisplayed())).perform(click());
  }

  /**
   * Perform action of waiting for a specific view id.
   *
   * @param viewId The id of the view to wait for.
   * @param millis The timeout of until when to wait for.
   */
  public static ViewAction waitId(final int viewId, final long millis) {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints() {
        return isRoot();
      }

      @Override
      public String getDescription() {
        return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
      }

      @Override
      public void perform(final UiController uiController, final View view) {
        uiController.loopMainThreadUntilIdle();
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + millis;
        final Matcher<View> viewMatcher = withId(viewId);

        do {
          for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
            if (viewMatcher.matches(child)) {
              return;
            }
          }

          uiController.loopMainThreadForAtLeast(50);
        }
        while (System.currentTimeMillis() < endTime);

        throw new PerformException.Builder()
            .withActionDescription(this.getDescription())
            .withViewDescription(HumanReadables.describe(view))
            .withCause(new TimeoutException())
            .build();
      }
    };
  }

  ClickWithoutDisplayConstraint getClickAction() {
    return new ClickWithoutDisplayConstraint(
        Tap.SINGLE,
        GeneralLocation.VISIBLE_CENTER,
        Press.FINGER,
        InputDevice.SOURCE_UNKNOWN,
        MotionEvent.BUTTON_PRIMARY);
  }

  ClickWithoutDisplayConstraint getLongClickAction() {
    return new ClickWithoutDisplayConstraint(
        Tap.LONG,
        GeneralLocation.CENTER,
        Press.FINGER,
        InputDevice.SOURCE_UNKNOWN,
        MotionEvent.BUTTON_PRIMARY);
  }

  ViewAction getSwipeAction(final int fromX, final int fromY, final int toX, final int toY) {
    return ViewActions.actionWithAssertions(
        new GeneralSwipeAction(
            Swipe.SLOW,
            view -> new float[]{fromX, fromY},
            view -> new float[]{toX, toY},
            Press.FINGER));
  }

  protected void openDrawer() {
    onView(allOf(withContentDescription("drawer open"),
        childAtPosition(
            allOf(withId(R.id.toolbar),
                childAtPosition(
                    withClassName(is("android.widget.RelativeLayout")),
                    0)),
            1),
        isDisplayed())).perform(click());
  }

  protected void navigateTo(int menuPosition) {
    openDrawer();
    onData(anything())
        .inAdapterView(allOf(withId(R.id.drawer_nav_list),
            childAtPosition(
                withId(R.id.left_drawer),
                1)))
        .atPosition(menuPosition).perform(scrollTo(), click());
  }

  protected void createNoteByUI(String title, String content) {
    ViewInteraction viewInteraction = onView(
            Matchers.allOf(ViewMatchers.withId(R.id.fab_expand_menu_button),
                    withParent(withId(R.id.fab)),
                    isDisplayed()));

    if (activityRule.getActivity().getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
      viewInteraction.perform(click());
    }
    viewInteraction.perform(click());

    onView(allOf(withId(R.id.fab_note),
            withParent(withId(R.id.fab)),
            isDisplayed())).perform(click());

    onView(allOf(withId(R.id.detail_title),
            withParent(allOf(withId(R.id.title_wrapper),
                    withParent(withId(R.id.detail_tile_card)))),
            isDisplayed())).perform(click());

    onView(allOf(withId(R.id.detail_title),
            withParent(allOf(withId(R.id.title_wrapper),
                    withParent(withId(R.id.detail_tile_card)))),
            isDisplayed())).perform(replaceText(title), closeSoftKeyboard());

    onView(withId(R.id.detail_content))
            .perform(scrollTo(), replaceText(content), closeSoftKeyboard());

    navigateUp();
  }

  protected void categorizeSelectedNotes(String categoryName, List<String> noteTitleList) {
    for (String title: noteTitleList) {
      onView(allOf(withId(R.id.note_title), withText(title))).perform(longClick());
    }

    onView(allOf(withId(R.id.menu_category), withContentDescription(R.string.category), isDisplayed()))
            .perform(click());

    onView((withText(R.string.add_category))).perform(click());

    onView(withId(R.id.category_title)).perform(replaceText(categoryName), closeSoftKeyboard());

    onView(allOf(withId(R.id.save), withText("Ok"), isDisplayed())).perform(click());
  }

}
