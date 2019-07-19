package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.bahusdivus.bhope.entities.User;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PostWithCommentsDto {
    private long id;
    private String content;
    private UserDto user;
    private List<CommentDto> comments;
}
