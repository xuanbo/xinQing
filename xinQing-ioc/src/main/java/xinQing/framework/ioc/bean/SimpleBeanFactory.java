package xinQing.framework.ioc.bean;

import xinQing.framework.ioc.util.Reflects;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory的一个简单实现
 * 说明：容器中的Bean为单例
 *
 * Created by xuan on 16-10-3.
 */
public class SimpleBeanFactory implements BeanFactory {

    /**
     * 保存Bean的key信息
     * key：Bean的名称
     * value：Bean的ClassName
     */
    private Map<String, String> singletonBeansKeys;

    /**
     * 保存Bean的信息
     * key：Bean的ClassName
     * value：Bean的实例
     */
    private Map<String, Object> singletonBeans;

    public SimpleBeanFactory() {
        this.singletonBeansKeys = new ConcurrentHashMap<>();
        this.singletonBeans = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Optional<T> getBean(Class<T> requireType) {
        String className = requireType.getName();
        T object = (T) singletonBeans.get(className);
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<?> getBean(String name) {
        String className = singletonBeansKeys.get(name);
        Object object = singletonBeans.get(className);
        return Optional.ofNullable(object);
    }

    @Override
    public <T> Optional<T> getBean(String name, Class<T> requireType) {
        String className = singletonBeansKeys.get(name);
        T object = (T) singletonBeans.get(className);
        return Optional.ofNullable(object);
    }

    @Override
    public Object registerBean(BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        singletonBeansKeys.put(beanDefinition.getName(), className);
        Object object = Reflects.newInstance(className);
        singletonBeans.put(className, object);
        return object;
    }

}
