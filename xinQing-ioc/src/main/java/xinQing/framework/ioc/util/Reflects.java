package xinQing.framework.ioc.util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 反射工具类
 *
 * Created by xuan on 16-10-3.
 */
public class Reflects {

    /**
     * 根据className创建对象
     *
     * @param className
     * @return
     */
    public static Object newInstance(String className) {
        Object object = null;
        try {
            Class<?> clazz = Class.forName(className);
            object = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 获取CLass的父类型和接口类型
     * 不获取泛型
     *
     * @param clazz
     * @return
     */
    public static void getSuperClassesAndInterfaces(Class<?> clazz, List<String> superClassesAndInterfaces) {
        Class<?>[] interfaces = clazz.getInterfaces();
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            superClassesAndInterfaces.add(superclass.getName());
            getSuperClassesAndInterfaces(superclass, superClassesAndInterfaces);
        }
        if (interfaces != null || interfaces.length > 0) {
            for (Class c : interfaces) {
                superClassesAndInterfaces.add(c.getName());
                getSuperClassesAndInterfaces(c, superClassesAndInterfaces);
            }
        }
    }

    /**
     * 根据属性设置值
     *
     * @param field
     * @param object
     * @param value
     */
    public static void setValueByField(Field field, Object object, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);
    }

}
