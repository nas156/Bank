package ua.bank.project.service;

import org.springframework.stereotype.Service;
import ua.bank.project.dto.CreditRequestDTO;
import ua.bank.project.entity.CreditRequests;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.entity.enums.CreditRequestStatus;
import ua.bank.project.exception.NoExistingUserException;
import ua.bank.project.repository.CreditRequestRepository;
import ua.bank.project.repository.UserWalletRepository;

import java.time.LocalDate;


@Service
public class AdminService {
    private final CreditRequestRepository creditRequestRepository;
    private final UserWalletRepository userWalletRepository;

    public AdminService(CreditRequestRepository creditRequestRepository, UserWalletRepository repository) {
        this.creditRequestRepository = creditRequestRepository;
        this.userWalletRepository = repository;
    }

    public CreditRequestDTO getCreditRequests(){
        return new CreditRequestDTO(creditRequestRepository.findAllByRequestDateOrderByRequestTimeDesc(LocalDate.now()));
    }

    public void confirmCreditRequest(Long id) throws NoExistingUserException {
        CreditRequests creditRequest = creditRequestRepository
                .findById(id)
                .orElseThrow(NoExistingUserException::new);
        creditRequest.setCreditRequestStatus(CreditRequestStatus.CONFIRMED);
        creditRequestRepository.save(creditRequest);
        UserWallet userWallet = creditRequest.getUserWallet();
        userWallet.setCurrentCreditWallet(creditRequest.getRequestAmount());
        userWallet.setBeginningCreditWallet(creditRequest.getRequestAmount());
        userWalletRepository.save(userWallet);
    }

    public void deleteRequest(Long id){
        creditRequestRepository.deleteById(id);
    }

    public void rejectCreditRequest(Long id) throws NoExistingUserException{
        CreditRequests creditRequests = creditRequestRepository
                .findById(id)
                .orElseThrow(NoExistingUserException::new);
        creditRequests.setCreditRequestStatus(CreditRequestStatus.REJECTED);
        creditRequestRepository.save(creditRequests);
    }
}
