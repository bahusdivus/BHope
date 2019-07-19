package ru.bahusdivus.bhope.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post addPost(Post post) {

        Post savePost = postRepository.save(post);
        return savePost;

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public PostDto editPost(Post post) {
        return null;
    }

    @Override
    public Optional<Post> getPostById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public Iterable<Post> getPosts() {
        return postRepository.findAll();

    }

    @Override
    public Iterable<PostDto> getPostsByUser(UserDto user) {

        return null;
    }
}
