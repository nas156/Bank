package ua.bank.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bank.project.entity.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
}
