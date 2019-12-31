#### 引入依赖(我使用后没看到提示...)

为了让 `Spring Boot` 更好的生成配置元数据文件，我们需要添加如下依赖（**该依赖可以不添加，但是在 IDEA 和 STS 中不会有属性提示，没有提示的配置就跟你用记事本写代码一样苦逼，出个问题弄哭你去**），该依赖只会在编译时调用，所以不用担心会对生产造成影响…

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

#### 自定义属性配置

在 `application.properties` 写入如下配置内容

```xml
my1.age=22
my1.name=battcn
```

定义 `MyProperties1.java` 文件，用来映射我们在 `application.properties` 中的内容

```java
package com.example.demo.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my1")
public class MyProperties1 {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyProperties1{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

```

- `@Component`

  将实体类定义为组件，使其可以被扫描进Ioc容器中

- `@ConfigurationProperties(prefix = "my1")`

  扫描定义在application.properties文件中前缀为my1的属性

- `MyProperties1`

  实体类需提供私有属性及set、get方法，顺便重写一下toString方法

定义我们的 `PropertiesController` 用来注入 `MyProperties1` 测试我们编写的代码

```java
package com.example.demo.controller;


import com.example.demo.properties.MyProperties1;
import com.example.demo.properties.MyProperties2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/properties")
@RestController
public class PropertiesController {

    private static final Logger log = LoggerFactory.getLogger(PropertiesController.class);

    private final MyProperties1 myProperties1;
    private final MyProperties2 myProperties2;


    @Autowired
    public PropertiesController(MyProperties1 myProperties1, MyProperties2 myProperties2){
        this.myProperties1 = myProperties1;
        this.myProperties2 = myProperties2;
    }

    @GetMapping("/1")
    public MyProperties1 myProperties1(){
        log.info("==============================================");
        log.info(myProperties1.toString());
        log.info("==============================================");
        return myProperties1;
    }

    @GetMapping("/2")
    public MyProperties2 myProperties2(){
        log.info("==============================================");
        log.info(myProperties2.toString());
        log.info("==============================================");
        return myProperties2;
    }

}

```

- `@RequestMapping("/properties")`

  表示该类接收所有`/properties`下的请求

- `@GetMapping("/1")`

  接收请求类型为get，地址为`/1`的请求

- 访问 http://localhost:8080/dev/properties/1  结果如下（dev是下文设置内容）

  ```xml
  2019-12-28 16:58:37.082  INFO 19260 --- [nio-8080-exec-7] c.e.d.controller.PropertiesController    : ==============================================
  2019-12-28 16:58:37.082  INFO 19260 --- [nio-8080-exec-7] c.e.d.controller.PropertiesController    : MyProperties1{age=22, name='battcn'}
  2019-12-28 16:58:37.083  INFO 19260 --- [nio-8080-exec-7] c.e.d.controller.PropertiesController    : ==============================================
  ```

#### 自定义文件配置

1. 定义一个名为 `my2.properties` 的资源文件

   ```xml
   my2.age=22
   my2.name=Levin
   my2.email=1837307557@qq.com
   ```

   

2. 其次定义 `MyProperties2.java` 文件，用来映射我们在 `my2.properties` 中的内容

   ```java
   package com.example.demo.properties;
   
   
   import org.springframework.boot.context.properties.ConfigurationProperties;
   import org.springframework.context.annotation.PropertySource;
   import org.springframework.stereotype.Component;
   
   @Component
   @PropertySource("classpath:my2.properties")
   @ConfigurationProperties(prefix = "my2")
   public class MyProperties2 {
   
       private int age;
       private String name;
       private String email;
   
       public int getAge() {
           return age;
       }
   
       public void setAge(int age) {
           this.age = age;
       }
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public String getEmail() {
           return email;
       }
   
       public void setEmail(String email) {
           this.email = email;
       }
   
       @Override
       public String toString() {
           return "MyProperties2{" +
                   "age=" + age +
                   ", name='" + name + '\'' +
                   ", email='" + email + '\'' +
                   '}';
       }
   }
   
   ```

   - `@PropertySource("classpath:my2.properties")`

     此注解用于加载我们自定义的配置文件，classpath指的是resources文件夹的路径

3. 接下来在 `PropertiesController` 用来注入 `MyProperties2` 测试我们编写的代码

   ```java
       @GetMapping("/2")
       public MyProperties2 myProperties2(){
           log.info("==============================================");
           log.info(myProperties2.toString());
           log.info("==============================================");
           return myProperties2;
       }
   ```

4. 访问 http://localhost:8080/dev/properties/2 结果如下（dev是下文设置内容）

   ```xml
   2019-12-28 17:07:19.887  INFO 19260 --- [nio-8080-exec-2] c.e.d.controller.PropertiesController    : ==============================================
   2019-12-28 17:07:19.887  INFO 19260 --- [nio-8080-exec-2] c.e.d.controller.PropertiesController    : MyProperties2{age=24, name='Levin', email='4164165@qq.com'}
   2019-12-28 17:07:19.887  INFO 19260 --- [nio-8080-exec-2] c.e.d.controller.PropertiesController    : ==============================================
   ```

#### 多环境化配置

在真实的应用中，常常会有多个环境（**如：开发，测试，生产等**），不同的环境数据库连接都不一样，这个时候就需要用到`spring.profile.active` 的强大功能了，它的格式为 `application-{profile}.properties`，这里的 `application` 为前缀不能改，`{profile}` 是我们自己定义的。

创建 `application-dev.properties`、`application-test.properties`、`application-prod.properties`，内容分别如下

> application-dev.properties

```
server.servlet.context-path=/dev
```

> application-test.properties

```
server.servlet.context-path=/test
```

> application-prod.properties

```
server.servlet.context-path=/prod
```

在 `application.properties` 配置文件中写入 `spring.profiles.active=dev`，这个时候我们在次访问 http://localhost:8080/properties/1 就没用处了，因为我们设置了它的`context-path=/dev`，所以新的路径就是 http://localhost:8080/dev/properties/1 ，**由此可以看出来我们激活不同的配置读取的属性值是不一样的**

#### 外部命令引导

前面三种方式都是基于配置文件层面的，那么有没有办法外部引导呢，假设这样的场景，我们对已经开发完成的代码打包发布，期间在测试环境测试通过了，那么即可发布上生产，这个时候是修改`application.properties`的配置方便还是直接在**命令参数配置**方便呢，毫无疑问是后者更有说服力。**默认情况下，`SpringApplication` 会将命令行选项参数（即：–property，如–server.port=9000）添加到Environment，命令行属性始终优先于其他属性源。**

> 如何测试？

- 进入到项目目录，此处以我本地目录为主：F:/battcn-workspace/spring-boot2-learning/chapter2
- 然后打开 cmd 程序，不会在当前目录打开 cmd 的请自行百度，输入：`mvn package`
- 打包完毕后进入到：F:/battcn-workspace/spring-boot2-learning/chapter2/target 目录中去，我们可以发现一个名为**chapter2-0.0.1-SNAPSHOT.jar** 的包
- 接着在打开 cmd 程序，输入：`java -jar chapter2-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --my1.age=32`。仔细观察**`spring.profiles.active=test`、`my1.age=32`** 这俩配置的键值是不是似曾相识（不认识的请从开头认真阅读）
- 最后输入测试地址：http://localhost:8080/test/properties/1 我们可以发现返回的JSON变成了 **`{"age":32,"name":"battcn"}`** 表示正确

#### 总结

- 掌握`@ConfigurationProperties`、`@PropertySource` 等注解的用法及作用
- 掌握编写自定义配置
- 掌握外部命令引导配置的方式