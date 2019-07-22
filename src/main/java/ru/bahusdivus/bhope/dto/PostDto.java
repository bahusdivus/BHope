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
    private String title;
    private String content;
    private LocalDateTime date;
    private boolean deleted;
    private Integer likeCount;
    private UserDto user;
    private int countComments;

    public PostDto(Post post) {

        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        date = post.getDate();
        deleted = post.isDeleted();
        likeCount = post.getLikeCount();
        user = new UserDto(post.getUser());
        countComments = post.getComments().size();
    }

}
