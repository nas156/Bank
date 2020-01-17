package ua.bank.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bank.project.dto.WalletDTO;
import ua.bank.project.exception.NoExistingUserException;
import ua.bank.project.exception.NotEnoughMoneyToPayException;
import ua.bank.project.service.CreditRequestService;
import ua.bank.project.service.PayService;
import ua.bank.project.service.WalletService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    final WalletService walletService;
    final CreditRequestService creditRequestService;
    final PayService payService;

    @Autowired
    public UserController(WalletService walletService, CreditRequestService creditRequestService, PayService payService) {
        this.walletService = walletService;
        this.creditRequestService = creditRequestService;
        this.payService = payService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('USER')" )
    public String getMainPage(Principal principal, Model model) {
        WalletDTO walletDTO = walletService.getWalletByUsername(principal.getName());
        model.addAttribute("walletDTO", walletDTO);
        return "userpage";
    }

    @RequestMapping(value = "/request")
    public String openCreditWallet(@RequestParam int amount, Principal principal){
        creditRequestService.makeCreditRequest(amount, principal.getName());
        return "redirect:/user";
    }

    @RequestMapping(value = "/paybill")
    public String payBill(@RequestParam String code, @RequestParam int amount, Principal principal)  {
        try{
            payService.payBill(code, amount, principal.getName());
        }
        catch (NotEnoughMoneyToPayException e){
            return "redirect:/user?amount=notenough";
        }
        return "redirect:/user?payed=true";
    }

    @RequestMapping(value = "/sendmoney")
    public String sendMoney(@RequestParam String recipientUsername, @RequestParam int amount, Principal principal){
        try {
            payService.sendMoneyToUser(recipientUsername, principal.getName(), amount);
        } catch (NotEnoughMoneyToPayException e) {
            return "redirect:/user?amount=notenough";
        } catch (NoExistingUserException e) {
            return "redirect:/user?user=notfound";
        }
        return "redirect:/user?sended=true";

    }

    @RequestMapping(value = "/close-credit")
    public String closeCredit(Principal principal){
        try {
            payService.closeCredit(principal.getName());
        } catch (NotEnoughMoneyToPayException e) {
            return "redirect:/user?amount=notenough";
        } catch (NoExistingUserException e) {
            return "redirect:/user?user=notfound";
        }
        return "redirect:/user";
    }

}
