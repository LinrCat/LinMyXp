package lin.xposed.LangReflectUtils;

import java.lang.reflect.Field;

public class FieIdUtils {
    //获得该对象的第一个这个类型的属性
    public static <T> T getTypeFirstFieId(Object obj, Class clz, Class Type) throws Exception {
        Class clazz = clz;//加载类模板
        while (clz != null) {
            for (Field field : clz.getDeclaredFields()) {
                if (Type == field.getType()) {
                    field.setAccessible(true);
                    return (T) field.get(obj);
                }
            }
        }
        clz = clz.getSuperclass();
        throw new RuntimeException("yi");
    }
}
