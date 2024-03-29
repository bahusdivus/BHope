package ru.bahusdivus.bhope.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    public static String getError(int statusCode, Model model) {
        String message = null;
        if (statusCode == 404) {
            message = "Страница не найдена";
        } else if (statusCode == 401) {
            message = "Нужно авторизоваться";
        } else if (statusCode == 403) {
            message = "Нет доступа";
        }
        model.addAttribute("errorMessage", message);
        model.addAttribute("statusCode", statusCode);

        return "error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                message = "Страница не найдена";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                message = "Внутренняя ошибка сервера";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                message = "Нужно авторизоваться";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                message = "Нет доступа";
            }
            model.addAttribute("errorMessage", message);
            model.addAttribute("statusCode", statusCode);
        } else {
            model.addAttribute("errorMessage", null);
            model.addAttribute("statusCode", null);
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
