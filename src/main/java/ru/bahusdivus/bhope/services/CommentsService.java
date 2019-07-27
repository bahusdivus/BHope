package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;

public interface CommentsService {

    PostWithCommentsDto getPostWithComments(long id);

    CommentDto getComment(long id);

    void saveComment(CommentDto comment);

    void deleteComment(long id);
}
