package it.feio.android.omninotes.stvAcceptanceTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static java.lang.Thread.sleep;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC20AddTagsToNote extends BaseEspressoTest {

    @Test
    public void numTagSpecTag(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#1tag!abc";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.no_tags_created)).check(matches(isDisplayed()));
        onView(withText(R.string.select_tags)).check(doesNotExist());
    }

    @Test
    public void specTagNumTag(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#@tag2abc";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.no_tags_created)).check(matches(isDisplayed()));
        onView(withText(R.string.select_tags)).check(doesNotExist());
    }

    @Test
    public void tagNumSpec(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#tag2@";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.select_tags)).inRoot(isDialog());
        ViewInteraction tag_retrieve = onView(
                allOf(withId(R.id.md_title), withText("tag2 (1)"),
                        withParent(withParent(withId(R.id.md_contentRecyclerView))),
                        isDisplayed()));
        tag_retrieve.check(matches(withText("tag2 (1)")));
    }

    @Test
    public void tagNum(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#tag3";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.select_tags)).inRoot(isDialog());
        ViewInteraction tag_retrieve = onView(
                allOf(withId(R.id.md_title), withText("tag3 (1)"),
                        withParent(withParent(withId(R.id.md_contentRecyclerView))),
                        isDisplayed()));
        tag_retrieve.check(matches(withText("tag3 (1)")));
    }

    @Test
    public void tag(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#tag";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.select_tags)).inRoot(isDialog());
        ViewInteraction tag_retrieve = onView(
                allOf(withId(R.id.md_title), withText("tag (1)"),
                        withParent(withParent(withId(R.id.md_contentRecyclerView))),
                        isDisplayed()));
        tag_retrieve.check(matches(withText("tag (1)")));
    }

    @Test
    public void tagNumTag(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#tag2tag";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.select_tags)).inRoot(isDialog());
        ViewInteraction tag_retrieve = onView(
                allOf(withId(R.id.md_title), withText("tag2tag (1)"),
                        withParent(withParent(withId(R.id.md_contentRecyclerView))),
                        isDisplayed()));
        tag_retrieve.check(matches(withText("tag2tag (1)")));
    }

    @Test
    public void charOnly(){
        String pre_content = "This is the content.";
        createNoteByUI("Note Title", pre_content);

        onView(withText("Note Title")).perform(click());

        String added_tag_content = pre_content + "\n" + "#a";
        ViewInteraction enter_tag = onView(
                allOf(withId(R.id.detail_content),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_detail_content),
                                        1),
                                0)));
        enter_tag.perform(scrollTo(), replaceText(added_tag_content), closeSoftKeyboard());

        // Waiting a little to ensure Eventbus post propagation
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.root),
                        childAtPosition(
                                allOf(withId(R.id.list),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_tag), withContentDescription("Add tags"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        onView(withText(R.string.no_tags_created)).check(matches(isDisplayed()));
        onView(withText(R.string.select_tags)).check(doesNotExist());
    }
}
