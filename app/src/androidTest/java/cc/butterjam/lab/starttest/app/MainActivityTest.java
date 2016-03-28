package cc.butterjam.lab.starttest.app;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        application = (MainApplication) instrumentation.getTargetContext().getApplicationContext();
    }

    private MainApplication application;

    /**
     * Try to input several values through UI, and check if they are validated correctly.
     */
    @Test
    public void testValidation() {
        // Preparation

        // Replace dependencies and inject them again
        application.componentManager.unregisterComponent(DaggerValidatorComponent.class);
        application.componentManager.registerComponent(DaggerMainActivityTest_ValidatorComponentMock.create());
        application.componentManager.injectComponentsInto(activityTestRule.getActivity());

        // UI Test

        // Words which will be input through UI
        final String[] samples = new String[5];

        // Add words to the list/**/
        for (int i = 0; i < samples.length; i++) {
            samples[i] = String.format("%s-%d", "Hello From Test", i);
            onView(withId(R.id.text_input)).perform(typeText(samples[i]), closeSoftKeyboard());
            onView(withId(R.id.button_input)).perform(click());
        }

        // Check words in the list
        for (int i = 0; i < samples.length; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_words))
                    .atPosition(i).check(matches(withText(startsWith(samples[i]))));
        }

    }

    //{{{ Mock objects which will be injected to the activity instead of real ones

    /**
     * Mock ValidatorComponent to skip network access
     */
    @Singleton
    @Component(modules = ValidatorModuleMock.class)
    public abstract static class ValidatorComponentMock extends BaseComponent {

        public abstract void inject(MainActivity mainActivity);

    }

    @Module
    public static class ValidatorModuleMock {

        /**
         * Use _mocked_ NetworkValidator instead of real NetworkValidator
         */
        private final static WordValidator[] validators = new WordValidator[]{
                new LocalValidator(), new NetworkValidatorMock()
        };

        @Singleton
        @Provides
        WordValidator[] provideValidators() {
            return validators;
        }

    }

    private static class NetworkValidatorMock extends NetworkValidator {

        @Override
        public String getValidatedWord(String word) throws NetworkException {
            return word.concat("[mock]");
        }

    }

    //}}}
}
