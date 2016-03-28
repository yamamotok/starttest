package cc.butterjam.lab.starttest.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseComponent {

    public static class ComponentInjectionException extends RuntimeException {
        public ComponentInjectionException(Throwable throwable) {
            super(throwable);
        }
    }

    private Method findInjector(Class target) {
        Method ret = null;
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (!method.getName().equals("inject")) continue;
            Class[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1) continue;
            if (paramTypes[0].equals(target)) {
                ret = method;
                break;
            }
        }
        return ret;
    }

    public boolean injectInto(Object target) {
        Method method = findInjector(target.getClass());
        if (method == null) return false;
        try {
            method.invoke(this, target);
        } catch (IllegalAccessException e) {
            throw new ComponentInjectionException(e);
        } catch (InvocationTargetException e) {
            throw new ComponentInjectionException(e);
        }
        return true;
    }

}
