package org.pigeon.common.util;

import java.lang.reflect.Method;

public class ReflectUtil {

    public static String getMethodSign(Class clazz, Method method) {

        StringBuilder builder = new StringBuilder(clazz.getName() + method.getName());

        for (Class c : method.getParameterTypes())
            builder.append(c.getName());

        return EncryptUtil.md532(builder.toString());
    }

}
