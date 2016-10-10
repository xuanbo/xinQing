#	简单的MVC

###	使用

* web.xml

```java
    <servlet>
        <servlet-name>dispatchServlet</servlet-name>
        <servlet-class>xinQing.framework.mvc.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>config</param-name>
          	<!-- 配置文件classpath下 -->
            <param-value>app.properties</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatchServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

* app.properties配置扫描Controller

```java
beanScan.packageName=xinQing.framework.mvc.test.controller
beanScan.recursive=true
```

`beanScan.recursive`是否扫描子包

* Controller

```java
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
      	// 返回视图
        modelAndView.setName("/index.jsp");
      	// 值
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
```

`@Controller`标注为一个Controller

`@Get`GET请求

`@Ajax`返回json

`@Param`绑定参数

其中: `@Post`、`@Put`、`@Delete`我还没有测试



###	注意

- 尝试写的这个mvc，不打算加入拦截器等，只处理请求，调用Controller中的方法，返回响应。
- restful还不支持(后续加上)
- 文件上传也不支持
- 不支持请求参数绑定到java对象
- 不支持返回视图定义前缀后缀
- 不支持参数校验
- 很多都不支持，单纯做了请求分发，方法响应



###	总结

我站在巨人的肩上，而我不是巨人。只有知己尝试去写点东西，才知道自己的渺小，前辈的伟大。