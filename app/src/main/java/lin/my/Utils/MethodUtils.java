package lin.my.Utils;

import de.robv.android.xposed.XposedBridge;

import java.lang.reflect.Method;


public class MethodUtils {
    public static Method findMethod(Class clazz, String name) {
        if (clazz == null) return null;
        Method method = null;
        try {
            method = clazz.getMethod(name);
        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
            XposedBridge.log("找不到方法" + clazz.getName() + name + "");
        }
        return method;
    }

    public static Method findMethod(Class className, String methodName, Class[] paramTypes) {
        if (className == null) {
            return null;
        }
        Method[] methods = className.getDeclaredMethods();
        //方法列表循环 优点 比对精准 效率一般
        is:
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] paramTypes1 = method.getParameterTypes();
                if (paramTypes1.length != paramTypes.length)
                    continue;
                for (int i = 0; i < paramTypes1.length; i++) {
                    if (!ClassType.CheckClass(paramTypes1[i], paramTypes[i]))
                        continue is;
                }
                method.setAccessible(true);
                return method;
            }
        }
        XposedBridge.log("找不到方法" + className.getName() + methodName + "");
        return null;
    }

    //查找和调用方法参数 （对象名，方法名 ，调用的参数名 ）
    public static <T> T findAndCallMethod(Object obj, String name, Object... params) {
        Class[] paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }
        try {
            return findAndCallMethod(obj, obj.getClass(), name, paramTypes, params);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    //通过已经实例化的对象执行方法得到返回结果（对象，Class字节码，方法名，方法所需要的参数，执行的参数）
    public static <T> T findAndCallMethod(Object obj, Class<?> clazz, String methodName,
                                          Class<?>[] paramTypes, Object... params) throws Throwable {
        Method method = findMethod(clazz, methodName, paramTypes);
        return (T) method.invoke(obj, params);
    }

}
