package ru.bahusdivus.bhope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bahusdivus.bhope.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLoginIgnoreCase(String login);

    User findUserByEmailIgnoreCase(String email);
}
