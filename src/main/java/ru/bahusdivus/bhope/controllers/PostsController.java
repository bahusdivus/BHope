package ru.bahusdivus.bhope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.entities.Post;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.services.CommentsService;
import ru.bahusdivus.bhope.services.PostsService;

import java.util.List;

@Controller
public class PostsController {

    private final CommentsService commentsService;
    private final PostsService postsService;

    public PostsController(CommentsService commentsService, PostsService postsService) {

        this.commentsService = commentsService;
        this.postsService = postsService;
    }

    @RequestMapping("/post/{postId}")
    public String getPostPage(@PathVariable("postId") long postId, Model model) {
        PostWithCommentsDto post = commentsService.getPost(postId);
        model.addAttribute("title", "Заголовок");
        model.addAttribute("postId", postId);
        model.addAttribute("content", post.getContent());
        model.addAttribute("comments", post.getComments());
        return "postPage";
    }

    @RequestMapping("/index")
    public String getIndex(Model model) {
        Iterable<Post> posts = postsService.getPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping("/postByUser")
    public String getPostByUser(Model model) {
        Iterable<Post> posts = postsService.getPosts();
        model.addAttribute("posts", posts);
        return "postByUser";
    }
}
