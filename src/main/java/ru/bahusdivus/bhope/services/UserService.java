package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.UserRegistrationDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.User;

public interface UserService {

    void saveUser(UserRegistrationDto userDto);
    User findByLogin(String login);
    User findByEmail(String login);
    UserDto findById(long id);

    User changeUserData(User user, UserRegistrationDto userForm);

    User changePassword(User user, UserRegistrationDto userForm);
}
