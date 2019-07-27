package ru.bahusdivus.bhope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bahusdivus.bhope.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLoginIgnoreCase(String login);

    User findUserByEmailIgnoreCase(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.name = ?2 where u.id = ?1")
    int changeName(Long id, String name);

    @Modifying
    @Transactional
    @Query("update User u set u.email = ?2 where u.id = ?1")
    int changeEmail(Long id, String email);

    @Modifying
    @Transactional
    @Query("update User u set u.name = ?2, u.email = ?3 where u.id = ?1")
    int changeNameAndEmail(Long id, String name, String email);

    @Modifying
    @Transactional
    @Query("update User u set u.password = ?2 where u.id = ?1")
    int changePassword(Long id, String password);
}
