package lin.xposed.Utils;

import java.lang.reflect.Constructor;

public class ConstructorUtils {
    public static Constructor findConstructor(Class clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //@SuppressLint("SuspiciousIndentation")
    public static Constructor findConstructor(Class clazz, Class[] paramTypes) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        co:
        for (int i = 0; i < constructors.length; i++) {
            Class[] paramTypes1 = constructors[i].getParameterTypes();
            if (paramTypes.length != paramTypes1.length) continue;
            for (int j = 0; j < paramTypes1.length; j++) {
                if (!ClassType.CheckClass(paramTypes[j], paramTypes1[j])) continue co;
            }
            constructors[i].setAccessible(true);
            return constructors[i];
        }
        return null;
    }

    public static <T> T NewInstance(Constructor constructor, Object... objects) throws Exception {

        return (T) constructor.newInstance(objects);

    }

    public static <T> T NewInstance(Class<?> clz, Class<?>[] paramTypes, Object... params) throws Exception {
        Loop:
        for (Constructor<?> con : clz.getDeclaredConstructors()) {
            Class<?>[] CheckParam = con.getParameterTypes();
            if (CheckParam.length != paramTypes.length) continue;
            for (int i = 0; i < paramTypes.length; i++) {
                if (!ClassType.CheckClass(CheckParam[i], paramTypes[i])) {
                    continue Loop;
                }
            }
            con.setAccessible(true);
            return (T) con.newInstance(params);
        }
        throw new RuntimeException("No Instance for " + clz);
    }

}
