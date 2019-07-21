package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PostWithCommentsDto {
    private PostDto post;
    private List<CommentDto> comments;
}