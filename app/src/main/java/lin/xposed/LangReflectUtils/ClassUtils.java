package lin.xposed.LangReflectUtils;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.HashMap;
import java.util.Map;

public class ClassUtils {
    public static Map ClassMap = new HashMap();
    public static ClassLoader classLoader;//类加载器 可以用来加载类

    public static void setLoadPackageParam(XC_LoadPackage.LoadPackageParam loadPackageParam1) {
        classLoader = loadPackageParam1.classLoader;
    }

    public static Class<?> getClass(String className) {
        Class clazz;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            XposedBridge.log("找不到类" + e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

}
