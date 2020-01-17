package ua.bank.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bank.project.entity.CreditRequests;

import java.time.LocalDate;
import java.util.List;

public interface CreditRequestRepository extends JpaRepository<CreditRequests, Long> {
    List<CreditRequests> findAllByRequestDateOrderByRequestTimeDesc(LocalDate date);
}
