package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.annotation.*;
import xinQing.framework.mvc.model.ModelAndView;
import xinQing.framework.mvc.servlet.param.Http;
import xinQing.framework.mvc.servlet.param.MethodInvocation;
import xinQing.framework.mvc.servlet.param.RequestMethod;
import xinQing.framework.mvc.util.JsonUtil;
import xinQing.framework.mvc.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理Url映射
 *
 * Created by xuan on 16-10-8.
 */
public class HandlerMapping {

    private static final Logger log = Logger.getLogger(HandlerMapping.class);

    private static final String URL_START_WITH = "/";

    /**
     * key：url，/XX/XX
     * value：封装了调用的方法信息
     */
    private Map<String, MethodInvocation> methodInvocationMap;

    public HandlerMapping() {
        this.methodInvocationMap = new ConcurrentHashMap<>();
    }

    /**
     * 处理
     *
     * @param http
     * @return
     */
    public boolean process(Http http) {
        MatcherMapping matcherMapping = new MatcherMapping();
        // 根据请求，找到处理请求的方法
        MethodInvocation methodInvocation = matcherMapping.matcher(http, methodInvocationMap);
        if (methodInvocation == null) {
            return false;
        }
        invokeMethod(http, methodInvocation, new ParameterBinding());
        // 正确执行返回
        return true;
    }

    /**
     * 处理Url
     *
     * @param classes
     */
    public void handlerMapping(Set<Class<?>> classes) {
        classes.forEach(clazz -> {
            try {
                Object instance = clazz.newInstance();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    methodAdapter(instance, method);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 适配Controller中处理url的方法
     *
     * @param object
     * @param method
     */
    public void methodAdapter(Object object, Method method) {
        MethodInvocation invocation = new MethodInvocation();
        String value;
        if (method.isAnnotationPresent(Get.class)) {
            invocation.setRequestMethod(RequestMethod.GET);
            value = method.getAnnotation(Get.class).value();
        } else if (method.isAnnotationPresent(Post.class)) {
            invocation.setRequestMethod(RequestMethod.POST);
            value = method.getAnnotation(Post.class).value();
        } else if (method.isAnnotationPresent(Put.class)) {
            invocation.setRequestMethod(RequestMethod.PUT);
            value = method.getAnnotation(Put.class).value();
        } else if (method.isAnnotationPresent(Delete.class)) {
            invocation.setRequestMethod(RequestMethod.DELETE);
            value = method.getAnnotation(Delete.class).value();
        } else {
            return;
        }
        if (StringUtil.ISNULL(value)) {
            log.error("@Get必须指定一个匹配的url:" + method);
            return;
        }
        if (!value.startsWith(URL_START_WITH)) {
            value = URL_START_WITH + value;
        }
        invocation.setObject(object);
        invocation.setMethod(method);
        invocation.setAjax(method.isAnnotationPresent(Ajax.class));
        methodInvocationMap.put(value, invocation);
    }

    /**
     * 调用Controller中的方法
     *
     * @param http
     * @param methodInvocation
     * @param parameterBinding
     */
    public void invokeMethod(Http http, MethodInvocation methodInvocation, ParameterBinding parameterBinding) {
        // 参数绑定
        Object[] args = parameterBinding.binding(http, methodInvocation);
        try {
            Object result = methodInvocation.invoke(args);
            // 如果是ajax，写入响应
            if (methodInvocation.isAjax()) {
                ajaxResolve(http, result);
            }
            // 否则返回视图
            // 返回ModelAndView
            if (methodInvocation.getMethod().getReturnType() == ModelAndView.class) {
                modelAndViewResolve(http, (ModelAndView) result);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ajax
     *
     * @param http
     * @param result
     */
    public void ajaxResolve(Http http, Object result) {
        HttpServletResponse response = http.getResponse();
        response.setContentType("application/json");
        try {
            response.getWriter().append(JsonUtil.parse(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ModelAndView
     *
     * @param http
     * @param modelAndView
     */
    public void modelAndViewResolve(Http http, ModelAndView modelAndView) {
        HttpServletRequest request = http.getRequest();
        HttpServletResponse response = http.getResponse();
        try {
            // 设置模型
            Map<String, Object> attributes = modelAndView.getAttributes();
            attributes.forEach((key, value) -> {
                request.setAttribute(key, value);
            });
            // 请求转发
            request.getRequestDispatcher(modelAndView.getName()).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
