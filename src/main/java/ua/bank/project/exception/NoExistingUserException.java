package ua.bank.project.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@Getter
@NoArgsConstructor
public class NoExistingUserException extends Exception {
}
