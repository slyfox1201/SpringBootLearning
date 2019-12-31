#### thymeleaf介绍

> 特点

- 支持无网络环境下运行，由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。所以它可以让前端小姐姐在浏览器中查看页面的静态效果，又可以让程序员小哥哥在服务端查看带数据的动态页面效果。
- 开箱即用，为`Spring`提供方言，可直接套用模板实现`JSTL、 OGNL`表达式效果，避免每天因套用模板而修改`JSTL、 OGNL`标签的困扰。同时开发人员可以扩展自定义的方言。
- `SpringBoot`官方推荐模板，提供了可选集成模块(`spring-boot-starter-thymeleaf`)，可以快速的实现表单绑定、属性编辑器、国际化等功能。

#### 使用

1. 添加依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-thymeleaf</artifactId>
   </dependency>
   ```

   

2. 编写controller，将需要传递的数据存到==ModelAndView==或==HttpServletRequest==中，以供thymeleaf调用，以下展示了两种传递数据的方法

   ```java
   package com.example.demo.controller;
   
   
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.servlet.ModelAndView;
   
   import javax.servlet.http.HttpServletRequest;
   
   @Controller
   @RequestMapping
   public class ThymeleafController {
   
       @GetMapping("/index")
       public ModelAndView index(){
           ModelAndView view = new ModelAndView();
   
           view.setViewName("index");
   
           view.addObject("title", "我的第一个界面");
           view.addObject("desc", "111111111111");
   
           Author author = new Author();
           author.setAge(22);
           author.setEmail("1837307557@qq.com");
           author.setName("唐亚峰");
   
           view.addObject("author", author);
   
           return view;
       }
   
       @GetMapping("/index1")
       public String index1(HttpServletRequest request){
           request.setAttribute("title" ,"我的第二个界面");
           request.setAttribute("desc", "2222222222");
   
           Author author = new Author();
           author.setAge(22);
           author.setEmail("1837307557@qq.com");
           author.setName("唐亚峰");
   
           request.setAttribute("author", author);
   
           return "index";
       }
   
       class Author{
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
       }
   }
   
   ```

   

3. 使用thymeleaf编写html

   ```xml
   <!DOCTYPE html>
   <html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
   <head>
       <meta charset="UTF-8">
       <title th:text="${title}">Title</title>
       <script type="text/javascript" th:src="@{/js/hello.js}"></script>
   </head>
   <body>
   <h1 th:text="${desc}">Hello world</h1>
   <h2>=====作者信息=====</h2>
   <p th:text="${author?.name}"></p>
   <p th:text="${author?.age}"></p>
   <p th:text="${author?.email}"></p>
   </body>
   </html>
   ```

   ==author?.name==中的？

   表示：若author不为null，则取author的name值

   

