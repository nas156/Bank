package ua.bank.project.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.bank.project.dto.UserDTO;
import ua.bank.project.entity.Role;
import ua.bank.project.entity.UserData;
import ua.bank.project.exception.ExistingUserRegistrationException;
import ua.bank.project.repository.UserDataRepository;


@Service
public class RegFormService {

    private final UserDataRepository userDataRepository;

    @Autowired
    public RegFormService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public UserData saveNewUser(UserDTO dto) throws ExistingUserRegistrationException {
        UserData userData = UserData.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(Role.USER)
                .build();
        try{
            userDataRepository.save(userData);
        }catch (DataIntegrityViolationException e){
            throw new ExistingUserRegistrationException("User with login is already exists: ", dto.getUsername());
        }
        return userData;
    }
}