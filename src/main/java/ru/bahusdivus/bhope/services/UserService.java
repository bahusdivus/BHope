package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.entities.User;

public interface UserService {

    void saveUser(User user, String role);

    User findByLogin(String login);
}
