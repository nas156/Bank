package ua.bank.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExistingUserRegistrationException extends Exception {
    private String message;
    private String login;
}
