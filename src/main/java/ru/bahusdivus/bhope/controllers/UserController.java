package ru.bahusdivus.bhope.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.bahusdivus.bhope.dto.UserRegistrationDto;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.services.SecurityService;
import ru.bahusdivus.bhope.services.UserServiceImpl;
import ru.bahusdivus.bhope.utils.UserDetailsUserImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final SecurityService securityService;

    public UserController(UserServiceImpl userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping("/login")
    public String login(Model model, String error, String logout,
                        @AuthenticationPrincipal UserDetails userDetails,
                        HttpServletRequest request) {
        if (userDetails != null) {
            return "redirect:/";
        }
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
    public String registration(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserRegistrationDto userForm,
                               String error,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        if (userDetails != null) {
            return "redirect:/";
        }
        model.addAttribute("userForm", userForm);
        String password = userForm.getPassword();
        String confirmPassword = userForm.getConfirmPassword();
        if ("".equals(userForm.getLogin())) {
            model.addAttribute("error", "Логин не может быть пустым");
            return "registration";
        } else if (userService.findByLogin(userForm.getLogin()) != null) {
            model.addAttribute("error", "Пользователь с таким логином уже зарегистрирован");
            return "registration";
        } else if ("".equals(userForm.getEmail())) {
            model.addAttribute("error", "Email не может быть пустым");
            return "registration";
        } else if (userService.findByEmail(userForm.getEmail()) != null) {
            model.addAttribute("error", "Пользователь с таким email уже зарегистрирован");
            return "registration";
        } else if ("".equals(password)) {
            model.addAttribute("error", "Пароль не может быть пустым");
            return "registration";
        } else if (password.length() < 6) {
            model.addAttribute("error", "Пароль должен быть не меньше 6 символов");
            return "registration";
        } else if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароль не соответствует подтверждению");
            return "registration";
        }
        if ("".equals(userForm.getName())) {
            userForm.setName(userForm.getLogin());
        }
        userService.saveUser(userForm);
        securityService.autoLogin(userForm.getLogin(), password);
        return "redirect:/";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(@AuthenticationPrincipal UserDetailsUserImpl userDetails,
                          String error, String message, Model model) {
        if (userDetails == null) {
            return MyErrorController.getError(401, model);
        }
        model.addAttribute("userDetails", userDetails);
        User user = userService.findByLogin(userDetails.getUsername());
        model.addAttribute("userForm", user);
        return "account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String account(@ModelAttribute("userForm") UserRegistrationDto userForm,
                          @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                          String error, String message, Model model) {
        if (userDetails == null) {
            return MyErrorController.getError(401, model);
        }
        User user = userService.findByLogin(userDetails.getUsername());
        model.addAttribute("userForm", user);
        if (message != null) {
            model.addAttribute("message", message);
            return "account";
        }
        if (error != null) {
            model.addAttribute("error", error);
            return "account";
        }
        model.addAttribute("userDetails", userDetails);

        if (user.getName().equals(userForm.getName())
                && user.getEmail().equalsIgnoreCase(userForm.getEmail())
                && "".equals(userForm.getPassword())
                && "".equals(userForm.getConfirmPassword())) {
            model.addAttribute("message", "Вы не внесли изменений");
            return "account";
        } else if (!user.getEmail().equalsIgnoreCase(userForm.getEmail())
                && userService.findByEmail(userForm.getEmail()) != null) {
            model.addAttribute("error", "Пользователь с таким email существует");
            return "account";
        } else if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("error", "Пароль не соответствует подтверждению");
            return "account";
        }

        user = userService.changeUserData(user, userForm);
        if (!"".equals(userForm.getPassword())) {
            user = userService.changePassword(user, userForm);
            securityService.autoLogin(user.getLogin(), user.getPassword());
        }
        model.addAttribute("message", "Изменения сохранены");
        model.addAttribute("userForm", user);
        return "account";
    }

    @RequestMapping("/access")
    public String access() {
        return "/access";
    }
}
