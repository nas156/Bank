package ua.bank.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bank.project.entity.CreditRequests;

public interface CreditRequestRepository extends JpaRepository<CreditRequests, Long> {
}
