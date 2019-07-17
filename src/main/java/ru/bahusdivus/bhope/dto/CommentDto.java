package ru.bahusdivus.bhope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private long id;
    private long parent;
    private long post;
    private UserDto user;
    private String content;
    private LocalDate date;
    private boolean deleted;
    private List<CommentDto> childrens;
}
