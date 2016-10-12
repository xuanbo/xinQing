package xinQing.framework.mvc.test.controller;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.annotation.*;
import xinQing.framework.mvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Target;

/**
 * Created by xuan on 16-10-8.
 */
@Controller
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    /**
     * 参数注入
     *
     * @param name
     * @return
     */
    @Get("/index")
    @Ajax
    public Test index(@Param("name") String name) {
        Test index = new Test();
        index.setName(name).setUrl("index");
        return index;
    }

    /**
     * 返回ModelAndView
     *
     * @return
     */
    @Get("/mv")
    public ModelAndView mv() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setName("/index.jsp");
        modelAndView.setAttributes("name", "xinQing mvc");
        return modelAndView;
    }

    /**
     * 原生Servlet参数注入
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @Get("/servlet")
    @Ajax
    public String servlet(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "request:" + request + "\nresponse" +response + "\nsession:" + session;
    }

    /**
     * restful风格
     *
     * @param id
     * @return
     */
    @Get("/user/:id")
    @Ajax
    public String restful(@Path("id") Integer id) {
        log.debug(id);
        return "id:" + id;
    }

    class Test {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public Test setName(String name) {
            this.name = name;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Test setUrl(String url) {
            this.url = url;
            return this;
        }
    }
}
