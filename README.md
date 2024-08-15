# 项目介绍
这是一个简单的由java编写的servelet起始小项目，该项目未用到IDEA等IDE，而是在最普通的文本编辑器里编写，以了解servlet的运行机制


# 项目结构
```
.
├── README.md
├── WEB-INF
│   ├── classes
│   │   └── com
│   │       └── frey
│   │           └── servlet
│   │               ├── HelloServlet.class
│   │               └── StudentServlet.class
│   ├── lib
│   │   └── mysql-connector-j-8.0.33.jar
│   └── web.xml
├── index.html
└── java
    └── com
        └── frey
            └── servlet
                ├── HelloServlet.java
                └── StudentServlet.java
```
# 项目包含技术项

* Tomcat（java运行服务器）
* Servlet（http请求）
* JDBC（连接docker中的mysql）
* Docker（运行mysql）
* Mysql（在docker中启动）


# 运行
### 前提准备
* 安装JDK
* 安装tomcat
* 安装docker及数据库
* 科隆项目
* tomcat的webapps文件夹下
    * tomcat一般目录为```/usr/local/tomcat/```

### 启动mysql的docker容器
启动一个mysql容器
```
docker run --name servlet-mysql -e MYSQL_ROOT_PASSWORD=mypasswor
d -p 3306:3306 -d mysql:8.0
```
* docker run: 这是启动一个新容器的命令。

* --name servlet-mysql: 使用 --name 选项为这个容器指定一个名字，这里是 servlet-mysql。这样你可以通过这个名字来管理这个容器（如停止、启动、删除等）。

* -p 3306:3306: 使用 -p 选项来映射端口。这里是将主机的 3306 端口映射到容器内部的 3306 端口。3306 是 MySQL 的默认端口，映射后可以通过 localhost:3306 访问容器中的 MySQL 服务。

* -e MYSQL_ROOT_PASSWORD=password: 使用 -e 选项设置环境变量。这里设置了 MySQL 数据库的 root 用户的密码为 password。如果不设置这个环境变量，MySQL 容器启动时会报错。

* -d: 以分离模式（后台运行）启动容器。即命令执行后，容器会在后台运行，终端不会被锁住。

* mysql:8.0: 指定使用的镜像名称和版本，这里是 mysql 镜像的 8.0 版本。

### 进入容器创建数据库及插入数据
1. 进入mysql容器
```
docker exec -it servlet-mysql bash
```
2. 登录mysql
```
 mysql -u root -p
```
 > 输入密码进入mysql控制台

3. 新建数据库及插入数据
```
-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS servletstart;

-- 2. 使用刚创建的数据库
USE servletstart;

-- 3. 创建 user 表
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. 插入 10 个用户数据
INSERT INTO user (username, email, password) VALUES 
('user1', 'user1@example.com', 'password1'),
('user2', 'user2@example.com', 'password2'),
('user3', 'user3@example.com', 'password3'),
('user4', 'user4@example.com', 'password4'),
('user5', 'user5@example.com', 'password5'),
('user6', 'user6@example.com', 'password6'),
('user7', 'user7@example.com', 'password7'),
('user8', 'user8@example.com', 'password8'),
('user9', 'user9@example.com', 'password9'),
('user10', 'user10@example.com', 'password10');

-- 5. 查看插入的数据
SELECT * FROM user;

```

### 编译
进入tomcat下webapps项目根目录执行编译
```
javac -d ./WEB-INF/classes -classpath ./WEB-INF/lib/servlet-api.jar ./java/com/frey/servlet/StudentServlet.java
```

##### 为什么执行 javac -d ./WEB-INF/classes -classpath ./WEB-INF/lib/servlet-api.jar ./java/com/frey/servlet/StudentServlet.java 就就可以 而不用加./WEB-INF/lib/mysql-connector-j-8.0.33.jar?
```
回答：

当你使用 javac 编译 StudentServlet.java 时，是否需要包含 mysql-connector-j-8.0.33.jar 取决于你的代码是否在编译阶段需要访问 MySQL 相关的类。

情况分析：
Servlet API 是必须的：

StudentServlet.java 文件中使用了 Servlet 类和接口（如 Servlet, ServletRequest, ServletResponse），这些类和接口定义在 servlet-api.jar 中。
因此，在编译 StudentServlet.java 时，javac 需要知道 Servlet API 的位置，所以必须指定 servlet-api.jar 的路径。

MySQL Connector 不一定是必须的：

StudentServlet.java 中使用了 DriverManager, Connection, PreparedStatement, 和 ResultSet 等与数据库交互的类，这些类和接口都是 Java 标准库中的类（即属于 java.sql 包）。
mysql-connector-j-8.0.33.jar 提供了与 MySQL 数据库通信的实现类，但这些类在编译期间通常不需要直接引用（除非你在代码中使用了 MySQL Connector 特有的类）。
因此，在编译阶段，javac 只需要 java.sql 包中的接口即可正常编译，而 java.sql 包是 Java 标准库的一部分，因此不需要额外指定 MySQL Connector 的类路径。
总结：
如果代码只使用了标准的 JDBC API（如 java.sql 包），编译时不需要 mysql-connector-j，因为这些类在 Java SE 中已经包含。
但是，运行时仍然需要 mysql-connector-j-8.0.33.jar，因为这是你的代码在运行时与 MySQL 数据库交互所必须的。
```

### 启动tomcat
```
cd /usr/local/tomcat/bin
./startup.sh
```

### 访问项目
```
http://localhost:8080/java-servlet-start/student/list
```
# 什么是servlet
Servlet 是 Java 中用于创建动态 Web 应用程序的服务器端组件。它运行在支持 Java 的 Web 服务器（如 Apache Tomcat）上，处理客户端（通常是浏览器）发送的请求并生成响应，通常是 HTML 页面或 JSON 数据。

# 开发servlet的一些规范
* WEB-INF文件夹是必须的，且在根目录下
* web.xml文件是必须的 在WEB-INF文件夹下
* classes文件夹是必须的，在WEB-INF文件夹下，该文件夹下是编译后的执行文件
* lib文件夹是非必需的，存放的一些需要引入的jar包


