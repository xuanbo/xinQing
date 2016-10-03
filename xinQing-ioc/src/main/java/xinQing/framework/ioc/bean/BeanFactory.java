package xinQing.framework.ioc.bean;

import java.util.Optional;

/**
 * 定义IOC容器接口
 *
 * Created by xuan on 16-10-3.
 */
public interface BeanFactory {

    /**
     * 根据Class获取Bean
     *
     * @param requireType
     * @param <T>
     * @return
     */
    <T> Optional<T> getBean(Class<T> requireType);

    /**
     * 根据名称获取Bean
     *
     * @param name
     * @return
     */
    Optional<?> getBean(String name);

    /**
     * 根据名称和所需的Class类型获取Bean
     *
     * @param name
     * @param requireType
     * @param <T>
     * @return
     */
    <T> Optional<T> getBean(String name, Class<T> requireType);

    /**
     * 注册一个Bean到容器
     *
     * @param beanDefinition
     * @return
     */
    Object registerBean(BeanDefinition beanDefinition);

}
