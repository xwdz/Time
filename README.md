### Time
`Spring boot` + `Mybatis` 实现`File`的`CRUE`

> 接口已部署在正式服务器

### 拿着Api去玩耍(需要Key密匙邮件我~)

#### baseUrl : http://47.106.223.246

**Http Method: `POST`**
> 1.  http://47.106.223.246/file/uploads

|输入参数|说明|类型|必填|
|:---:|:---:|:---:|:---:|
|`key`|api秘钥|string|yes|
|`files`|文件|file|yes|
|`desc`|文件说明|string|no|
|`address`|地址|string|no|


---

**Http Method: `GET`**
> 2.  http://47.106.223.246/file/query
> 
|输入参数|说明|类型|必填|
|---:|---:|---:|---:|
|`key`|api秘钥|string|yes|
|`id`|查询id|string|yes|

---

**Http Method: `GET`**
> 2.  http://47.106.223.246/file/queryAll

|输入参数|说明|类型|必填|
|:---:|:---:|:---:|:---:|
|`key`|api秘钥|string|yes|
|`pageNum`|分页页码（默认1）|int|no|
|`pageSize`|每页条数目（默认10）|int|no|


#### 接口响应结果如下
```
{
    "code": "200",
    "message": "success",
    "data": [
        {
            "id": "45172c4928c746fe95474f148451ad25",
            "path": "/home/spring_server/uploads/JPEG_20190328_193806.jpg",
            "desc": "phone",
            "name": "JPEG_20190328_193806.jpg",
            "uploadTime": "1553773090613",
            "address": "深圳",
            "ukey": "10926a9165054566b6df6a8410e45f08"
        },
        ...
    ]
}
```

#### 文件的静态链接
**上传成功的文件均以`baseUrl`+ `/uploads/`+`文件名称`拿到。**

**simple: http://47.106.223.246/uploadsJPEG_20190328_193806.jpg**


### Web

// TODO

### Android

![simple.png](./image/simple_android.gif)

目前实现查询所有照片列表，上传单个文件，以及上传多个文件。


### Server

服务器采用Spring + Mybatis 框架编写

#### 启动须知

1. 请配置好 `application-dev.yml` 中连接数据库的用户名和密码

2. 启动前，请创建数据库`site_server`，建表`api_channel`、`picture`

3. XwdzApplication.java 中的main方法，访问 http://localhost:8082/ 进行API测试。


```
site
|-- src
	  |-- main
	        |--java/com/xwdz/site
	        		|-- base (基础类)
					|-- configs (静态文件配置)
						   |-- PathConfigs.java 配置静态文件
					|-- controller (各种controller)
							|-- ApiSignature.java 验证客户端传参key是否正常	
							|-- UploadController.java 文件上传，查询接口
					|-- entity (数据库实体类)
						    |-- ApiChannel.java 校验key实体类
						    |-- Picture.java 实体类
						    |-- Response.java 返回外层包装实体类
					|-- mapper (mapper文件)
							 |-- ApiChannelMapper.java 提供查询sql
							 |-- PictureMapper.java 提供各种sql语句
					|-- utils (工具)
					|-- SpringContext.java (用于手动解析mapper接口)
					|-- XwdzApplication.java
			|-- resources	
					|-- mapper 对应的mapper.xml 文件
					|-- static 静态文件
					|-- templates html模板文件
					|-- application.yml 配置文件
					|-- application-dev.yml 测试配置文件
					|-- application-release.yml 生产配置文件
					
```