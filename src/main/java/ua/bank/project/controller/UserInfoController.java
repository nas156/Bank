package ua.bank.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.bank.project.dto.UserInfoDTO;
import ua.bank.project.service.UserInfoService;

import java.security.Principal;

@Controller
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping(value = "/user/transaction-info")
    public String describeUserInfo(Principal principal, Model model){
        UserInfoDTO userInfoDTO = userInfoService.getUserInfo(principal.getName());
        model.addAttribute("userInfoDTO", userInfoDTO);
        return "info_for_user";
    }

}
