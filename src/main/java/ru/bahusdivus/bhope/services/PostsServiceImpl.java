package ru.bahusdivus.bhope.services;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostsService {

    private final PostRepository postRepository;

    public PostsServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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


    public Page<PostDto> getPostsOrderByDate(int pageNumber) {
        Page<Post> posts = postRepository.findByDeletedFalseOrderByDateDesc(PageRequest.of(pageNumber, 5));
        Page<PostDto> postDto = posts.map(PostDto::new);
        return postDto;
    }

    @Override
    public Page<PostDto> getPostsOrderByLikeCount(int pageNumber) {
        Page<Post> posts = postRepository.findByDeletedFalseOrderByLikeCountDesc(PageRequest.of(pageNumber, 5));
        Page<PostDto> postDto = posts.map(PostDto::new);
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByDateByLike() {
        Page<Post> posts = postRepository.findByDeletedFalseOrderByDateDesc(PageRequest.of(0, Integer.MAX_VALUE));
        LocalDateTime date = posts.getContent().get(0).getDate();

        return posts.stream()
                .map(PostDto::new)
                .sorted(Comparator.comparing(PostDto::getLikeCount).reversed())
                .filter(x -> x.getDate().isAfter(date.minusDays(1)))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> getPostsByUserId(long userId, int pageNumber) {
        Page<Post> posts = postRepository.findByUserIdAndDeletedFalseOrderByDateDesc(userId,
                PageRequest.of(pageNumber, 5));
        Page<PostDto> postDto = posts.map(PostDto::new);
        return postDto;
    }

    @Override
    public Page<PostDto> getPostsByUserName(String userName, int pageNumber) {
        Page<Post> posts = postRepository.findByUserNameIgnoreCaseLikeAndDeletedFalseOrderByDateDesc
                ("%" + userName.toLowerCase() + "%", PageRequest.of(pageNumber, 5));
        Page<PostDto> postDto = posts.map(PostDto::new);
        return postDto;
    }
}
