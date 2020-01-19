package ua.bank.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bank.project.dto.CreditRequestDTO;
import ua.bank.project.entity.enums.TransactionType;
import ua.bank.project.exception.NoExistingRequestException;
import ua.bank.project.service.AdminService;
import ua.bank.project.service.PayService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService, PayService payService) {
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
    public String confirmRequest(@RequestParam Long id, @ModelAttribute(value = "requestAmount") Integer requestAmount){
        try{
            adminService.confirmCreditRequest(id);
            adminService.createCreditInfo(id, TransactionType.CREDIT_CONFIRMATION, requestAmount);
        }catch (NoExistingRequestException e){
            adminService.deleteRequest(id);
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/rejection")
    public String rejectRequest(@RequestParam Long id, @ModelAttribute(value = "requestAmount") Integer requestAmount){
        try {
            adminService.rejectCreditRequest(id);
            adminService.createCreditInfo(id,
                    TransactionType.CREDIT_REJECTION,
                    requestAmount);
        } catch (NoExistingRequestException e) {
            adminService.deleteRequest(id);
        }
        return "redirect:/admin";
    }
}
