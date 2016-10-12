#	简单的MVC

###	原理

扫描@Controller，解析Controller中的方法，得到url与method的映射关系。处理请求时，首先精确匹配，从映射关系中找到处理该请求的方法，然后获取请求中的参数，对方法中的参数进行注入，调用方法，然后判断是否@Ajax，如果是，则把结果输出到Response，否则，请求转发到视图，并设置request的Attribute。然后才进行模糊匹配，也就是restful风格的处理。分析Controller方法中url的restful参数，把url中的`:XX`换成`.+`，然后根据正则进行匹配，匹配成功后把`:xx`中的`xx`作为map的key，请求路径中匹配这一项的值，作为map的value，这样就得到了restful风格关于参数和url的映射。如果找不到处理请求的方法，那么就返回404响应专状态码。

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
    
    <!-- 静态资源，必须加上，框架不处理静态资源 -->
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.css</url-pattern>
        <url-pattern>*.gif</url-pattern>
        <url-pattern>*.jpg</url-pattern>
        <url-pattern>*.js</url-pattern>
        <url-pattern>*.html</url-pattern>
        <url-pattern>*.ico</url-pattern>
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
  
 	 /**
     * restful风格
     * :id匹配url的参数
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
```

`@Controller`标注为一个Controller

`@Get`GET请求

`@Ajax`返回json

`@Param`绑定参数

`@Path`绑定url的参数，用于实现restful

其中: `@Post`、`@Put`、`@Delete`我还没有测试



###	注意

- 尝试写的这个mvc，不打算加入拦截器等，只处理请求，调用Controller中的方法，返回响应。
- 文件上传不支持
- 不支持请求参数绑定到java对象
- 不支持返回视图定义前缀后缀
- 不支持参数校验
- 很多都不支持，单纯做了请求分发，方法响应



###	总结

我站在巨人的肩上，而我不是巨人。只有知己尝试去写点东西，才知道自己的渺小，前辈的伟大。