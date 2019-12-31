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
