# cc

基于Netty实现的推送中心，在服务端使用长链接向客户端推送配置信息。

2017-05-29更新：
因为工作一个配置中心，同时想基于这个配置中心开发一个自动降级的功能。由于正好对这个功能比较感兴趣，所以打算用业余时间完成这个事情，并将代码维护到了此处。
代码中借鉴了不少阿里开源项目diamond的代码，如果发现熟悉的套路莫要惊讶，如果侵权请联系。

### 背景

读了Netty源码，为了加深理解做的练手项目。本项目实现的功能用Zookeeper同样可以实现，而且Zookeeper实现的可能更好。

因此不具备线上运行条件。



### 当前状态（开发中）

1. 心跳检测（已实现）；
2. 断线重连（已实现）；
3. 长连接数据推送（已实现）；
4. 客户端接口提供（未实现）；
5. ...？

### 运行环境

暂时只支持Java 8以及更高版本（截止2016-09-22，还没有更新的～）

