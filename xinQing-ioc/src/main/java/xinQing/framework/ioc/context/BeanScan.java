package xinQing.framework.ioc.context;

import xinQing.framework.ioc.annotation.Bean;
import xinQing.framework.ioc.bean.BeanDefinition;
import xinQing.framework.ioc.bean.BeanFactory;
import xinQing.framework.ioc.util.CastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 扫描@Bean标注的Bean
 *
 * Created by xuan on 16-10-3.
 */
public class BeanScan {

    // 注入器
    private Injection injection;

    public BeanScan() {
        this.injection = new Injection();
    }

    /**
     * 扫描包下的Bean
     *
     * @param configuration 配置信息
     * @param beanFactory
     */
    public void beanScan(Configuration configuration, BeanFactory beanFactory) {
        // 获取扫描包的配置信息
        String packageName = configuration.getProperty(Configuration.BASE_SCAN_PACKAGE);
        boolean recursive = CastUtils.cast(configuration.getProperty(Configuration.BASE_SCAN_RECURSIVE));
        // 扫描包下的Class
        Set<Class<?>> classes = classScan(packageName, recursive);
        // 过滤掉没有被@Bean标注的Class
        classes.removeIf(clazz -> !clazz.isAnnotationPresent(Bean.class));
        // 将Bean纳入到容器管理
        classes.forEach(clazz -> handlerBean(clazz, beanFactory));
        // 处理Bean的注入
        injection.handlerAutoWired(beanFactory);
    }

    /**
     * 将Bean注册到容器
     *
     * @param clazz
     * @param beanFactory
     */
    public void handlerBean(Class<?> clazz, BeanFactory beanFactory) {
        Bean annotation = clazz.getAnnotation(Bean.class);
        BeanDefinition beanDefinition = new BeanDefinition();
        // 获取bean的名称，如果没有指定value属性，则默认为类名的首字母小写
        String beanName = annotation.value();
        if (beanName == null || beanName.isEmpty()) {
            String className = clazz.getSimpleName();
            // 首字母小写
            if (className.length() > 1) {
                beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
            } else {
                beanName = className.substring(0, 1).toLowerCase();
            }
        }
        beanDefinition.setName(beanName);
        beanDefinition.setClassName(clazz.getName());
        // 注册到容器
        beanFactory.registerBean(beanDefinition);
    }

    /**
     * 扫描包下面的Class
     *
     * @param packageName
     * @param recursive 是否扫描包下面的子包
     * @return
     */
    public Set<Class<?>> classScan(String packageName, boolean recursive) {
        // 存放扫描到的Bean的Class
        Set<Class<?>> classes = new LinkedHashSet<>();
        Enumeration<URL> resources;
        String packageDirs = packageName.replace(".", "/");
        try {
            resources = Thread.currentThread().getContextClassLoader().getResources(packageDirs);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String fileAbsolutePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    classScanByFile(packageName, fileAbsolutePath, recursive, classes);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 扫描文件类型
     *
     * @param packageName   包名
     * @param fileAbsolutePath  文件的绝对路径
     * @param recursive
     * @param classes
     */
    public void classScanByFile(String packageName, String fileAbsolutePath, boolean recursive, Set<Class<?>> classes) throws ClassNotFoundException {
        File file = new File(fileAbsolutePath);
        // 如果不是一个目录，则跳出
        if (!file.isDirectory())
            return;
        // 获取路径下的目录，或者.class文件
        File[] files = file.listFiles(f -> recursive || f.isDirectory() || f.getName().endsWith(".class"));
        // 遍历
        for (File f : files) {
            // 如果是目录则递归处理，否则将.class文件的Class信息加入Set
            if (f.isDirectory()) {
                classScanByFile(packageName + "." + f.getName(), f.getAbsolutePath(), recursive, classes);
            } else {
                // 截取掉.class后缀
                String fileName = f.getName();
                String className = fileName.substring(0, fileName.length() - 6);
                // 将Class放入Set
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
            }
        }
    }

}
