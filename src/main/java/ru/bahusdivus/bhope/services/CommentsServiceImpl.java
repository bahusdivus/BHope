package ru.bahusdivus.bhope.services;

import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.Comment;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;

    public CommentsServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public PostWithCommentsDto getPost(long id) {
        // Это заглушка, реальный метод будет собирать ДТО из полученных из базы ентитей
        List<Comment> comments = commentRepository.findAllByPostIdAndParentIsNull(id);
        List<CommentDto> commentDtos = comments.stream().map(CommentDto::new).collect(Collectors.toList());
        return new PostWithCommentsDto(id, "Post test text here", commentDtos);
    }

    @Override
    public CommentDto getComment(long id) {
        UserDto user1 = new UserDto(1, "Test User 1");
        return new CommentDto(1, 0, id, user1, "Comment test text 1", LocalDateTime.now(), false, new ArrayList<>());
    }

    @Override
    public void saveComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId())
                .orElse(new Comment(commentDto.getUser().getId(), commentDto.getPost(), commentDto.getParent()));
        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);
    }

}
