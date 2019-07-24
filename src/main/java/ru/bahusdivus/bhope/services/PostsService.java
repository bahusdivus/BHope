package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.PostDto;

import java.util.List;

public interface PostsService {

    void savePost(PostDto post);
    void deletePost(long id);
    int incrementLikeCount(long id);
    PostDto getPost(long id);
    List<PostDto> getPostsOrderByDate();
    List<PostDto> getPostsOrderByLikeCount();
    List<PostDto> getPostsByLike();
    List<PostDto> getPostsByUserId(long userId);
    List<PostDto> getPostsByUserName(String userName);
}
