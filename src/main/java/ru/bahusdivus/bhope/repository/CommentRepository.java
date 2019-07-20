package ru.bahusdivus.bhope.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bahusdivus.bhope.entities.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByPostIdAndParentIsNull(long id);
}
