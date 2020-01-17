package ua.bank.project.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
public class NoExistingRequestException extends Exception {
    private String message;
}
