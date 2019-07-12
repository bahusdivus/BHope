package ru.bahusdivus.bhope;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostsController {

    @RequestMapping("/")
    public String getPage(Model model) {
        model.addAttribute("title", "Загогловок");
        return "index";
    }
}
