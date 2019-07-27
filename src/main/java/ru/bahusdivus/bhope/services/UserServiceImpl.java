package ru.bahusdivus.bhope.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.UserRegistrationDto;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.repository.UserRepository;
import ru.bahusdivus.bhope.dto.UserDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @Override
    public UserDto findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserDto::new).orElse(null);
    }

    @Override
    public User changeUserData(User user, UserRegistrationDto userForm) {
        if (!user.getName().equals(userForm.getName()) && user.getEmail().equals(userForm.getEmail())) {
            userRepository.changeName(user.getId(), userForm.getName());
            user.setName(userForm.getName());
        } else if (!user.getEmail().equals(userForm.getEmail()) && user.getName().equals(userForm.getName())) {
            userRepository.changeEmail(user.getId(), userForm.getEmail());
            user.setEmail(userForm.getEmail());
        } else {
            userRepository.changeNameAndEmail(user.getId(), userForm.getName(), userForm.getEmail());
            user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
        }
        return user;
    }

    @Override
    public User changePassword(User user, UserRegistrationDto userForm) {
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userRepository.changePassword(user.getId(), user.getPassword());
        return user;
    }
}