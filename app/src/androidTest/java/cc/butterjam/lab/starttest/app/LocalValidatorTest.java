package cc.butterjam.lab.starttest.app;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for LocalValidator
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LocalValidatorTest {

    private LocalValidator validator;

    @Before
    public void setup() {
        validator = new LocalValidator();
    }

    @Test
    public void testValidate() {
        ValidatedWord validated;

        // Phone number is OK
        validated = validator.validate("Call me, 03-3300-0001");
        assertTrue(validated.isValid);

        // Mobile phone is bad
        validated = validator.validate("Call me, 090-8000-0001");
        assertFalse(validated.isValid);
    }

}
