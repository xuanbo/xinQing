package xinQing.framework.ioc;

import org.junit.Test;
import xinQing.framework.ioc.bean.BeanDefinition;
import xinQing.framework.ioc.bean.BeanFactory;
import xinQing.framework.ioc.bean.SimpleBeanFactory;
import xinQing.framework.ioc.service.Message;

import java.util.Optional;

/**
 * 对IOC容器测试
 *
 * Created by xuan on 16-10-3.
 */
public class IOCTest {

    /**
     * 版本1
     *
     * 不足之处：
     * 1.需要手动向容器中注册Bean
     * 2.没有实现注入
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

    /**
     * 版本2
     *
     * 自动扫描包，并注册到容器
     *
     * 优化之后，跟Spring创建ApplicationContext操作方式一样
     *
     * 不足之处：
     * 1.没有定义一个扫描包的入口（目前写死了）
     * 2.没有实现注入
     *
     * 如何解决扫描包的入口：
     * 1.使用配置文件，配置扫描的包，以及是否扫描子包（我采用这种方式）
     * 2.创建BeanFactory的时候指定扫描的信息
     *
     */
    @Test
    public void autoInit() {
        // 1.创建BeanFactory的一个简单实现：SimpleBeanFactory
        BeanFactory beanFactory = new SimpleBeanFactory();

        // 2.从容器中获取Bean
        Optional<?> hero1 = beanFactory.getBean("hero");
        Optional<Hero> hero2 = beanFactory.getBean(Hero.class);
        Optional<Hero> hero3 = beanFactory.getBean("hero", Hero.class);

        // 3.对Bean做一些操作
        hero1.ifPresent(o -> System.out.println(o));
        hero2.ifPresent(o -> System.out.println(o));
        hero3.ifPresent(o -> System.out.println(o));
    }

    /**
     * 版本3
     *
     * 使用Properties属性文件进行配置信息
     *
     * 不足之处：
     * 1.没有实现注入
     */
    @Test
    public void configurationInit() {
        // 1.创建BeanFactory的一个简单实现：SimpleBeanFactory，如果不指定，默认为classpath下的app.properties
        BeanFactory beanFactory = new SimpleBeanFactory("app.properties");

        // 2.从容器中获取Bean
        Optional<?> hero1 = beanFactory.getBean("hero");
        Optional<Hero> hero2 = beanFactory.getBean(Hero.class);
        Optional<Hero> hero3 = beanFactory.getBean("hero", Hero.class);

        Optional<?> message1 = beanFactory.getBean("message");
        Optional<Message> message2 = beanFactory.getBean(Message.class);
        Optional<Message> message3 = beanFactory.getBean("message", Message.class);

        // 3.对Bean做一些操作
        hero1.ifPresent(o -> System.out.println(o));
        hero2.ifPresent(o -> System.out.println(o));
        hero3.ifPresent(o -> System.out.println(o));

        message1.ifPresent(o -> {
            System.out.println(o);
            Message message = (Message) o;
            message.say();
        });
        message2.ifPresent(o -> {
            System.out.println(o);
            o.say();
        });
        message3.ifPresent(o -> {
            System.out.println(o);
            o.say();
        });
    }

}
