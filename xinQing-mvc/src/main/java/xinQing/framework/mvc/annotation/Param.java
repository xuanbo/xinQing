package xinQing.framework.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by xuan on 16-10-9.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {

    String value();

}
