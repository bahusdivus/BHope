package ru.bahusdivus.bhope.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.bahusdivus.bhope.entities.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUserId(long userId);

    @Query("select p from Post p where p.deleted = false")
    List<Post> findAll();
}