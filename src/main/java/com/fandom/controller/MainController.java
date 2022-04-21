package com.fandom.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class MainController{

    @GetMapping("/")
    public String hihi(){
        return "Hello Worldddddd!!!!!";
    }

    @GetMapping("/admin")
    public String ahah(){
        return "admin page";
    }

    @GetMapping("/user")
    public String userasdd(){
        return "user page";
    }
}
