package xinQing.framework.mvc.servlet.param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
