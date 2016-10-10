package xinQing.framework.mvc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图
 *
 * Created by xuan on 16-10-10.
 */
public class ModelAndView {

    private String name;

    private Map<String, Object> attributes;

    public ModelAndView() {
        this.attributes = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public void setAttributes(String name, Object attribute) {
        this.attributes.put(name, attribute);
    }
}
