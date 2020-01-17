package ua.bank.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bank.project.entity.UserInfo;

import java.util.ArrayList;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<ArrayList<UserInfo>> findAllByUser_UsernameOrderByTransactionDateDescTransactionTimeDesc(String username);
}
