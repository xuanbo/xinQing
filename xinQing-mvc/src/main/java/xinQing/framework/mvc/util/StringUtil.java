package xinQing.framework.mvc.util;

/**
 * Created by xuan on 16-10-8.
 */
public class StringUtil {

    /**
     * 是否为null
     *
     * @param str
     * @return
     */
    public static boolean ISNULL(String str) {
        if (str == null || str.isEmpty())
            return true;
        return false;
    }
}
