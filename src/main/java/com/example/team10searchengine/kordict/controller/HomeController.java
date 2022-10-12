package com.example.team10searchengine.kordict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping(path = "/kordict")
    public String wiki() { return "kordict.html";}

}
