package ua.bank.project.entity;

import lombok.*;
import org.hibernate.Transaction;
import ua.bank.project.entity.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    private Integer sentValue;

    private String recipient;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDate transactionDate;
    private LocalTime transactionTime;

    @ManyToOne
    private User user;
}
