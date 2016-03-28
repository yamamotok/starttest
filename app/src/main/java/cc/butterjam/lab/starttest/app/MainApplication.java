package cc.butterjam.lab.starttest.app;

import android.app.Application;

public class MainApplication extends Application {

    public final ComponentManager componentManager = new ComponentManager();

    @Override
    public void onCreate() {
        super.onCreate();

        // Dependencies, which will be injected into objects in this application
        componentManager.registerComponent(DaggerValidatorComponent.create());
    }
}
