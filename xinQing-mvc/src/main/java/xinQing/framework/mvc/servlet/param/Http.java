package xinQing.framework.mvc.servlet.param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by xuan on 16-10-8.
 */
public class Http {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public Http(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * 获取参数
     *
     * @return
     */
    public Map<String, String[]> getParameters() {
        return request.getParameterMap();
    }

}
