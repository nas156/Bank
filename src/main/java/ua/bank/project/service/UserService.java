package ua.bank.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ua.bank.project.entity.Role;
import ua.bank.project.entity.User;
import ua.bank.project.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveNewUser(User user) {
        //TODO inform the user about the replay email
        // TODO exception to endpoint
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            log.info("{Почтовый адрес уже существует}");
        }

    }

    public Role getUserRole(String username){
        return userRepository.findByUsername(username).get().getRole();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOpt = Optional.ofNullable(userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s + " not found")));
        User user = userOpt.get();
        return user;
    }
}

