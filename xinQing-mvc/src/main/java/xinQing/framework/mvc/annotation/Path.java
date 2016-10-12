package xinQing.framework.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by xuan on 16-10-12.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {

    String value();

}
