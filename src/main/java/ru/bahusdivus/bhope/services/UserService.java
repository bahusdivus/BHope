package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.UserRegistrationDto;
import ru.bahusdivus.bhope.entities.User;

public interface UserService {

    void saveUser(UserRegistrationDto userDto);

    User findByLogin(String login);

    User findByEmail(String login);
}
