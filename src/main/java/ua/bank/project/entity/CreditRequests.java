package ua.bank.project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime requestDate;
}
