package xinQing.framework.ioc.bean;

import java.util.Collection;
import java.util.List;
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
     * 根据className获取Bean
     *
     * @param className
     * @param <T>
     * @return
     */
    <T> Optional<T> getBeanByClassName(String className);

    /**
     * 注册一个Bean到容器
     *
     * @param beanDefinition
     * @return
     */
    Object registerBean(BeanDefinition beanDefinition);

    /**
     * 根据父类型找到Bean的ClassName
     *
     * @param superClassNameOrInterfaceName
     * @return
     */
    List<String> getBeanClassNames(String superClassNameOrInterfaceName);

    /**
     * 获取所有的Bean
     *
     * @return
     */
    Collection<Object> getBeans();

}
