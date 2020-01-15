package ua.bank.project.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer debitWallet;

    private Integer currentCreditWallet;

    private Integer beginningCreditWallet;

    @OneToOne
    private User user;

}
