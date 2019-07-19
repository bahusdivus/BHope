package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    public CommentDto(long parent, long post, UserDto user, String content) {
        this.parent = parent;
        this.post = post;
        this.user = user;
        this.content = content;
    }
}
