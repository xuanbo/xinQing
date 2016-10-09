package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.servlet.param.Http;
import xinQing.framework.mvc.servlet.param.MethodInvocation;

import java.util.Map;

/**
 * 匹配映射
 *
 * Created by xuan on 16-10-8.
 */
public class MatcherMapping {

    private static final Logger log = Logger.getLogger(MatcherMapping.class);

    /**
     * 根据请求获取匹配的Method
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
        // restful匹配

        // 找不到匹配
        log.error("Controller中找不到处理请求的方法:" + path + " " + http.getRequest().getMethod());
        return null;
    }

}
