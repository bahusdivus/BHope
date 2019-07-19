package ru.bahusdivus.bhope.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(PostDto postDto) {

        Post post = postRepository.findById(postDto.getId())
                .orElse(new Post(postDto.getUser().getId()));
        post.setHeader(postDto.getHeader());
        post.setContent(postDto.getContent());
        postRepository.save(post);
    }

    @Override
    public void deletePost(long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setDeleted(true);
            postRepository.save(post);
        }
    }

    @Override
    public PostDto getPost(long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.map(PostDto::new).orElse(null);
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUserId(long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(PostDto::new).collect(Collectors.toList());
    }
}
