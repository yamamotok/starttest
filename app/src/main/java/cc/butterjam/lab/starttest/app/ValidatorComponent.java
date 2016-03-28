package cc.butterjam.lab.starttest.app;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ValidatorModule.class)
public abstract class ValidatorComponent extends BaseComponent {

    public abstract void inject(MainActivity mainActivity);

}
