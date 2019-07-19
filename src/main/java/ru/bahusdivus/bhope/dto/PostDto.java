package ru.bahusdivus.bhope.dto;

import lombok.*;
import ru.bahusdivus.bhope.entities.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    private String header;
    private String content;
    private LocalDateTime date;
    private boolean deleted;
    private UserDto user;
    private int countComments;

    public PostDto(Post post) {

        id = post.getId();
        header = post.getHeader();
        content = post.getContent();
        date = post.getDate();
        deleted = post.isDeleted();
        user = new UserDto(post.getUser());
        countComments = post.getComments().size();
    }

}
