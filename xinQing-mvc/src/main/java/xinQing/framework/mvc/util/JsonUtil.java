package xinQing.framework.mvc.util;

import com.google.gson.Gson;

/**
 * json工具
 *
 * Created by xuan on 16-10-9.
 */
public class JsonUtil {

    /**
     * 对象转为json字符串
     *
     * @param object
     * @return
     */
    public static String parse(Object object) {
        Gson gson = new Gson();
        if (object instanceof String) {
            return (String) object;
        }
        return gson.toJson(object);
    }
}
