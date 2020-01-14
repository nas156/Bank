package ua.bank.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bank.project.entity.Role;
import ua.bank.project.service.UserService;

import java.security.Principal;

@Controller
public class LoginFormController {

    private final UserService userService;

    public LoginFormController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/")
    public String getInside(Principal principal){
        Role role = userService.getUserRole(principal.getName());
        switch (role){
            case ADMIN:
                return "redirect:/admin";
            case USER:
                return "redirect:/user";
        }
        return "redirect:/registration";
    }

    @GetMapping(value = "/login")
    public String getLoginForm(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login_form";
    }
}
