package ua.bank.project.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@ToString
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Integer debitWallet;

    @Column
    private Integer currentCreditWallet;

    @Column
    private Integer beginningCreditWallet;

    @OneToOne
    private User user;

}
