package ua.bank.project.entity;

import lombok.*;
import ua.bank.project.entity.enums.CreditRequestStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer requestAmount;

    @OneToOne
    private User user;

    @OneToOne
    UserWallet userWallet;

    @Enumerated(EnumType.STRING)
    private CreditRequestStatus creditRequestStatus;

    private LocalDate requestDate;

    private LocalTime requestTime;
}
