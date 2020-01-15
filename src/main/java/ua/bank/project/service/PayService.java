package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bank.project.entity.User;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.exception.NotEnoughMoneyToPayException;
import ua.bank.project.repository.UserWalletRepository;

@Service
public class PayService {

    final UserWalletRepository userWalletRepository;

    @Autowired
    public PayService(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    public void payBill(String billCode, int amount, String username) throws NotEnoughMoneyToPayException {
        UserWallet userWallet = userWalletRepository.findUserWalletByUser_Username(username);
        int debit = userWallet.getDebitWallet();
        int credit = userWallet.getCurrentCreditWallet();
        if (debit<amount && credit<(amount - debit)){
            throw new NotEnoughMoneyToPayException("not enough money");
        }
        if (debit<amount){
            userWallet.setDebitWallet(0);
            userWallet.setCurrentCreditWallet(credit-(amount-debit));
            userWalletRepository.save(userWallet);
            return;
        }
        userWallet.setDebitWallet(debit - amount);
        userWalletRepository.save(userWallet);
    }

}
