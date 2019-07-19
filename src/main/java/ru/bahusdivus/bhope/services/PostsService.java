package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.entities.User;

import java.util.List;
import java.util.Optional;

public interface PostsService {

    Post addPost(Post post);
    void delete(long id);
    PostDto editPost(Post post);
    Optional<Post> getPostById(long id);
    Iterable<Post> getPosts();
    Iterable<PostDto> getPostsByUser(UserDto user);
}
