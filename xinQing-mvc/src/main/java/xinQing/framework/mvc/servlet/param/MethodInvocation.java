package xinQing.framework.mvc.servlet.param;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 封装Controller中的方法信息
 *
 * Created by xuan on 16-10-7.
 */
public class MethodInvocation {

    // 方法
    private Method method;

    private Object object;

    private boolean isAjax;

    private String requestMethod;

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, args);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isAjax() {
        return isAjax;
    }

    public void setAjax(boolean ajax) {
        isAjax = ajax;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
