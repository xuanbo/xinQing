package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.servlet.param.Http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Web应用
 *
 *
 * Created by xuan on 16-10-8.
 */
public class WebApplication {

    private static final Logger log = Logger.getLogger(WebApplication.class);

    private Configuration configuration;

    private HandlerMapping handlerMapping;

    public WebApplication(Configuration configuration) {
        this.configuration = configuration;
        this.handlerMapping = new HandlerMapping();
        processController();
    }

    /**
     * 处理Controller中url与方法之间的映射关系
     */
    public void processController() {
        ControllerScan scan = new ControllerScan(configuration);
        log.debug("扫描@Controller");
        // 获取@Controller标识的Class
        Set<Class<?>> classes = scan.loadControllerClasses();
        // 处理url映射
        handlerMapping.handlerMapping(classes);
    }

    /**
     * 处理请求
     *
     * @param request
     * @param response
     */
    public void doService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Http http = new Http(request, response);
        // 如果有方法匹配请求，则处理
        if (handlerMapping.process(http)) {
            return;
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
