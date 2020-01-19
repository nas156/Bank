package ua.bank.project.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BankTransactionException extends Exception {
    private String message;
}
