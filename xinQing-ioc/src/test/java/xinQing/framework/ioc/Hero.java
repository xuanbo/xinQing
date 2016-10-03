package xinQing.framework.ioc;

import xinQing.framework.ioc.annotation.Bean;

/**
 * 英雄
 *
 * Created by xuan on 16-10-3.
 */
@Bean
public class Hero {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
