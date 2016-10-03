package xinQing.framework.ioc.bean;

/**
 * Bean定义信息
 *
 * Created by xuan on 16-10-3.
 */
public class BeanDefinition {

    private String name;

    private String className;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
