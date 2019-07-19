package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.bahusdivus.bhope.entities.Comment;
import ru.bahusdivus.bhope.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private long id;
    private String header;
    private String content;
    private LocalDateTime date;
    private boolean deleted;
    private UserDto user;
    private List<CommentDto> comments;

    public PostDto(Post post) {

        id = post.getId();
        header = post.getHeader();
        content = post.getContent();
        date = post.getDate();
        deleted = post.isDeleted();
        user = new UserDto(post.getUser());

        for (Comment comment : post.getComments()) {
            comments.add(new CommentDto(comment));
        }

    }
}
