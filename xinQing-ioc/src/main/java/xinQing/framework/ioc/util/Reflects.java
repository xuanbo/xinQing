package xinQing.framework.ioc.util;

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

}
