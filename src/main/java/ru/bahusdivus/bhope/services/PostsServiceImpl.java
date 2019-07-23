package ru.bahusdivus.bhope.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.repository.PostRepository;

import java.util.Comparator;
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
                .orElse(new Post(postDto.getUser()));
        post.setTitle(postDto.getTitle());
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
    public void incrementLikeCount(long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
        }
    }

    @Override
    public PostDto getPost(long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.map(PostDto::new).orElse(null);
    }

    @Override
    public List<PostDto> getPostsOrderByDate() {
        List<Post> posts = postRepository.findByDeletedFalseOrderByDateDesc();
        return posts.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsOrderByLikeCount() {
        List<Post> posts = postRepository.findByDeletedFalseOrderByLikeCountDesc();
        return posts.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByLike() {
        List<Post> posts = postRepository.findByDeletedFalse();
        return posts.stream()
                .map(PostDto::new)
                .sorted(Comparator.comparing(PostDto::getDate).thenComparing(PostDto::getLikeCount).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUserId(long userId) {
        List<Post> posts = postRepository.findByUserIdAndDeletedFalse(userId);
        return posts.stream()
                .map(PostDto::new)
                .sorted(Comparator.comparing(PostDto::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
     public List<PostDto> getPostsByUserName(String userName) {
        List<Post> posts = postRepository.findByUserName(userName.toLowerCase());
        return posts.stream()
                .map(PostDto::new)
                .sorted(Comparator.comparing(PostDto::getDate).reversed())
                .collect(Collectors.toList());
    }
}
