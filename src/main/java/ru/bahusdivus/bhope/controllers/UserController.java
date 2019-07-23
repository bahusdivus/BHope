package ru.bahusdivus.bhope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.services.SecurityService;
import ru.bahusdivus.bhope.services.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final SecurityService securityService;

    public UserController(UserServiceImpl userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping("/main")
    public String main() {
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(Model model, String error, String logout, HttpServletRequest request) {
        if (logout != null) {
            model.addAttribute("logout", "Вы успешно вышли из учетной записи");
        }
        return "login";
    }

    @RequestMapping("/loginError")
    public String loginError(Model model, String username) {
        model.addAttribute("error", "Неверная пара логин-пароль");
        model.addAttribute("username", username);
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                               Model model, String isAdmin) {
        String password = userForm.getPassword();
        userService.saveUser(userForm, isAdmin);
        securityService.autoLogin(userForm.getLogin(), password);
        return "redirect:/main";
    }

    @RequestMapping("/access")
    public String access() {
        return "/access";
    }
}
