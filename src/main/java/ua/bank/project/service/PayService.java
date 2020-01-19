package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bank.project.entity.User;
import ua.bank.project.entity.UserInfo;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.entity.enums.TransactionType;
import ua.bank.project.exception.BankTransactionException;
import ua.bank.project.exception.NoExistingUserException;
import ua.bank.project.exception.NotEnoughMoneyToPayException;
import ua.bank.project.repository.UserInfoRepository;
import ua.bank.project.repository.UserRepository;
import ua.bank.project.repository.UserWalletRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class PayService {

    final UserWalletRepository userWalletRepository;
    final UserInfoRepository userInfoRepository;
    final UserRepository userRepository;

    @Autowired
    public PayService(UserWalletRepository userWalletRepository
            ,UserInfoRepository userInfoRepository
            ,UserRepository userRepository) {
        this.userWalletRepository = userWalletRepository;
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
    }

    private UserWallet findByUsername(String username) throws NoExistingUserException {
        return userWalletRepository.findByUser_Username(username).orElseThrow(NoExistingUserException::new);
    }


    public void payBill(String billCode, int amount, String username) throws NotEnoughMoneyToPayException, NoExistingUserException{
        addAmount(username, -amount);
    }


    @Transactional
    public void sendMoneyToUser(String recipient, String sender, int amount)throws NotEnoughMoneyToPayException, NoExistingUserException {
        addAmount(sender, -amount);
        addAmount(recipient, amount);
    }

    public void closeCredit(String username) throws NotEnoughMoneyToPayException, NoExistingUserException{
        addAmount(username, -userWalletRepository.findByUser_Username(username).get().getCurrentCreditWallet());
        closeCreditWallet(username);
    }

    private void addAmount(String username, int amount) throws NoExistingUserException, NotEnoughMoneyToPayException {
        UserWallet userWallet = this.findByUsername(username);
        int newBalance = userWallet.getDebitWallet() + amount;
        if (userWallet.getDebitWallet() + amount < 0) {
            throw new NotEnoughMoneyToPayException(
                    "The money in the account '" + username + "' is not enough (" + userWallet.getDebitWallet() + ")");
        }
        userWallet.setDebitWallet(newBalance);
        userWalletRepository.save(userWallet);
    }

    public void createUserInfo(String recipient, String sender, int amount, TransactionType type){
        UserInfo userInfo = UserInfo.builder()
                .type(type)
                .sentValue(amount)
                .user(userRepository.findByUsername(sender).get())
                .transactionTime(LocalTime.now())
                .transactionDate(LocalDate.now())
                .recipient(recipient)
                .build();
        userInfoRepository.save(userInfo);
    }

    public void createCreditInfo(String username, TransactionType type, int amount){
        if (type.equals(TransactionType.CREDIT_CLOSING)) amount =
                userWalletRepository.findByUser_Username(username).get().getCurrentCreditWallet();
        UserInfo userInfo = UserInfo.builder()
                .type(type)
                .sentValue(amount)
                .recipient(null)
                .user(userRepository.findByUsername(username).get())
                .transactionTime(LocalTime.now())
                .transactionDate(LocalDate.now())
                .build();
        userInfoRepository.save(userInfo);
    }
    private void closeCreditWallet(String username) throws NotEnoughMoneyToPayException, NoExistingUserException {
        UserWallet userWallet = this.findByUsername(username);
        userWallet.setCurrentCreditWallet(0);
        userWalletRepository.save(userWallet);
    }

}
