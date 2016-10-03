package xinQing.framework.ioc.annotation;

import java.lang.annotation.*;

/**
 * 标注后会被当做一个Bean被IOC容器管理
 *
 * Created by xuan on 16-10-3.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {

    /**
     * 指定Bean的名称，如果不指定，扫描后会以当前类的首字母小写作为Bean的名称
     *
     * @return
     */
    String value() default "";
}
