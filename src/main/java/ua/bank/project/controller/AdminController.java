package ua.bank.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bank.project.dto.CreditRequestDTO;
import ua.bank.project.exception.NoExistingUserException;
import ua.bank.project.service.AdminService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String adminWork(Model model){
        CreditRequestDTO requestDTO = adminService.getCreditRequests();
        model.addAttribute("requestDTO", requestDTO);
        return "admin";
    }

    @RequestMapping(value = "/confirmation")
    public String confirmRequest(@RequestParam Long id){
        try{
            adminService.confirmCreditRequest(id);
        }catch (NoExistingUserException e){
            adminService.deleteRequest(id);
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/rejection")
    public String rejectRequest(@RequestParam Long id){
        try {
            adminService.rejectCreditRequest(id);
        } catch (NoExistingUserException e) {
            adminService.deleteRequest(id);
        }
        return "redirect:/admin";
    }
}
