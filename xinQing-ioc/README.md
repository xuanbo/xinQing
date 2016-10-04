#	IOC

###	1、介绍

这个简单的IOC容器，基于java annotation进行配置。实现了单例容器，对Bean扫描和注入功能。下面是两个注解：

* 使用@Bean将一个类标识为Bean
* 使用@Inject进行注入

**说明**

* @Bean中如何不指定value，扫描后会将类名的首字母小写作为Bean的名称。
* @Inject不指定value的情况下，首先根据类型注入，如果找到多个，那么会将标注的属性名作为Bean的名称进行注入。
* @Inject的实现是根据反射Filed设置值，因此该Annotation只能使用在属性上。
* @Inject如果指定value，那么只会根据名称注入。



###	2、使用

在使用mavan构建项目的前提下。

* 将这个项目git clone下来，安装到本地`mvn install`，然后依赖。


* resources下面指定扫描包的配置信息,默认为`app.properties`

  ```
  #   ioc的配置信息
  ioc.beanScan.packageName=com.web // 扫描的包
  ioc.beanScan.recursive=true	// 是否扫描子包
  ```

  ​

* 使用@Bean和@Inject。这里我给一个例子。

  com.web.dao包下写一个接口

  ```java
  public interface UserDao {

      int deleteById(Long id);
  }
  ```

  com.web.dao.impl提供两个实现类

  ```java
  @Bean	// 默认的名称为userDaoImpl
  public class UserDaoImpl implements UserDao {

      @Override
      public int deleteById(Long id) {
          System.out.println("UserDaoImpl -> deleteById:" + id);
          return 1;
      }

  }

  @Bean	// 默认的名称为userDaoImplOld
  public class UserDaoImplOld implements UserDao {

      @Override
      public int deleteById(Long id) {
          System.out.println("UserDaoImplOld -> deleteById:" + id);
          return 1;
      }
  }
  ```

  com.web.service包下写一个接口

  ```java
  public interface UserService {

      int deleteById(Long id);
  }
  ```

  com.web.service.impl提供一个实现

  ```java
  @Bean	// 默认的名称为userServiceImpl
  public class UserServiceImpl implements UserService {

      @Inject("userDaoImpl")		// 会注入UserDaoImpl
      private UserDao userDao；

      @Override
      public int deleteById(Long id) {
          System.out.println("UserServiceImpl -> deleteById:" + id);
          return userDao.deleteById(id);
      }
  }
  ```



* 创建一个BeanFactory的实例，并使用它取Bean。可以通过构造函数修改默认的配置文件。

  ```java
      @Test
      public void inject() {
          BeanFactory beanFactory = new SimpleBeanFactory("app.properties");
          Optional<UserService> bean = beanFactory.getBean(UserService.class);
          bean.ifPresent(o -> o.deleteById(1L));
      }
  ```

  ​



###	3.不足之处

正如前面所介绍的，我自己实现的IOC容器功能很简单，使用也有限制。比如只实现了单例、不能泛型注入等。



###	4.总结

写起来还是挺好玩的，虽然很简单的功能，写起来还真是另一回事。加油！



