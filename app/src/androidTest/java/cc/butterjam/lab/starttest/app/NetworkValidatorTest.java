package cc.butterjam.lab.starttest.app;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class NetworkValidatorTest {
    private NetworkValidator validator;

    @Before
    public void setup() {
        validator = new NetworkValidator();
        validator = spy(validator);
    }

    @Test
    public void testNetworkError() {
        String wordTested = "Hello";

        // Mock a method which calls external API
        try {
            when(validator.getValidatedWord(wordTested)).thenThrow(new NetworkValidator.NetworkException());
        } catch (NetworkValidator.NetworkException e) {
            // Exception will be handled inside the class
        }

        ValidatedWord validated = validator.validate(wordTested);
        assertFalse(validated.isValid);
        assertFalse(validated.errorMessage.isEmpty());
    }
}
