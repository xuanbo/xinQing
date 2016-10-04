package xinQing.framework.ioc.annotation;

import java.lang.annotation.*;

/**
 * 使用限制：注入，只能通过属性进行注入
 * 默认根据类型注入，如果有多个Bean，则根据名称注入
 * value属性不写，则默认属性名称为Bean的名称。例如：private Hero xHero，则默认为'xHero'
 *
 * Created by xuan on 16-10-3.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {

    String value() default "";

}
