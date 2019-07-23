package ru.bahusdivus.bhope.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(User user, String isAdmin) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setDate(Timestamp.valueOf(LocalDateTime.now()));

        if ("ADMIN".equalsIgnoreCase(isAdmin)) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }
        userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findUserByLoginIgnoreCase(login);
    }
}
