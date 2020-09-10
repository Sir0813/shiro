# wws-second-springboot-shiro-jwt

#### 介绍
springboot整合shiro和jwt

#### 软件架构
springboot整合shiro和jwt,实现前后端分离


#### 安装教程

1.  导入sql文件夹下的shiro.sql
2.  更改application.yml中的数据库配置，换为自己的
3.  启动项目不报错即可

#### 使用说明

1.  先执行密码登录或者验证码登录拿到token
2.  将token放在请求头中，测试权限即可


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 目录结构

interfaces：用户接口层，这里面的facade里面放着大家熟悉的controller，其余都是用来转换
前后端的对象的，例如DTO。

infrastructure：基础设施层，这里面存放的是一些配置信息、工具类以及shiro相关的一些类，
你可以理解为此层是为了给整个项目提供通用支持。

appication：应用服务层，进入微服务的入口。此层组织多个业务领域完成来自facade的业务
需求，除此以外，一些spring的事件、定时任务、消息中间件的东西都会放在这里。

domain层：领域模型层，ddd领域驱动模型最重要但是最难理解的一层。这里面会存在多个聚合，
每一个聚合都是独立的，这种独立体现在业务上。例如用户周围的一些概念，用户+角色+权限可
以形成一个聚合，系统日志和用户操作日志可以形成一个聚合。这一层为了代码的简易没有按照
ddd严格划分领域，感兴趣的伙伴可以去看一些书籍了解。

#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
