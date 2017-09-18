package org.pigeon.common.util;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectUtil {

    public static String getMethodSign(Class clazz, Method method) {
        StringBuilder builder = new StringBuilder(clazz.getName() + method.getName());
        Arrays.stream(method.getParameterTypes()).forEach((c) -> builder.append(c.getName()));
        return EncryptUtil.md532(builder.toString());
    }

    public static String getMethodSign(String className, String methodName, Object[] paramterTypes) {
        StringBuilder builder = new StringBuilder(className + methodName);
        if (null != paramterTypes)
            Arrays.stream(paramterTypes).forEach((o -> builder.append(o.toString())));
        return EncryptUtil.md532(builder.toString());
    }
}
