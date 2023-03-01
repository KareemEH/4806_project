package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class GUIController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/book")
    public String book(@RequestParam("title") String title, Model model){
        model.addAttribute("path", "images/" + title);
        return "book";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("register")
    public String register(){
        return "register";
    }
}
