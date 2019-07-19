package ru.bahusdivus.bhope.services;

import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;

public interface CommentsService {

    PostWithCommentsDto getPost(long id);

    CommentDto getComment(long id);
}
