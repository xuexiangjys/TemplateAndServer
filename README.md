# TemplateAndServer

简化版的Android服务端模版，用于接口模拟测试。

## 关于我

[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)   [![简书](https://img.shields.io/badge/简书-xuexiangjys-red.svg)](https://www.jianshu.com/u/6bf605575337)   [![掘金](https://img.shields.io/badge/掘金-xuexiangjys-brightgreen.svg)](https://juejin.im/user/598feef55188257d592e56ed)   [![知乎](https://img.shields.io/badge/知乎-xuexiangjys-violet.svg)](https://www.zhihu.com/people/xuexiangjys) 

## 演示（请star支持）

### 服务端演示

![](https://cdn.jsdelivr.net/gh/BugF/IMG/2020/10/05/5f7b19e093fd6.gif)

### 浏览器演示

![](https://cdn.jsdelivr.net/gh/BugF/IMG/2020/10/05/5f7b19de9cb51.gif)

### 写法对比

* AndServer

![](https://cdn.jsdelivr.net/gh/BugF/IMG/2020/10/05/5f7b19db8508d.png)

![](https://cdn.jsdelivr.net/gh/BugF/IMG/2020/10/05/5f7b19de4f5f6.png)

* SpringBoot

![](https://cdn.jsdelivr.net/gh/BugF/IMG/2020/10/05/5f7b19de9166b.png)

从上面的图片我们很容易看出，AndServer的写法和SpringBoot是非常相似的，就连项目工程的结构也是相似的。

---

## 功能介绍

> 本项目使用[AndServer](https://github.com/yanzhenjie/AndServer)提供的服务搭建。

* 统一的请求日志记录。

* 全局异常捕获处理，返回统一API结果。

* 增加全局权限验证拦截器。

* 文件上传。

* 后台管理界面。

* 接口测试界面。

## 返回Json格式

```
{
    "code":0, //响应码，0为成功，否则失败
    "msg":"", //请求失败的原因说明
    "data":{} //返回的数据对象
}
```

## 使用方式

### 服务器配置

在`com.xuexiang.server.ServerConfig`进行配置的修改。

### 服务接口编写

1.在`com.xuexiang.server.controller`包下创建Controller类。

2.在Controller类上加上`@RestController`和`@RequestMapping`注解。

### 日志查看

在logcat上搜索关键词"AndServer"即可查看请求日志。
