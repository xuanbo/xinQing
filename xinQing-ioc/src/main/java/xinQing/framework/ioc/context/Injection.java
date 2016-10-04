package xinQing.framework.ioc.context;

import xinQing.framework.ioc.annotation.Inject;
import xinQing.framework.ioc.bean.BeanFactory;
import xinQing.framework.ioc.exception.BeanFoundException;
import xinQing.framework.ioc.util.Reflects;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 注入，完成Bean的注入
 *
 * Created by xuan on 16-10-4.
 */
public class Injection {

    /**
     * 对扫描到的Bean进行注入
     *
     * @param beanFactory
     */
    public void handlerAutoWired(BeanFactory beanFactory) {
        beanFactory.getBeans().forEach(bean -> inject(bean, beanFactory));
    }

    /**
     * 对某个Bean进行注入
     *
     * @param object
     * @param beanFactory
     */
    private void inject(Object object, BeanFactory beanFactory) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }
            Inject injectAnnotation = field.getAnnotation(Inject.class);
            // 获取@Inject上面的beanName
            String beanName = injectAnnotation.value();
            // 如果beanName不写默认根据类型注入
            if (beanName == null || beanName.isEmpty()) {
                // 根据@Inject标注的属性的Class找到可以注入的Bean的名称
                List<String> beanClassNames = beanFactory.getBeanClassNames(field.getType().getName());
                // 如果找不到可以注入的
                if (beanClassNames != null && beanClassNames.size() == 0) {
                    try {
                        throw new BeanFoundException("根据类型注入，找不到Bean：" + injectAnnotation);
                    } catch (BeanFoundException e) {
                        e.printStackTrace();
                    }
                } else if (beanClassNames.size() == 1){
                    // 根据类型找到一个Bean，则注入
                    beanFactory.getBeanByClassName(beanClassNames.get(0)).ifPresent(o ->
                                Reflects.setValueByField(field, object, o)
                    );
                } else {
                    // 如果找到多个类型，则把属性名当做Bean名称
                    beanName = field.getName();
                    Reflects.setValueByField(field, object, beanFactory.getBean(beanName));
                }
            } else {
                // 如果bean的名称指定了，那么根据名称进行注入
                beanFactory.getBean(beanName).ifPresent(o ->
                        Reflects.setValueByField(field, object, o));
            }

        }
    }
}
