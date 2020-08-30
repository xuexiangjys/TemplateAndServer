# TemplateAndServer

简化版的Android服务端模版，用于接口模拟测试。

## 关于我

[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)   [![简书](https://img.shields.io/badge/简书-xuexiangjys-red.svg)](https://www.jianshu.com/u/6bf605575337)   [![掘金](https://img.shields.io/badge/掘金-xuexiangjys-brightgreen.svg)](https://juejin.im/user/598feef55188257d592e56ed)   [![知乎](https://img.shields.io/badge/知乎-xuexiangjys-violet.svg)](https://www.zhihu.com/people/xuexiangjys) 

## 功能介绍（请star支持）

> 本项目使用[AndServer](https://github.com/yanzhenjie/AndServer)提供的服务搭建。

* 统一的请求日志记录。

* 全局异常捕获处理，返回统一API结果。

* 增加全局权限验证拦截器。

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

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![](https://github.com/xuexiangjys/Resource/blob/master/img/pay/alipay.jpeg) &emsp; ![](https://github.com/xuexiangjys/Resource/blob/master/img/pay/weixinpay.jpeg)

## 联系方式

[![](https://img.shields.io/badge/点击一键加入QQ交流群-602082750-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=9922861ef85c19f1575aecea0e8680f60d9386080a97ed310c971ae074998887)

![](https://github.com/xuexiangjys/Resource/blob/master/img/qq/qq_group.jpg)