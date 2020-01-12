package ua.bank.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.bank.project.entity.Role;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String username;
    private String email;
    private String lastName;
    private String password;
}