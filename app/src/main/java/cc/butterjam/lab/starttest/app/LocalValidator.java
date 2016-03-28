package cc.butterjam.lab.starttest.app;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator which works locally without external dependencies
 */
class LocalValidator extends WordValidator {

    @Override
    public ValidatedWord validate(String word) {
        ValidatedWord validated = new ValidatedWord(word);

        // Just an example:
        //   Detect numbers which likely form mobile phone number
        Pattern p = Pattern.compile(".*(?:090|080).*");
        Matcher m = p.matcher(word);
        if (m.matches()) {
            validated.isValid = false;
            validated.errorMessage = "It seems to contain a mobile phone number.";
        } else {
            validated.isValid = true;
        }
        return validated;
    }

}
