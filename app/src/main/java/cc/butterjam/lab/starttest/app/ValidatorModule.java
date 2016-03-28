package cc.butterjam.lab.starttest.app;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ValidatorModule {

    /**
     * Validators which are applied on the target one by one
     */
    private final static WordValidator[] validators = new WordValidator[]{
            new LocalValidator(), new NetworkValidator()
    };

    @Singleton
    @Provides
    WordValidator[] provideValidators() {
        return validators;
    }

}
