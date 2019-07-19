package ru.bahusdivus.bhope.services;

import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.entities.Comment;
import ru.bahusdivus.bhope.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
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
        Optional<Comment> commentOptional = commentRepository.findById(id);
        return commentOptional.map(CommentDto::new).orElse(null);
    }

    @Override
    public void saveComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId())
                .orElse(new Comment(commentDto.getUser().getId(), commentDto.getPost(), commentDto.getParent()));
        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setDeleted(true);
            commentRepository.save(comment);
        }
    }
}