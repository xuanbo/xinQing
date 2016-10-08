package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;
import xinQing.framework.mvc.annotation.Controller;
import xinQing.framework.mvc.util.CastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Controller Annotation扫描
 *
 * Created by xuan on 16-10-8.
 */
public class ControllerScan {

    private static final Logger log = Logger.getLogger(ControllerScan.class);

    private Configuration configuration;

    public ControllerScan(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 获取@Controller标识的Class
     *
     * @return
     */
    public Set<Class<?>> loadControllerClasses() {
        // 获取扫描包的配置信息
        String packageName = configuration.getProperty(Configuration.BASE_SCAN_PACKAGE);
        boolean recursive = CastUtils.cast(configuration.getProperty(Configuration.BASE_SCAN_RECURSIVE));
        Set<Class<?>> classes = classScan(packageName, recursive);
        classes.removeIf(clazz -> {
            log.debug(clazz);
            return !clazz.isAnnotationPresent(Controller.class);
        });
        return classes;
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
