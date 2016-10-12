package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.servlet.param.Http;
import xinQing.framework.mvc.servlet.param.MethodInvocation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配映射
 *
 * Created by xuan on 16-10-8.
 */
public class MatcherMapping {

    private static final Logger log = Logger.getLogger(MatcherMapping.class);

    /**
     * 根据请求精确匹配的Method
     *
     * @param http
     * @param methodInvocationMap
     * @return
     */
    public MethodInvocation matcher(Http http, Map<String, MethodInvocation> methodInvocationMap) {
        String path = http.getRequest().getServletPath();
        MethodInvocation methodInvocation;
        // 精确匹配
        for (Map.Entry<String, MethodInvocation> entry : methodInvocationMap.entrySet()) {
            methodInvocation = entry.getValue();
            String url = entry.getKey();
            String requestMethod = methodInvocation.getRequestMethod();
            if (path.equals(url) && http.getRequest().getMethod().equalsIgnoreCase(requestMethod)) {
                log.debug(path + " : " + requestMethod + " -> " + methodInvocation.getMethod());
                return methodInvocation;
            }
        }
        // 找不到匹配
        log.error("Controller中找不到精确匹配请求的方法:" + path + " " + http.getRequest().getMethod());
        return null;
    }

    /**
     * restful 匹配
     *
     * @param http
     * @param methodInvocationMap
     * @return
     */
    public Map<String, Object> restfulMatcher(Http http, Map<String, MethodInvocation> methodInvocationMap) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> matcherMap;
        String requestPath = http.getRequest().getServletPath();
        MethodInvocation methodInvocation;
        for (Map.Entry<String, MethodInvocation> entry : methodInvocationMap.entrySet()) {
            methodInvocation = entry.getValue();
            String url = entry.getKey();
            String requestMethod = methodInvocation.getRequestMethod();
            if (!http.getRequest().getMethod().equalsIgnoreCase(requestMethod)) {
                continue;
            }
            matcherMap = restfulMatcher(url, requestPath);
            if (matcherMap != null) {
                result.put("restful", matcherMap);
                result.put("methodInvocation", methodInvocation);
                return result;
            }
        }
        return null;
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
