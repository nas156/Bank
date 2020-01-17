package ua.bank.project.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CreditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

}
