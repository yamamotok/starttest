package cc.butterjam.lab.starttest.app;

/**
 * Base class of validators
 */
public abstract class WordValidator {

    public abstract ValidatedWord validate(String word);

    public ValidatedWord validate(ValidatedWord validatedWord) {
        if (validatedWord == null) {
            validatedWord = new ValidatedWord("");
        }
        return validate(validatedWord.word);
    }
}
