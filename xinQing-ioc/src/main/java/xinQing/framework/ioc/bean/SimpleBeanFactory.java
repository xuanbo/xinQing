package xinQing.framework.ioc.bean;

import xinQing.framework.ioc.context.BeanScan;
import xinQing.framework.ioc.context.Configuration;
import xinQing.framework.ioc.exception.BeanFoundException;
import xinQing.framework.ioc.util.Reflects;

import java.util.*;
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

    /**
     * 某个类型对应它的子类型
     * key:Bean的ClassName
     * value:Bean的父类型(父类、实现的接口、一直向上引用)
     */
    private Map<String, List<String>> typeBeans;

    public SimpleBeanFactory() {
        this("app.properties");
    }

    /**
     * 加载ioc容器的配置文件
     *
     * @param configPath
     */
    public SimpleBeanFactory(String configPath) {
        this.singletonBeansKeys = new ConcurrentHashMap<>();
        this.singletonBeans = new ConcurrentHashMap<>();
        this.typeBeans = new ConcurrentHashMap<>();
        initBeanFactory(configPath);
    }

    @Override
    public <T> Optional<T> getBean(Class<T> requireType) {
        String className = requireType.getName();
        // 找到向下引用的Bean的ClassName
        List<String> beanClassNames = getBeanClassNames(className);
        // 根据类型找所匹配的Bean
        if (beanClassNames == null || beanClassNames.size() == 0) {
            try {
                throw new BeanFoundException("根据所给的Class找不到Bean：" + className);
            } catch (BeanFoundException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        } else if (beanClassNames.size() > 1) {
            try {
                throw new BeanFoundException("根据所给的Class找到多个Bean：" + className + "。可以匹配的Bean => " +
                        beanClassNames + "]");
            } catch (BeanFoundException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
        T object = (T) singletonBeans.get(beanClassNames.get(0));
        return Optional.of(object);
    }

    @Override
    public Optional<?> getBean(String name) {
        String className = singletonBeansKeys.get(name);
        Object object = singletonBeans.get(className);
        if (className == null || className.isEmpty() || object == null) {
            try {
                throw new BeanFoundException("根据所给的名称找不到Bean：" + name);
            } catch (BeanFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(object);
    }

    @Override
    public <T> Optional<T> getBean(String name, Class<T> requireType) {
        String className = singletonBeansKeys.get(name);
        T object = (T) singletonBeans.get(className);
        if (className == null || className.isEmpty() || object == null) {
            try {
                throw new BeanFoundException("根据所给的名称找不到Bean：" + name);
            } catch (BeanFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(object);
    }

    @Override
    public <T> Optional<T> getBeanByClassName(String className) {
        T object = (T) singletonBeans.get(className);
        return Optional.ofNullable(object);
    }

    @Override
    public Object registerBean(BeanDefinition beanDefinition) {
        // 将Bean放入单例Map
        String className = beanDefinition.getClassName();
        singletonBeansKeys.put(beanDefinition.getName(), className);
        Object object = Reflects.newInstance(className);
        singletonBeans.put(className, object);
        // 处理Bean和类型的关系
        afterRegisterBean(object);
        return object;
    }

    /**
     * 初始化BeanFactory
     *
     * @param configPath 配置文件的路径
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

    /**
     * 处理bean的类型关系，因为向上转型是安全的，父类型可以引用子类
     *
     * @param object
     */
    public void afterRegisterBean(Object object) {
        // 获取Bean的接口类型和父类型
        List<String> superClassesAndInterfaces = new LinkedList<>();
        // 自己也兼容自己
        superClassesAndInterfaces.add(object.getClass().getName());
        Reflects.getSuperClassesAndInterfaces(object.getClass(), superClassesAndInterfaces);
        superClassesAndInterfaces.forEach(className -> {
            // 如果不存在父类型的key，需要new一个
            List<String> beans = typeBeans.getOrDefault(object.getClass().getName(), new LinkedList<>());
            beans.add(className);
            typeBeans.put(object.getClass().getName(), beans);
        });
    }

    /**
     * 根据父类型找到Bean的ClassName
     *
     * @param superClassNameOrInterfaceName
     * @return
     */
    public List<String> getBeanClassNames(String superClassNameOrInterfaceName) {
        List<String> beanClassNames = new LinkedList<>();
        typeBeans.forEach((beanClassName, superClassNameOrInterfaceNames) ->
                superClassNameOrInterfaceNames.forEach(s -> {
                    if (s.equals(superClassNameOrInterfaceName)) {
                        beanClassNames.add(beanClassName);
                    }
                })
        );
        return beanClassNames;
    }

    @Override
    public Collection<Object> getBeans() {
        return singletonBeans.values();
    }

}
