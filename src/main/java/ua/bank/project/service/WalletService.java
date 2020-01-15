package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bank.project.dto.WalletDTO;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.repository.UserWalletRepository;

@Service
public class WalletService {

    final UserWalletRepository userWalletRepository;

    public WalletService(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    public WalletDTO getWalletByUsername(String username){
        UserWallet userWallet = userWalletRepository.findByUser_Username(username).get();
        return new WalletDTO(userWallet.getDebitWallet(), userWallet.getCurrentCreditWallet());
    }
}
