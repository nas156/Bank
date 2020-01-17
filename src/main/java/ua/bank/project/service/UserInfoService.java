package ua.bank.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bank.project.dto.UserInfoDTO;
import ua.bank.project.entity.UserInfo;
import ua.bank.project.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoDTO getUserInfo(String username){
        List<UserInfo> userInfos = userInfoRepository.
                findAllByUser_UsernameOrderByTransactionDateDescTransactionTimeDesc(username)
                .orElse(new ArrayList<>());
        return new UserInfoDTO(userInfos);
    }
}
