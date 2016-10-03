package xinQing.framework.ioc.util;

/**
 * 类型转化工具
 *
 * Created by xuan on 16-10-3.
 */
public class CastUtils {

    /**
     * String转化为boolean
     *
     * @param flag
     * @return
     */
    public static boolean cast(String flag) {
        return Boolean.parseBoolean(flag);
    }

}
