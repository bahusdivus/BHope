package ru.bahusdivus.bhope.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.entities.User;

import java.util.Optional;


public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findByUser(@Param("user") User user);

}