package xinQing.framework.ioc.bean;

import xinQing.framework.ioc.context.BeanScan;
import xinQing.framework.ioc.context.Configuration;
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
        initBeanFactory("app.properties");
    }

    /**
     * 加载ioc容器的配置文件
     *
     * @param configPath
     */
    public SimpleBeanFactory(String configPath) {
        this.singletonBeansKeys = new ConcurrentHashMap<>();
        this.singletonBeans = new ConcurrentHashMap<>();
        initBeanFactory(configPath);
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

    /**
     * 初始化BeanFactory
     *
     * @param configPath    配置文件的路径
     */
    public void initBeanFactory(String configPath) {
        // 创建Bena扫描器
        BeanScan beanScan = new BeanScan();
        // 根据配置文件的路径创建Configuration
        Configuration configuration = new Configuration();
        configuration.parse(configPath);
        // 扫描器进行扫描
        beanScan.beanScan(configuration, this);
    }

}
