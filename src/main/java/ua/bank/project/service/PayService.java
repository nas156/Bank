package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bank.project.entity.User;
import ua.bank.project.entity.UserInfo;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.entity.enums.TransactionType;
import ua.bank.project.exception.NoExistingUserException;
import ua.bank.project.exception.NotEnoughMoneyToPayException;
import ua.bank.project.repository.UserInfoRepository;
import ua.bank.project.repository.UserRepository;
import ua.bank.project.repository.UserWalletRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void payBill(String billCode, int amount, String username) throws NotEnoughMoneyToPayException {
        UserWallet userWallet = userWalletRepository.findByUser_Username(username).get();
        int current = userWallet.getDebitWallet();
        if (isEnoughMoney(amount, current)){
            userWallet.setDebitWallet(current-amount);
            userWalletRepository.save(userWallet);
            UserInfo userInfo = UserInfo.builder()
                    .type(TransactionType.BILL_PAYMENT)
                    .sentValue(amount)
                    .user(userRepository.findByUsername(username).get())
                    .transactionTime(LocalTime.now())
                    .transactionDate(LocalDate.now())
                    .recipient(billCode)
                    .build();
            userInfoRepository.save(userInfo);
        }else throw new NotEnoughMoneyToPayException("not enough money");


    }

    //Problem
    public void sendMoneyToUser(String recipient, String sender, int amount)throws NotEnoughMoneyToPayException, NoExistingUserException {
        if (recipient.equals(sender)) return;
        UserWallet recipientWallet = userWalletRepository.findByUser_Username(recipient).orElseThrow(NoExistingUserException::new);
        UserWallet senderWallet = userWalletRepository.findByUser_Username(sender).get();
        int currentSender = senderWallet.getDebitWallet();
        if (isEnoughMoney(amount, currentSender)){
            senderWallet.setDebitWallet(currentSender-amount);
            recipientWallet.setDebitWallet(recipientWallet.getDebitWallet()+amount);
            UserInfo userInfo = UserInfo.builder()
                    .type(TransactionType.USER_SENDING)
                    .sentValue(amount)
                    .user(userRepository.findByUsername(sender).get())
                    .transactionTime(LocalTime.now())
                    .transactionDate(LocalDate.now())
                    .recipient(recipient)
                    .build();
            userWalletRepository.save(senderWallet);
            userWalletRepository.save(senderWallet);
            userInfoRepository.save(userInfo);
        }else throw new NotEnoughMoneyToPayException("not enough money");
    }

    public void closeCredit(String username) throws NotEnoughMoneyToPayException, NoExistingUserException{
        UserWallet userWallet = userWalletRepository.findByUser_Username(username).orElseThrow(NoExistingUserException::new);
        int debit = userWallet.getDebitWallet();
        int credit = userWallet.getCurrentCreditWallet();
        if(isEnoughMoney(credit, debit)){
            userWallet.setDebitWallet(debit-credit);
            userWallet.setCurrentCreditWallet(0);
            userWalletRepository.save(userWallet);
        }else throw new NotEnoughMoneyToPayException();
    }

    private boolean isEnoughMoney(int amount, int current){
        return amount <= current;
    }

}
