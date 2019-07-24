package ru.bahusdivus.bhope.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.UserRegistrationDto;
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
    public void saveUser(UserRegistrationDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setAdmin(false);
        userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findUserByLoginIgnoreCase(login);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email);
    }
}
