package xinQing.framework.mvc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuan on 16-10-12.
 */
public class AppTest {

    @Test
    public void restful() {
        String url = "/user/:id/index";
        String path = "/user/1/index";
        restfulMatcher(url, path).forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
    }

    /**
     * restful url 匹配
     * 如果匹配，返回一个key为path，value为路径值的map
     * 例如url为/user/:id/index，requestPath为/user/1/index，那么返回一个key为id，value为1的map
     *
     * @param url restful url，例如/user/:id/index
     * @param requestPath 请求路径，例如/user/1/index
     * @return
     */
    public Map<String, String> restfulMatcher(String url, String requestPath) {
        Map<String, String> matcherMap = new HashMap<>();
        String[] urls = url.split("/");
        String[] requestPaths = requestPath.split("/");
        if (urls.length != requestPaths.length) {
            return null;
        }
        for (int i = 0; i < urls.length; i++) {
            String p = urls[i];
            if (urls[i].startsWith(":")) {
                p = ".+";
                urls[i] = urls[i].substring(1);
            }
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(requestPaths[i]);
            if (!matcher.find()) {
                return null;
            }
            if (p.equals(".+")) {
                matcherMap.put(urls[i], matcher.group());
            }
        }
        return matcherMap;
    }
}
