package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bank.project.dto.CreditRequestDTO;
import ua.bank.project.entity.CreditRequests;
import ua.bank.project.entity.UserInfo;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.entity.enums.CreditRequestStatus;
import ua.bank.project.entity.enums.TransactionType;
import ua.bank.project.exception.NoExistingRequestException;
import ua.bank.project.repository.CreditRequestRepository;
import ua.bank.project.repository.UserInfoRepository;
import ua.bank.project.repository.UserRepository;
import ua.bank.project.repository.UserWalletRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class AdminService {
    private final CreditRequestRepository creditRequestRepository;
    private final UserWalletRepository userWalletRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public AdminService(CreditRequestRepository creditRequestRepository
            , UserWalletRepository repository
            , UserInfoRepository userInfoRepository) {
        this.creditRequestRepository = creditRequestRepository;
        this.userWalletRepository = repository;
        this.userInfoRepository = userInfoRepository;
    }

    public CreditRequestDTO getCreditRequests(){
        return new CreditRequestDTO(creditRequestRepository.findAllByRequestDateOrderByRequestTimeDesc(LocalDate.now()));
    }

    @Transactional
    public void confirmCreditRequest(Long id) throws NoExistingRequestException {
        CreditRequests creditRequest = creditRequestRepository
                .findById(id)
                .orElseThrow(NoExistingRequestException::new);
        creditRequest.setCreditRequestStatus(CreditRequestStatus.CONFIRMED);
        creditRequestRepository.save(creditRequest);
        giveCredit(creditRequest.getUserWallet(), creditRequest.getRequestAmount());
    }

    public void deleteRequest(Long id){
        creditRequestRepository.deleteById(id);
    }

    public void rejectCreditRequest(Long id) throws NoExistingRequestException{
        CreditRequests creditRequests = creditRequestRepository
                .findById(id)
                .orElseThrow(NoExistingRequestException::new);
        creditRequests.setCreditRequestStatus(CreditRequestStatus.REJECTED);
        creditRequestRepository.save(creditRequests);
    }
    private void giveCredit(UserWallet userWallet, int requestAmount){
        userWallet.setCurrentCreditWallet(userWallet.getCurrentCreditWallet()
                +requestAmount);
        userWallet.setBeginningCreditWallet(userWallet.getCurrentCreditWallet()
                +requestAmount);
        userWallet.setDebitWallet(userWallet.getDebitWallet()
                +requestAmount);
        userWalletRepository.save(userWallet);
    }

    public void createCreditInfo(Long id, TransactionType type, int amount){
        UserInfo userInfo = UserInfo.builder()
                .user(creditRequestRepository.findById(id).get().getUserWallet().getUser())
                .recipient(null)
                .type(type)
                .transactionDate(LocalDate.now())
                .transactionTime(LocalTime.now())
                .sentValue(amount)
                .build();
        userInfoRepository.save(userInfo);
    }
}
