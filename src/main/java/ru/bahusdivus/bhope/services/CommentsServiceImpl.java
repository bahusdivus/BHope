package ru.bahusdivus.bhope.services;

import org.springframework.stereotype.Service;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Override
    public PostWithCommentsDto getPost(long id) {
        // Это заглушка, реальный метод будет собирать ДТО из полученных из базы ентитей
        List<CommentDto> comments = getCommentsByPostId(id);
        return new PostWithCommentsDto(id, "Post test text here", comments);
    }

    @Override
    public CommentDto getComment(long id) {
        UserDto user1 = new UserDto(1, "Test User 1");
        return new CommentDto(1, 0, id, user1, "Comment test text 1", LocalDateTime.now(), false, new ArrayList<>());
    }

    private List<CommentDto> getCommentsByPostId(long id) {
        // Это заглушка, реальный метод будет собирать ДТО из полученных из базы ентитей
        List<CommentDto> comments = new ArrayList<>();
        UserDto user1 = new UserDto(1, "Test User 1");
        UserDto user2 = new UserDto(2, "Test User 2");
        comments.add(new CommentDto(1, 0, id, user1, "Comment test text 1", LocalDateTime.now(), false, new ArrayList<>()));
        comments.get(0).getChildren().add(new CommentDto(2, 1, id, user2, "Comment test text 2", LocalDateTime.now(), false, new ArrayList<>()));
        comments.get(0).getChildren().add(new CommentDto(3, 1, id, user2, "Comment test text 3", LocalDateTime.now(), false, null));
        comments.get(0).getChildren().add(new CommentDto(4, 1, id, user2, "Comment test text 4", LocalDateTime.now(), false, null));
        comments.get(0).getChildren().get(0).getChildren().add(new CommentDto(5, 2, id, user1, "Comment test text 5", LocalDateTime.now(), false, null));
        return comments;
    }
}
