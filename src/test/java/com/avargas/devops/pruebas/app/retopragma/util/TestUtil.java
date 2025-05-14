package com.avargas.devops.pruebas.app.retopragma.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestUtil {
    public static <T> T invokePrivateMethod(Object instance, String methodName, Class<T> returnType, Class<?>[] parameterTypes, Object... args) throws Exception {
        Method method = findMethod(instance.getClass(), methodName, parameterTypes);
        method.setAccessible(true);
        try {
            return returnType.cast(method.invoke(instance, args));
        } catch (InvocationTargetException e) {
            throw (Exception) e.getTargetException();
        }
    }

    private static Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException ignored) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + methodName + " not found");
    }
}
