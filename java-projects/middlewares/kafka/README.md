# kafka

https://kafka.apache.org/0102/javadoc/index.html

raw编程 <br> 
语言开发框架(不同的语言版本以及不同的方案对比) <br>
源码搭建 <br>  
原理设计和实现 <br>  
性能测试(指标 测试方案 测验报告) <br>
监控指标和监控工具 <br>
集群的部署和日常维护(另外:容器编排的方式维护) <br> 
经典的业务场景和问题 <br>
主要的配置属性 <br>
不同版本的kafka特性 <br>
其他类型的消息中间件的对比 <br>
参考 <br>


## 基本特点

异步 解耦 数据持久化 局部顺序保证 扩展和容灾 缓冲和消峰


## Core Concept
Topic Partition Log
Broker
Leader Follower Replica  In-Sync-Replica 
HW(HighWatermark) LEO(Log End Offset)
Cluster＆Controller
Producer Consumer Consumer-Group


## 源码

KafkaProducer的使用和相关配置，剖析核心代码及原理<br>
API样例;<br>
分析(宏观流程 部分关键的调用流程);<br>
关键功能:拦截消息、序列化消息、路由消息功能;RecordAccumulator的结构和实现;NetworkClient;新Kafka集群元数据;Sender线程 <br>

KafkaConsumer
API样例;<br>
消息的传递保证;  Consumer Group ReBalance;



## 主要的配置属性



## raw code

## 源码分析思路
梳理:基于主干实现的基本样例,源码分析环境构建

梳理:时序图 类图 

梳理:实例和类直接引用关系 

梳理:核心类、类的核心字段和方法

梳理:使用的模式

梳理:对象间协作 线程间的协作  -> 引用关系是分析的重要线索之一

尝试:根据分析结果的反向验证

对比提高:针对实现场景的抽象、针对实现思路的抽象 、对比类似的场景的其他实现(例如线程工具和效率保障、数据结构的设计、算法的抉择、网络交互、内存重用、回调设计、整体结构的设计等等)


## 源码分析-生产者

网络交互(网络负载的优化技巧、网络交互的统一抽象)

内存回收

线程协作

回调

开发人员使用的api、控制生产者的配置、生产者状况的指标判断/分析/调优思路



## 传递性保证

### 语义
At most once <br>
At least once <br>
Exactly once <br>


### 支持的提交策略
1.自动提交offset: enable.auto.commit auto.commit.interval.ms poll调用 <br>
2.手工同步提交: commitSync()方法 <br>
3.手工异步提交: commitAsync()方法 <br>

分析不同的提交策略提交时机、消费消息处理时机、消费者重启时机、ReBalance时机这些因素和语义的关系


### Exactly-Once方案 

Exactly-Once分析和实现思路: <br>
1.生产者要保证不会产生重复的消息并且成功投递 <br>
2.消费者不能重复拉取相同的消息 <br>

Exactly-Once可行方案(1.offset提交和消息处理合成一个事务处理  2.重启或者ReBalance时进行seekOffset) <br>
将offset和消息处理放在一个事务中，事务执行成功则认为此消息被消费，否则事务回滚需要重新消费。 <br>
当出现消费者宕机重启或ReBalance操作(ConsumerRebalanceListener回调)时，消费者可以从关系型数据库中找到对应的offset，然后调用KafkaConsumer.seek()方法手动设置消费位置，从此offset处开始继续消费。<br>


Exactly-Once可选方案(At least once的语义下,消费者通过幂等保证语义收敛)
生成者保证消息一定被集群分区保存
消费者保证消费方法幂等


Exactly-Once可选方案(At least once的语义下,生成者和消费者通过全局唯一ID保证语义收敛)
生产者保证消息一定被集群分区保存,消息附带全局唯一ID
消费者可以根据全局唯一ID进行去重处理

其他参考 <br>
https://cwiki.apache.org/confluence/display/KAFKA/KIP-98+-+Exactly+Once+Delivery+and+Transactional+Messaging <br>




## 源码分析-消费者


心跳检查 故障转移 Group-ReBalance


消费者的状态转移与各请求之间的关系 <br>
状态:Initialize、Joining、AwaitingSync、Stable、StopConsumption <br>
请求:JoinGroupRequest/JoinGroupResponse、SyncGroupRequest/SyncGroupResponse、
HeartbeatRequest/HeartbeatResponse、GroupCoordinationRequest/GroupCoordinationResponse




