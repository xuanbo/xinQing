package xinQing.framework.ioc.service.impl;

import xinQing.framework.ioc.annotation.Bean;
import xinQing.framework.ioc.annotation.Inject;
import xinQing.framework.ioc.dao.UserDao;
import xinQing.framework.ioc.service.UserService;

/**
 * Created by xuan on 16-10-4.
 */
@Bean
public class UserServiceImpl implements UserService {

    @Inject("userDaoImpl")
    private UserDao userDao;

    @Override
    public int deleteById(Long id) {
        System.out.println("UserServiceImpl -> deleteById:" + id);
        return userDao.deleteById(id);
    }
}
