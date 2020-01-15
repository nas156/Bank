package ua.bank.project.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.bank.project.dto.UserDTO;
import ua.bank.project.entity.Role;
import ua.bank.project.entity.User;
import ua.bank.project.entity.UserWallet;
import ua.bank.project.exception.ExistingUserRegistrationException;
import ua.bank.project.repository.UserRepository;
import ua.bank.project.repository.UserWalletRepository;


@Service
public class RegFormService {

    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegFormService(UserRepository userRepository, UserWalletRepository userWalletRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userWalletRepository = userWalletRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveNewUser(UserDTO dto) throws ExistingUserRegistrationException {
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(Role.USER)
                .build();
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        try{
            userRepository.save(user);
            UserWallet userWallet = UserWallet.builder()
                    .debitWallet(1000)
                    .currentCreditWallet(0)
                    .user(user)
                    .build();
            userWalletRepository.save(userWallet);
        }catch (DataIntegrityViolationException e){
            throw new ExistingUserRegistrationException("User with login is already exists: ", dto.getUsername());
        }
        return user;
    }
}