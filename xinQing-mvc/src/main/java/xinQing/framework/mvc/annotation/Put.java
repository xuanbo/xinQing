package xinQing.framework.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by xuan on 16-10-8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Put {

    String value();

}
