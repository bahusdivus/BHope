package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bahusdivus.bhope.entities.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    private long parent;
    private long post;
    private UserDto user;
    private String content;
    private LocalDateTime date;
    private boolean deleted;
    private List<CommentDto> children;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        if (comment.getParent() != null) {
            this.parent = comment.getParent().getId();
        } else {
            this.parent = 0;
        }
        this.post = comment.getPost().getId();
        this.user = new UserDto(comment.getUser());
        this.content = comment.getContent();
        this.date = comment.getDate();
        this.deleted = comment.isDeleted();
        this.children = comment.getChildren().stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
