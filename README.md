# 简易服务器
---
## 表结构

```sql
CREATE TABLE `upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名字',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'UUID',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件类型',
  `save_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '文件保存地址',
  `add_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_deleted` (`id`,`deleted_id`) USING BTREE,
  UNIQUE KEY `code_deleted` (`code`,`deleted_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4
```

## 服务端部署：

 1. 采用docker容器方式进行部署，挂载主机目录，mysql同样放入容器中
 2. 日志文件采用自定义logback-spring.xml，按照info和error级别进行按天输出
 3. 定时删除日志,每隔30天自动删除

	```powershell
	#!/bin/bash
	find /root/upload/log/ -mtime +30 -name "*.log" -exec rm -rf {} \;
	```

	 每天凌晨2点删除30天内的日志

	```powershell
	0 2 * * * /project/upload/deleted.sh
	```

4. 日志输出，docker挂载主机目录，按天数输出，过滤error
5. 在线地址：[在线地址](http://121.36.77.21:8082/itdfq/)

## 服务端
#### 文件上传：/itdfq/upload  POST
##### 注意
 

 1. 单词文件最大不能超过5MB
 2. 不限制格式上传
 3. 上传后保存于 服务器：/project/upload/data，会在data文件夹下自动创建文件夹进行保存，命名格式为yyyyMMdd,保存地址可以再yml文件中进行配置
 4. 上传文件之后，会返回一个code

##### 测试

 1. 选择一个txt上传，使用postMan进行请求
![上传测试](https://img-blog.csdnimg.cn/50077164004a49dfa1281c46e97b7a74.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASVRkZnE=,size_20,color_FFFFFF,t_70,g_se,x_16)

	数据库信息如下：
![数据库信息](https://img-blog.csdnimg.cn/82db5f581fe0453eb30be171191cc238.png)

	服务器保存地址如下：
![服务器保存信息](https://img-blog.csdnimg.cn/fb190795d4a44618ad6a68334f721615.png)

#### 查看文件信息：/itdfq/getByCode  GET
1. 通过code可以获取到文件的原始名字，类型等等相关信息
举例
![查看](https://img-blog.csdnimg.cn/e0353b0a278c4856b8aa965af20ddc51.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASVRkZnE=,size_20,color_FFFFFF,t_70,g_se,x_16)

#### 文件下载：/itdfq/downByCode    GET
1. 参数为code,String类型，由上传时返回
2. 浏览器打开可以直接进行下载
举例
![地址](https://img-blog.csdnimg.cn/0629c20b41c44326bf5f59a970af46d6.png)
![结果](https://img-blog.csdnimg.cn/91c01f57f4804b7b97c3803d36a214e6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASVRkZnE=,size_20,color_FFFFFF,t_70,g_se,x_16)


### 客户端
1. 客户端主要使用jdk自带工具封装了http请求
2. 在ClientTest进行单元测试