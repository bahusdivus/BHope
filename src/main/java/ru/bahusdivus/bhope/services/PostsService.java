package ru.bahusdivus.bhope.services;

import org.springframework.data.domain.Page;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.entities.User;

import java.util.List;
import java.util.Optional;

public interface PostsService {

    void savePost(PostDto post);
    void deletePost(long id);
    void incrementLikeCount(long id);
    PostDto getPost(long id);
    Page<PostDto> getPostsOrderByDate(int pageNumber);
    Page<PostDto> getPostsOrderByLikeCount(int pageNumber);
    List<PostDto> getPostsByDateByLike();
    Page<PostDto> getPostsByUserId(long userId, int pageNumber);
    Page<PostDto> getPostsByUserName(String userName, int pageNumber);
}
