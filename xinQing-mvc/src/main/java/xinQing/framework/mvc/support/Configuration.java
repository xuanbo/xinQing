package xinQing.framework.mvc.support;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 处理Properties配置文件
 *
 * Created by xuan on 16-10-3.
 */
public class Configuration {

    private static final Logger log = Logger.getLogger(Configuration.class);

    // 扫描包key
    public static final String BASE_SCAN_PACKAGE = "beanScan.packageName";
    // 是否扫描子包key
    public static final String BASE_SCAN_RECURSIVE = "beanScan.recursive";


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
            log.error("找不到配置文件");
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
