package xinQing.framework.mvc.servlet;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.support.Configuration;
import xinQing.framework.mvc.support.WebApplication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求分发Servlet
 *
 * Created by xuan on 16-10-5.
 */
public class DispatchServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(DispatchServlet.class);

    private WebApplication application;

    @Override
    public void init() throws ServletException {
        String configPath = getInitParameter("config");
        log.debug("读取配置文件：" + configPath);
        Configuration configuration = new Configuration();
        configuration.parse(configPath);
        application = new WebApplication(configuration);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        application.doService(req, resp);
    }

}
