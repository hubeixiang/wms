# wms

标签（空格分隔）： 网站管理系统

---

搭建的网站管理系统工程分类

## 使用的maven地址
请参看工程wms.parent目录下的settings.xxml
maven的host地址配置为wmsmvnhost

## 部署后测试地址
* http://发布的host:8808/wms/nav/init
* http://127.0.0.1:8808/wms/swagger-ui.html  使用swagger管理的rest api访问地址

## 开发分支说明
develop 分支是开发合并分支，所有自有的分支需要先合并到develop中，合并正常后,最后有管理员合并到master主分支中。
禁止直接向master分支中提交代码

## 工程介绍
* wms.parent
  系统所有的module的父module

* wms.utils
  公用的相关工具所在module

* wms.core 
  系统基础使用的相关实体

* wms.configuration 
  系统使用spring.boot框架搭建,其中相关的autoconfiguration注解加载

* wms.db 
  数据库相关的

* wms.service
  系统可以对外独立提供服务的service的父module

* wms.service.core
  系统可以对外独立提供服务的核心module

* wms.service.business
  暂时不对外独立提供服务。业务相关的处理

* wms.service.tasks
  系统独立提供的定时任务类的module,可以单独发布

* wms.service.web
   系统独立对外提供的web服务



