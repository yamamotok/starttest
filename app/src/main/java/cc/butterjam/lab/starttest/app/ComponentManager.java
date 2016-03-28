package cc.butterjam.lab.starttest.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComponentManager {
    private final HashMap<Class, BaseComponent> componentMap = new HashMap<Class, BaseComponent>();

    public void registerComponent(BaseComponent component) {
        if (componentMap.containsKey(component.getClass())) return;
        componentMap.put(component.getClass(), component);
    }

    public void unregisterComponent(BaseComponent component) {
        componentMap.remove(component.getClass());
    }

    public void unregisterComponent(Class someClass) {
        componentMap.remove(someClass);
    }

    public void injectComponentsInto(Object target) {
        for (BaseComponent component : componentMap.values()) {
            component.injectInto(target);
        }
    }

}
