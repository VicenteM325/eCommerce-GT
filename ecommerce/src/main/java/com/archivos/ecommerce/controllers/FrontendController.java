package com.archivos.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping("/{path:^(?!.*\\.).*}")
    public String redirect() {
        return "forward:/index.html";
    }

    @RequestMapping("/{path:^(?!.*\\.).*}/**")
    public String redirectDeep() {
        return "forward:/index.html";
    }
}
