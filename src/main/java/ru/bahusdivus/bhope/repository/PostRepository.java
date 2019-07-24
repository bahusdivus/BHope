package ru.bahusdivus.bhope.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.bahusdivus.bhope.entities.Post;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findByUserIdAndDeletedFalse(long userId);

    @Query("select p from Post p, User u where p.deleted = false and p.user = u " +
            "and lower(u.name) like %?1%")
    List<Post> findByUserName(@Param("name") String name);

    List<Post> findByDeletedFalse();
    Page<Post> findByDeletedFalseOrderByDateDesc(Pageable pageable);
    List<Post> findByDeletedFalseOrderByLikeCountDesc();
}