package ru.bahusdivus.bhope.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bahusdivus.bhope.entities.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUserId(long userId);
    List<Post> findAll();
}