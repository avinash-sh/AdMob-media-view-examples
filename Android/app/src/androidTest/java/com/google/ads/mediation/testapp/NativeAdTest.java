package com.google.ads.mediation.testapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NativeAdTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void nativeAdTest() {
        ViewInteraction appCompatCheckBox = onView(withId(R.id.content_ad_check_box));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(withId(R.id.app_install_ad_check_box));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(withId(R.id.load_native_ad_button));
        appCompatButton.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(withId(R.id.ai_ad_headline_text_view));
        textView.check(matches(isDisplayed()));

        ViewInteraction frameLayout = onView(withId(R.id.adMediaView));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(withId(R.id.ai_ad_body_text_view));
        textView2.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(withId(R.id.ai_ad_icon_image_view));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(withId(R.id.ai_ad_icon_image_view));
        imageView2.check(matches(isDisplayed()));
        imageView2.perform(ViewActions.swipeUp());


        ViewInteraction button = onView(withId(R.id.ai_ad_call_to_action_text_view));
        button.check(matches(isDisplayed()));
        button.perform(ViewActions.swipeUp());

        ViewInteraction textView3 = onView(withId(R.id.ai_ad_star_rating_text_view));
        textView3.check(matches(isDisplayed()));
        textView3.perform(ViewActions.swipeUp());

        ViewInteraction textView4 = onView(withId(R.id.ai_ad_store_text_view));
        textView4.perform(ViewActions.swipeUp());
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(withId(R.id.log_text_view));
        textView5.check(matches(withText(containsString("Ad loaded."))));

        button.perform(ViewActions.click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pressBack();

    }

    private static Matcher<View> childAtPosition(
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
}
