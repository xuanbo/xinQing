package xinQing.framework.ioc.service;

import xinQing.framework.ioc.annotation.Bean;

/**
 * 消息
 *
 * Created by xuan on 16-10-3.
 */
@Bean
public class Message {

    public void say() {
        System.out.println("invoke say...");
    }

}
