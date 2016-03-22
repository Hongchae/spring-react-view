package net.hongchae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index/{value}")
    String index(Model model, @PathVariable String value) {
        model.addAttribute("value", value);
        return "index";
    }
}
