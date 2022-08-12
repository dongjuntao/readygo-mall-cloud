# readygo-mall-cloud 莱蒂购商城

#### 介绍
一个基于SpringCloud, SpringCloud alibaba的微服务商城系统，支持单商户，多商户。目前包含的功能有“系统管理”，“会员管理”，“商品管理”，“商户管理”，“物流管理”，“营销管理”，“商城管理”等，使用的技术有Java,SpringBoot,SpringCloud,SpringCloudAlibaba, mysql, redis, rabbitmq, seata, nacos, SpringCloud gateway, SpringCloud OAuth等

#### 软件架构


#### 使用说明
本地可以直接启动所有服务(可以使用idea services),所需的中间件已经安装在云服务，若需要使用自己的中间件，请在启动前确保其已安装并在项目中修改配置信息，所需的中间件有：
jdk8，
mysql5.7(或以上版本)，
redis5.0.3(或其他版本)，
rabbitmq3.9.15(或其他版本)，
seata1.4.0，
nacos2.0.3

#### 前端项目
后台管理端（运营管理端和商户端）：git@github.com:dongjuntao/readygo-mall-admin-vue.git，
门户端（PC端网站）：git@github.com:dongjuntao/readygo-mall-portal-vue.git
