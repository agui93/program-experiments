https://agui93.github.io/2021/10/22/io-model/
https://agui93.github.io/2021/10/23/java/java-nio-socket/




NIO  Reactor
Poll-based vs  epoll-based
Zero Copy



Epoll:	
    https://docs.oracle.com/javase/8/docs/technotes/guides/io/enhancements.html
    https://zhuanlan.zhihu.com/p/27419141
    https://zhuanlan.zhihu.com/p/27434028
    https://zhuanlan.zhihu.com/p/27441342?group_id=859562548406677504


Socket篇纲要


各类IO模型;   by unix环境编程  linux网络编程





Reactor reactor论文
---> reactor模式解释对比  https://www.cnblogs.com/doit8791/p/7461479.html ;;



Scalable IO in Java by Doug Lea


TCP/IP机制: 结合WireShark拦截分析工具/接口的作用等,统一绘制



网络框架的开发机制netty：编解码  业务解耦



源码中的样例(nio  selector reactor等); kafka(producer consumer broker对比)   netty  redis中的机制
---》netty的reactor对应 https://blog.csdn.net/u011857851/article/details/103962025；


配套机制: 序列化方案  压缩方案(另外压缩-解压的机制-例如:端到端压缩)

场景: 框架中间件通信协调   IM聊天   即时通讯等
