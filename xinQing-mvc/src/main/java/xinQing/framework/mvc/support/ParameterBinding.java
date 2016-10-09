package xinQing.framework.mvc.support;

import xinQing.framework.mvc.annotation.Param;
import xinQing.framework.mvc.servlet.param.Http;
import xinQing.framework.mvc.servlet.param.MethodInvocation;
import xinQing.framework.mvc.util.CastUtils;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

/**
 * 参数绑定
 *
 * Created by xuan on 16-10-9.
 */
public class ParameterBinding {

    /**
     * 绑定参数
     *
     * @param http
     * @param methodInvocation
     * @return
     */
    public Object[] binding(Http http, MethodInvocation methodInvocation) {
        // 获取请求的参数
        Map<String, String[]> requestParameters = http.getParameters();
        System.out.println("=======================");
        requestParameters.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(Arrays.toString(value));
        });
        // 获取方法的参数
        Parameter[] parameters = methodInvocation.getMethod().getParameters();
        if (parameters.length == 0) {
            return null;
        }
        int len = parameters.length;
        Object[] args = new Object[len];
        for (int i = 0; i < len; i++) {
            // 如果被@Param标识
            if (parameters[i].isAnnotationPresent(Param.class)) {
                // 获取到绑定的参数
                String[] values = requestParameters.get(parameters[i].getName());
                args[i] = CastUtils.cast(values, parameters[i].getType());
            }
            // 如果被

        }
        return args;
    }
}
