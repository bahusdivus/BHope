package ru.bahusdivus.bhope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.bahusdivus.bhope.dto.UserRegistrationDto;
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
    public String registration(@ModelAttribute("userForm") UserRegistrationDto userForm,
                               String error,
                               Model model) {
        String password = userForm.getPassword();
        String confirmPassword = userForm.getConfirmPassword();
        if (userService.findByLogin(userForm.getLogin()) != null) {
            model.addAttribute("error", "Пользователь с таким именем уже зарегистрирован");
            model.addAttribute("userForm", userForm);
            return "registration";
        } else if (userService.findByEmail(userForm.getEmail()) != null) {
            model.addAttribute("error", "Пользователь с таким email уже зарегистрирован");
            model.addAttribute("userForm", userForm);
            return "registration";
        } else if (("").equals(password)) {
            model.addAttribute("error", "Пароль не может быть пустым");
            model.addAttribute("userForm", userForm);
            return "registration";
        } else if (password.length() < 6) {
            model.addAttribute("error", "Пароль должен быть не меньше 6 символов");
            model.addAttribute("userForm", userForm);
            return "registration";
        } else if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароль не соответствует подтверждению");
            model.addAttribute("userForm", userForm);
            return "registration";
        }
        if (("").equals(userForm.getName())) {
            userForm.setName(userForm.getLogin());
        }
        userService.saveUser(userForm);
        securityService.autoLogin(userForm.getLogin(), password);
        return "redirect:/main";
    }

    @RequestMapping("/access")
    public String access() {
        return "/access";
    }
}
