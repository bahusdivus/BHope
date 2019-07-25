package ru.bahusdivus.bhope.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.bahusdivus.bhope.entities.Post;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findByUserIdAndDeletedFalseOrderByDateDesc(long userId, Pageable pageable);
    Page<Post> findByUserNameIgnoreCaseLikeAndDeletedFalseOrderByDateDesc(@Param("name") String name, Pageable pageable);
    List<Post> findByDeletedFalse();
    Page<Post> findByDeletedFalseOrderByDateDesc(Pageable pageable);
    Page<Post> findByDeletedFalseOrderByLikeCountDesc(Pageable pageable);
}