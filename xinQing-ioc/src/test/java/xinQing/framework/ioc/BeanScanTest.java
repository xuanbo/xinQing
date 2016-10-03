package xinQing.framework.ioc;

import org.junit.Test;
import xinQing.framework.ioc.context.BeanScan;

import java.util.Set;

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
}
