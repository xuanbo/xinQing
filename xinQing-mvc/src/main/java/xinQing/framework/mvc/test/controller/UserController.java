package xinQing.framework.mvc.test.controller;

import xinQing.framework.mvc.annotation.Ajax;
import xinQing.framework.mvc.annotation.Controller;
import xinQing.framework.mvc.annotation.Get;
import xinQing.framework.mvc.annotation.Param;

/**
 * Created by xuan on 16-10-8.
 */
@Controller
public class UserController {

    @Get("/index")
    @Ajax
    public String index(@Param String name) {
        return "index";
    }
}
