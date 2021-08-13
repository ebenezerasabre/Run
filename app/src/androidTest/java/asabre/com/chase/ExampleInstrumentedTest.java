package asabre.com.chase;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import asabre.com.chase.view.ui.MainActivity;
import asabre.com.chase.view.ui.MapFragment;
import kotlin.jvm.JvmField;

import static com.google.common.truth.Truth.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {



    @Rule
//    @JvmField
    public ActivityScenarioRule<MainActivity> mMainActivityActivityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

//    public ActivityScenario<MainActivity> mActivityScenario =  mActivityScenario = ActivityScenario.launch(new Intent(this, MainActivity.class));
//

//    @Rule
//    public FragmentScenario<MapFragment> mMapFragmentFragmentScenario = mMapFragmentFragmentScenario = FragmentScenario.launch(MapFragment.class);


//    @Rule
//    @JvmField
//    public ActivityScenario mainFrageRule = new ActivityScenario<Fragment>(MapFragment.class)

//    Bundle args = new Bundle(0);
//    private FragmentScenario<MapFragment> mFragmentScenario = new FragmentScenario<MapFragment>(MapFragment.class);


    @Before
    public void setUp(){

//        mActivityScenario = ActivityScenario.launch(MainActivity.class);

//        mActivityScenario.onActivity(activity -> {
//            assertThat(activity.getSupportFragmentManager().getFragments().contains(new MapFragment())).isTrue();
//        });
//
//
//        mMapFragmentFragmentScenario = FragmentScenario.launch(MapFragment.class);
    }

    @After
    public void tearDown(){


    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("asabre.com.chase", appContext.getPackageName());
    }

    @Test
    public void showCoords(){
//        onView(withId(R.id.showCoords)).perform(typeText("Grace Empire"));

    }

    @Test
    public void whereAreYouGoing(){
//        onView(withId(R.id.goingWhere)).perform(click());


    }







}