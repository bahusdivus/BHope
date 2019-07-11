package ru.bahusdivus.bhope;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostsController {

    @RequestMapping("/")
    @ResponseBody
    public String getPage() {
        return "";
    }
}
