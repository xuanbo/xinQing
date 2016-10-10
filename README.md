#	说明

> ​	大四不读研，天天像过年。那么，在这操蛋的大学生活中，总要做一些操蛋的事。我决定自己去尝试简单的实现Spring的一些组件(IOC，MVC...)，我想，总该做点有意义的事。学习java web的这一年中，总是无聊的填充一些增删改查的无效代码，我想继续这样下去，100年都只是一个菜鸟。虽然重复造轮子没有必要，但是，自己尝试去写总是对自己有帮助的。而且大四秋招的这段时间，自己的心情很浮躁。所以我希望沉下心来，好好的提高下自己的水平。以后在工作中能成为合格的java工程师。

##	一、IOC

> ​	大家都知道Spring的IOC容器的原理是使用反射、解析xml或annotation。那么，自己去写却又是一回事了。自己想简单的实现IOC，因此我只实现单例的Bean，和基于annotation的Bean扫描和注入。

具体实现以及测试看本项目下的[xinQing-ioc模块](https://github.com/xuanbo/xinQing/tree/xuanbo/xinQing-ioc) 。

##	二、MVC

> ​	写MVC纯属是为了好玩，既然为了实现MVC，第一步肯定是需要一个请求分发器的，也就是Spring MVC中的DispatchServlet。Spring MVC有使用注解配置请求映射，可以很方便的实现Restful风格，因此，我打算用@Get、@Post、@Put、@Delele四个Annotation完成Restful风格。

具体实现以及测试请看本项目下的[xinQing-mvc模块](https://github.com/xuanbo/xinQing/tree/xuanbo/xinQing-mvc)。