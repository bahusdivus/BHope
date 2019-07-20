package ru.bahusdivus.bhope.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.services.CommentsService;
import ru.bahusdivus.bhope.services.PostsService;
import ru.bahusdivus.bhope.services.UserService;

import java.util.List;

@Controller
public class PostsController {

    private final CommentsService commentsService;
    private final PostsService postsService;
    private final UserService userService;

    public PostsController(CommentsService commentsService, PostsService postsService, UserService userService) {
        this.commentsService = commentsService;
        this.postsService = postsService;
        this.userService = userService;
    }

    @RequestMapping("/post/{postId}")
    public String getPage(@PathVariable("postId") long postId,
                          @AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);

        PostWithCommentsDto post = commentsService.getPost(postId);
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        model.addAttribute("title", "Заголовок");
        model.addAttribute("postId", postId);
        model.addAttribute("content", post.getContent());
        model.addAttribute("comments", post.getComments());
        return "postPage";
    }

    @RequestMapping("/")
    public String getIndex(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        List<PostDto> posts = postsService.getPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping("/users/{userId}/posts")
    public String getPostByUser(@PathVariable("userId") long userId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        //Получить имя user по userId

//        long userId = userService.findByLogin(userLogin).getId();
        List<PostDto> posts = postsService.getPostsByUserId(userId);
        model.addAttribute("posts", posts);
        return "postByUser";
    }

    @RequestMapping("/post")
    public String getNewPostForm(@AuthenticationPrincipal UserDetails userDetails,
                                 Model model) throws IllegalAccessException {

        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        model.addAttribute("login", userDetails.getUsername());

        PostDto postDto = new PostDto();
        postDto.setUser(new UserDto(1));
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/edit")
    public String getEditPostForm(@PathVariable("postId") long postId,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) throws IllegalAccessException {

        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        model.addAttribute("login", userDetails.getUsername());

        PostDto postDto = postsService.getPost(postId);
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId,
                             @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        postsService.deletePost(postId);
        return "redirect:/";
    }

    @RequestMapping(value = "savePost", method = RequestMethod.POST)
    public String savePost(@ModelAttribute PostDto post) {
        postsService.savePost(post);
        return "redirect:/";
    }

    @RequestMapping("/post/{postId}/comment")
    public String getNewCommentForm(@PathVariable("postId") long postId,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) throws IllegalAccessException {
        return getNewCommentFormWithParent(postId, 0, userDetails, model);
    }

    @RequestMapping("/post/{postId}/comment/{id}")
    public String getEditCommentForm(@PathVariable("postId") long postId,
                                     @PathVariable("id") long id,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     Model model) throws IllegalAccessException {
        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        CommentDto comment = commentsService.getComment(id);
        model.addAttribute("comment", comment);
        return "commentPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}/comment")
    public String getNewCommentFormWithParent(@PathVariable("postId") long postId,
                                              @PathVariable("id") long id,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              Model model) throws IllegalAccessException {

        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        model.addAttribute("login", userDetails.getUsername());

        CommentDto commentDto = new CommentDto();
        commentDto.setPost(postId);
        commentDto.setParent(id);
        long userId = userService.findByLogin(userDetails.getUsername()).getId();
        commentDto.setUser(new UserDto(userId));
        model.addAttribute("comment", commentDto);
        return "commentPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}/delete")
    public String deleteComment(@PathVariable("postId") long postId,
                                @PathVariable("id") long id,
                                @AuthenticationPrincipal UserDetails userDetails) throws IllegalAccessException {
        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        commentsService.deleteComment(id);
        return "redirect:/post/" + postId;
    }

    @RequestMapping(value = "saveComment", method = RequestMethod.POST)
    public String saveComment(@ModelAttribute CommentDto comment) {
        commentsService.saveComment(comment);
        return "redirect:/post/" + comment.getPost();
    }
}
