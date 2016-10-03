package xinQing.framework.ioc;

import org.junit.Test;
import xinQing.framework.ioc.bean.BeanDefinition;
import xinQing.framework.ioc.bean.BeanFactory;
import xinQing.framework.ioc.bean.SimpleBeanFactory;

import java.util.Optional;

/**
 * 对IOC容器测试
 *
 * Created by xuan on 16-10-3.
 */
public class IOCTest {

    /**
     * 不足之处：需要手动向容器中注册Bean
     */
    @Test
    public void simpleIOC() {
        // 1.创建BeanFactory的一个简单实现：SimpleBeanFactory
        BeanFactory beanFactory = new SimpleBeanFactory();

        // 2.手动定义需要向容器中注册的Bean信息：BeanDefinition
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setName("hero");
        beanDefinition.setClassName("xinQing.framework.ioc.Hero");

        // 3.将BeanDefinition注册到容器中
        beanFactory.registerBean(beanDefinition);

        // 4.从容器中取出Bean
        Optional<?> hero1 = beanFactory.getBean("hero");
        Optional<Hero> hero2 = beanFactory.getBean(Hero.class);
        Optional<Hero> hero3 = beanFactory.getBean("hero", Hero.class);

        // 5.对Bean做一些操作
        hero1.ifPresent(o -> System.out.println(o));
        hero2.ifPresent(o -> System.out.println(o));
        hero3.ifPresent(o -> System.out.println(o));
    }
}
