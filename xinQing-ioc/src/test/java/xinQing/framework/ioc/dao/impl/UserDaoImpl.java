package xinQing.framework.ioc.dao.impl;

import xinQing.framework.ioc.annotation.Bean;
import xinQing.framework.ioc.dao.UserDao;

/**
 * Created by xuan on 16-10-4.
 */
@Bean
public class UserDaoImpl implements UserDao {

    @Override
    public int deleteById(Long id) {
        System.out.println("UserDaoImpl -> deleteById:" + id);
        return 1;
    }

}
