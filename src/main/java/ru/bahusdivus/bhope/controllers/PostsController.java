package ru.bahusdivus.bhope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;
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

    @RequestMapping("/")
    public String getIndex(Model model) {
        List<PostDto> posts = postsService.getPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping("/users/{userId}/posts")
    public String getPostByUser(@PathVariable("userId") long userId, Model model) {
        //Получить имя user по userId
        List<PostDto> posts = postsService.getPostsByUserId(userId);
        model.addAttribute("posts", posts);
        return "postByUser";
    }

    @RequestMapping("/post")
    public String getNewPostForm(Model model) {
        PostDto postDto = new PostDto();
        postDto.setUser(new UserDto(1));
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/edit")
    public String getEditPostForm(@PathVariable("postId") long postId, Model model) {
        PostDto postDto = postsService.getPost(postId);
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId) {
        postsService.deletePost(postId);
        return "redirect:/";
    }

    @RequestMapping(value = "savePost", method = RequestMethod.POST)
    public String savePost(@ModelAttribute PostDto post) {
        postsService.savePost(post);
        return "redirect:/";
    }

}
