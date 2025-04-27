## 常用信息

**鸿蒙与华为云结合的智能农业管理系统**

Intelligent agricultural management system based on the combination of Hongmeng and Huawei Cloud

## 开发日志

### 开发环境配置

#### 鸿蒙端

```
IDE：DevEco studio 5.00

HarmonyOS SDK：HarmonyOS 5.0.3（15）

开发主要语言：ArkTS

node版本：16.19.1

ohpm：1.1.2

Gradle：7.3

stage模型
```

#### Web端

```
IDE：IDEA 2021.3.2

Java版本：1.8

maven版本：3.6.1

Mysql版本：8.0

xftp：7

xshell：7

lombok

mybatis

服务器连接池 c3p0   druid 连接池

前端： Vue+js

前后端分离，web的springboot的服务器端口是9090  npm的服务器端口是8080
```

## 数据库

### 🔧 1. 用户表 `users`

```sql
CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255),
  phone VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(255),
  gender VARCHAR(255),
  id_card VARCHAR(255),
  address VARCHAR(255),
  create_date VARCHAR(255),
  image_url VARCHAR(255)
);
```

------

### 🌾 2. 农作物表 `crops`

```sql
CREATE TABLE crops (
  crop_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  greenhouse_name VARCHAR(50),
  crop_name VARCHAR(50),
  planting_time VARCHAR(255),
  growth_cycle VARCHAR(50),
  estimated_harvest VARCHAR(255),
  status VARCHAR(50),
  image_url VARCHAR(255),
  content VARCHAR(255)
);
```

------

### 🏠 3. 大棚表 `greenhouses`

```sql
CREATE TABLE greenhouses (
  greenhouse_id INT PRIMARY KEY AUTO_INCREMENT,
  greenhouse_name VARCHAR(50),
  user_id INT,
  greenhouse_location VARCHAR(255),
  greenhouse_type VARCHAR(50),
  area DOUBLE,
  status VARCHAR(255),
  description VARCHAR(255),
  image_url VARCHAR(255),
  plants VARCHAR(100),
  video_url VARCHAR(255),
  crop_maturity VARCHAR(50),
  expected_harvest VARCHAR(255),
  grow_days INT,
  irrigation_status BOOLEAN,
  ventilation_status BOOLEAN,
  lighting_status BOOLEAN,
  create_time VARCHAR(255),
  supervisor VARCHAR(50)
);
```

------

### 📋 4. 任务表 `tasks`

```sql
CREATE TABLE tasks (
  task_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  content VARCHAR(255),
  completed VARCHAR(10),
  user_id INT,
  complete_time VARCHAR(255),
  greenhouse_name VARCHAR(50),
  publish_time VARCHAR(255),
  deadline VARCHAR(255),
  description VARCHAR(255)
);
```

------

### 🌱 5. 环境数据表 `environment_data`

```sql
CREATE TABLE environment_data (
  envid INT PRIMARY KEY AUTO_INCREMENT,
  greenhouse_id INT,
  record_date VARCHAR(255),
  temperature DOUBLE,
  humidity DOUBLE,
  soil_moisture DOUBLE,
  light_intensity DOUBLE
);
```







## 遇到的bug

### 什么是resourceTable

在 HarmonyOS（以及 DevEco Studio）的开发过程中，ResourceTable 是一个由系统自动生成的类，它起到类似于 Android 中 R 类的作用，用来管理所有资源（布局、图片、字符串等）的 ID 映射。你无需手动创建或编辑它，因为在编译阶段，IDE 会根据你的 resources 文件夹下的资源（如 XML 布局文件、图片资源等）自动生成 ResourceTable.java 文件。

如果你在项目中没有直接看到这个文件，通常可以在生成的代码目录中找到，比如在 build/generated 或者自动生成的源代码包中。你只需要在代码中通过 ResourceTable.Layout_xxx、ResourceTable.Id_xxx 等方式调用对应资源即可，而不用关心具体数字是多少。

因此，ResourceTable 就是 HarmonyOS 项目中资源自动管理的映射入口，不需要你手动管理。
