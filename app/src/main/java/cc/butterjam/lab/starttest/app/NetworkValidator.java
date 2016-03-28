package cc.butterjam.lab.starttest.app;

/**
 * Validator which works with restful-API
 */
public class NetworkValidator extends WordValidator {

    public static class NetworkException extends Exception {
    }

    /**
     * Give a word to API, and get validated word
     *
     * @param word original word
     * @return validated word which may be modified by API
     */
    public String getValidatedWord(String word) throws NetworkException {
        //TODO: implement this
        return word.concat("[checked]");
    }

    @Override
    public ValidatedWord validate(String word) {
        ValidatedWord validated = new ValidatedWord();
        try {
            validated.word = getValidatedWord(word);
            validated.isValid = true;
        } catch (NetworkException e) {
            //TODO: Catch network exception
            validated.errorMessage = "Network error";
        }
        return validated;
    }

}
