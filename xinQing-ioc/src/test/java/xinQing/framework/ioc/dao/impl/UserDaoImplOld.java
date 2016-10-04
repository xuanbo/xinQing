package xinQing.framework.ioc.dao.impl;

import xinQing.framework.ioc.annotation.Bean;
import xinQing.framework.ioc.dao.UserDao;

/**
 * Created by xuan on 16-10-4.
 */
@Bean
public class UserDaoImplOld implements UserDao {

    @Override
    public int deleteById(Long id) {
        System.out.println("UserDaoImplOld -> deleteById:" + id);
        return 1;
    }
}
