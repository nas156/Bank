package ua.bank.project.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.bank.project.dto.UserDTO;
import ua.bank.project.entity.UserData;
import ua.bank.project.exception.ExistingUserRegistrationException;
import ua.bank.project.service.RegFormService;


import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/registration")
public class RegFormController {

    private final RegFormService regFormService;

    @Autowired
    public RegFormController(RegFormService regFormService) {
        this.regFormService = regFormService;
    }

    @GetMapping
    public String getRegistrationForm(Model model) {
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "reg_form";
    }

    @PostMapping
    public String registrer(@ModelAttribute( "user" ) @Valid UserDTO userDto,
                            BindingResult result) {

        UserData registered = null;
        if (!result.hasErrors()) {
            registered = createUserAccount(userDto, result);
        }
        if (registered == null) {
            result.rejectValue("username", "string.reg.login.exists");
            return "reg_form";
        } else return "login_form";
    }


    private UserData createUserAccount(@Valid UserDTO userDto, BindingResult result) {
        UserData registered = null;
        try {
            registered = regFormService.saveNewUser(userDto);
        } catch (ExistingUserRegistrationException e) {
            return null;
        }
        return registered;
    }

}
