package ua.bank.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ua.bank.project.entity.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserDetails findByUsername(String s);
}
