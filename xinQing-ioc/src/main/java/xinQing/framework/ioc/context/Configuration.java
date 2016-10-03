package xinQing.framework.ioc.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 处理Properties配置文件
 *
 * Created by xuan on 16-10-3.
 */
public class Configuration {

    // 扫描包key
    public static final String BASE_SCAN_PACKAGE = "ioc.beanScan.packageName";
    // 是否扫描子包key
    public static final String BASE_SCAN_RECURSIVE = "ioc.beanScan.recursive";


    private Properties properties;


    public Configuration() {
        this.properties = new Properties();
    }

    /**
     * 解析Properties配置文件
     *
     * @param configPath
     */
    public void parse(String configPath) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key获取Properties配置文件中的值
     *
     * @param key   Properties配置文件中的key
     * @return
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
