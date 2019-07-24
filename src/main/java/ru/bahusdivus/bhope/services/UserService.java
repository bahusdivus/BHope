package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.User;

import java.util.Optional;

public interface UserService {

    void saveUser(User user, String role);
    User findByLogin(String login);
    UserDto findById(long id);
}
