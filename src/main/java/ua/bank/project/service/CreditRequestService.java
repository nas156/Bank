package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ua.bank.project.entity.CreditRequests;
import ua.bank.project.entity.User;
import ua.bank.project.entity.enums.CreditRequestStatus;
import ua.bank.project.repository.CreditRequestRepository;
import ua.bank.project.repository.UserRepository;
import ua.bank.project.repository.UserWalletRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CreditRequestService {
    final CreditRequestRepository creditRequestRepository;
    final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;

    @Autowired
    public CreditRequestService(CreditRequestRepository creditRequestRepository, UserRepository userRepository, UserWalletRepository userWalletRepository) {
        this.creditRequestRepository = creditRequestRepository;
        this.userRepository = userRepository;
        this.userWalletRepository = userWalletRepository;
    }

    public void makeCreditRequest(int amount, String username){
        CreditRequests creditRequest = CreditRequests.builder()
                .requestAmount(amount)
                .requestDate(LocalDate.now())
                .requestTime(LocalTime.now())
                .creditRequestStatus(CreditRequestStatus.PROCESSING)
                .userWallet(userWalletRepository.findByUser_Username(username).get())
                .build();
        creditRequestRepository.save(creditRequest);
    }
}
