package xinQing.framework.ioc;

import org.junit.Test;
import xinQing.framework.ioc.context.BeanScan;
import xinQing.framework.ioc.dao.impl.UserDaoImpl;
import xinQing.framework.ioc.service.UserService;
import xinQing.framework.ioc.service.impl.UserServiceImpl;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 测试扫描Bean
 *
 * Created by xuan on 16-10-3.
 */
public class BeanScanTest {

    /**
     * 对包进行扫描
     */
    @Test
    public void scan() {
        BeanScan beanScan = new BeanScan();
        Set<Class<?>> scan = beanScan.classScan("xinQing.framework.ioc", true);
        scan.forEach(clazz -> System.out.println(clazz));
    }

    @Test
    public void map() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("a", Arrays.asList("a"));
        List<String> b = map.getOrDefault("b", new ArrayList<>());
        b.forEach(s -> System.out.println(s));
        System.out.println(b);
    }

    @Test
    public void reflect() throws NoSuchFieldException, IllegalAccessException {
        UserService userService = new UserServiceImpl();
        Field userDao = UserServiceImpl.class.getDeclaredField("userDao");
        userDao.setAccessible(true);
        userDao.set(userService, new UserDaoImpl());
        userService.deleteById(1L);
        System.out.println(userDao.getName());
    }
}
