package xinQing.framework.ioc.annotation;

import java.lang.annotation.*;

/**
 * 注入，只能通过属性进行注入
 *
 * Created by xuan on 16-10-3.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {

    String value() default "";

}
