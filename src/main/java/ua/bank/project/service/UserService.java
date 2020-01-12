package ua.bank.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.bank.project.entity.UserData;
import ua.bank.project.repository.UserDataRepository;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserDataRepository userDataRepository;

    @Autowired
    public UserService(UserDataRepository userRepository) {
        this.userDataRepository = userRepository;
    }



    public void saveNewUser (UserData user){
        //TODO inform the user about the replay email
        // TODO exception to endpoint
        try {
            userDataRepository.save(user);
        } catch (Exception ex){
            log.info("{Почтовый адрес уже существует}");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDataRepository.findByUsername(s);
    }
}

