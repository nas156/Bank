package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ua.bank.project.entity.CreditRequests;
import ua.bank.project.entity.User;
import ua.bank.project.repository.CreditRequestRepository;
import ua.bank.project.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class CreditRequestService {
    final CreditRequestRepository creditRequestRepository;
    final UserRepository userRepository;

    @Autowired
    public CreditRequestService(CreditRequestRepository creditRequestRepository,UserRepository userRepository) {
        this.creditRequestRepository = creditRequestRepository;
        this.userRepository = userRepository;
    }

    public void makeCreditRequest(int amount, String username){
        User user = userRepository.findByUsername(username).get();
        CreditRequests creditRequest = CreditRequests.builder()
                .requestAmount(amount)
                .requestDate(LocalDateTime.now())
                .user(user)
                .build();
        creditRequestRepository.save(creditRequest);
    }
}
