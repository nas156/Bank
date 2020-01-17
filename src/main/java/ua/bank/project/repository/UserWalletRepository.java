package ua.bank.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bank.project.entity.User;
import ua.bank.project.entity.UserWallet;

import java.util.List;
import java.util.Optional;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
    Optional<UserWallet> findByUser_Username(String username);
}
