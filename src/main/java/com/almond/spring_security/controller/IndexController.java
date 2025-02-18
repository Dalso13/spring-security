package com.almond.spring_security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {


    @RequestMapping(path = {"/", ""}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public @ResponseBody String user() {
        return "user";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public @ResponseBody String admin() {
        return "admin";
    }

    @RequestMapping(path = "/manager", method = RequestMethod.GET)
    public @ResponseBody String manager() {
        return "manager";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public @ResponseBody String login() {
        return "login";
    }

    @RequestMapping(path = "/join", method = RequestMethod.GET)
    public @ResponseBody String join() {
        return "join";
    }

    @RequestMapping(path = "/joinProc", method = RequestMethod.GET)
    public @ResponseBody String joinProc() {
        return "회원가입 완료";
    }
}

