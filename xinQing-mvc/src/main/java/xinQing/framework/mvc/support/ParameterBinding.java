package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.annotation.Param;
import xinQing.framework.mvc.servlet.param.Http;
import xinQing.framework.mvc.servlet.param.MethodInvocation;
import xinQing.framework.mvc.util.CastUtils;
import xinQing.framework.mvc.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

/**
 * 参数绑定
 *
 * Created by xuan on 16-10-9.
 */
public class ParameterBinding {

    private static final Logger log = Logger.getLogger(ParameterBinding.class);

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
                Param param = parameters[i].getAnnotation(Param.class);
                String value = param.value();
                Class<?> type = parameters[i].getType();
                log.debug("正在绑定方法参数名：" + value + " 类型:" + type);
                // 获取到绑定的参数
                String[] values = requestParameters.get(value);
                log.debug("待绑定方法的参数值：" + Arrays.toString(values));
                args[i] = CastUtils.cast(values, type);
                log.debug("绑定后方法的参数值：" + args[i]);
            }
            // 如果被@Path标识


            // 注入原生Servlet参数
            // 如果是HttpServletRequest
            if (parameters[i].getType() == HttpServletRequest.class) {
                args[i] = http.getRequest();
            }
            // 如果是HttpServletResponse
            if (parameters[i].getType() == HttpServletResponse.class) {
                args[i] = http.getResponse();
            }
            // 如果是HttpSession
            if (parameters[i].getType() == HttpSession.class) {
                args[i] = http.getRequest().getSession();
            }

        }
        return args;
    }
}
