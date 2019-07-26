package ru.bahusdivus.bhope.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import ru.bahusdivus.bhope.dto.PostDto;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.services.CommentsService;
import ru.bahusdivus.bhope.services.PostsService;
import ru.bahusdivus.bhope.services.UserService;
import ru.bahusdivus.bhope.utils.UserDetailsUserImpl;

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

    @RequestMapping("/")
    public String getIndex(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        model.addAttribute("userDetails", userDetails);
        List<PostDto> posts = postsService.getPostsByDateByLike();
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping("/posts/byDate/{pageNumber}")
    public String getPosts(@PathVariable("pageNumber") int pageNumber,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        model.addAttribute("userDetails", userDetails);
        Page<PostDto> posts = postsService.getPostsOrderByDate(pageNumber);
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        String byType = "byDate";
        model.addAttribute("byType", byType);
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "posts";
    }

    @RequestMapping("/posts/byLike/{pageNumber}")
    public String getPostsByLike(@PathVariable("pageNumber") int pageNumber,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        model.addAttribute("userDetails", userDetails);
        Page<PostDto> posts = postsService.getPostsOrderByLikeCount(pageNumber);
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        String byType = "byLike";
        model.addAttribute("byType", byType);
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "posts";
    }

    @RequestMapping("/users/{userId}/posts/{pageNumber}")
    public String getPostByUser(@PathVariable("userId") long userId,
                                @PathVariable("pageNumber") int pageNumber,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        model.addAttribute("userDetails", userDetails);
        UserDto userDto = userService.findById(userId);
        PostDto postDto = new PostDto();
        Page<PostDto> posts = postsService.getPostsByUserId(userId, pageNumber);
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "postByUser";
    }

    @RequestMapping(value = "/find/{name}/{pageNumber}")
    public String getPostByUserName(@PathVariable("name") String name,
                                    @PathVariable("pageNumber") int pageNumber,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        model.addAttribute("login", userDetails != null ? userDetails.getUsername() : null);
        model.addAttribute("userDetails", userDetails);
        Page<PostDto> posts = postsService.getPostsByUserName(name, pageNumber);
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        model.addAttribute("user", userDto);
        model.addAttribute("postDto", postDto);
        model.addAttribute("name",name);
        model.addAttribute("posts", posts);
        return "findByUserName";
    }

    @RequestMapping("/post")
    public String getNewPostForm(@AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                 Model model) {

        if (userDetails == null) {
            model.addAttribute("errorMessage", "Нужно авторизоваться");
            model.addAttribute("statusCode", "401");
            return "error";
        }
        model.addAttribute("userDetails", userDetails);

        PostDto postDto = new PostDto();
        postDto.setUser(new UserDto(userDetails.getId()));
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/edit")
    public String getEditPostForm(@PathVariable("postId") long postId,
                                  @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                  Model model) {

        if (userDetails == null) {
            model.addAttribute("errorMessage", "Нужно авторизоваться");
            model.addAttribute("statusCode", "401");
            return "error";
        }
        model.addAttribute("userDetails", userDetails);

        PostDto postDto = postsService.getPost(postId);
        if (userDetails.getId() != postDto.getUser().getId()) {
            model.addAttribute("errorMessage", "Нет доступа");
            model.addAttribute("statusCode", "403");
            return "error";
        }
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId,
                             @AuthenticationPrincipal UserDetailsUserImpl userDetails, Model model) {
        if (userDetails == null) {
            model.addAttribute("errorMessage", "Нужно авторизоваться");
            model.addAttribute("statusCode", "401");
            return "error";
        }
        if (userDetails.getId() != postsService.getPost(postId).getUser().getId()
                && !userDetails.getAdmin()) {
            model.addAttribute("errorMessage", "Нет доступа");
            model.addAttribute("statusCode", "403");
            return "error";
        }
        postsService.deletePost(postId);
        return "redirect:/";
    }

    @RequestMapping(value = "savePost", method = RequestMethod.POST)
    public String savePost(@ModelAttribute PostDto postDto) {
        postsService.savePost(postDto);
        return "redirect:/posts/" + postDto.getId();
    }

    @RequestMapping(value = "findByUserName", method = RequestMethod.POST)
    public String findByUserName(@ModelAttribute UserDto userDto) {
        return "redirect:/find/" + userDto.getName() + "/0";
    }

    @RequestMapping(value = "incrementLikeCount", method = RequestMethod.POST)
    public String incrementLikeCount(@ModelAttribute PostDto postDto) {
        postsService.incrementLikeCount(postDto.getId());
        return "redirect:/";
    }

    @RequestMapping("/post/{postId}")
    public String getPage(@PathVariable("postId") long postId,
                          @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                          Model model) {
        model.addAttribute("userDetails", userDetails);

        PostWithCommentsDto post = commentsService.getPostWithComments(postId);
        model.addAttribute("post", post.getPost());
        model.addAttribute("comments", post.getComments());
        return "postPage";
    }

    @RequestMapping("/post/{postId}/comment")
    public String getNewCommentForm(@PathVariable("postId") long postId,
                                    @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                    Model model) {
        return getNewCommentFormWithParent(postId, 0, userDetails, model);
    }

    @RequestMapping("/post/{postId}/comment/{id}")
    public String getEditCommentForm(@PathVariable("postId") long postId,
                                     @PathVariable("id") long id,
                                     @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                     Model model) {
        if (userDetails == null) {
            model.addAttribute("errorMessage", "Нужно авторизоваться");
            model.addAttribute("statusCode", "401");
            return "error";
        }
        CommentDto comment = commentsService.getComment(id);
        if (userDetails.getId() != comment.getUser().getId()) {
            model.addAttribute("errorMessage", "Нет доступа");
            model.addAttribute("statusCode", "403");
            return "error";
        }
        model.addAttribute("comment", comment);
        return "commentPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}/comment")
    public String getNewCommentFormWithParent(@PathVariable("postId") long postId,
                                              @PathVariable("id") long id,
                                              @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                              Model model) {

        if (userDetails == null) {
            model.addAttribute("errorMessage", "Нужно авторизоваться");
            model.addAttribute("statusCode", "401");
            return "error";
        }
        model.addAttribute("userDetails", userDetails);

        CommentDto commentDto = new CommentDto();
        commentDto.setPost(postId);
        commentDto.setParent(id);
        long userId = userDetails.getId();
        commentDto.setUser(new UserDto(userId));
        model.addAttribute("comment", commentDto);
        return "commentPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}/delete")
    public String deleteComment(@PathVariable("postId") long postId,
                                @PathVariable("id") long id,
                                @AuthenticationPrincipal UserDetailsUserImpl userDetails, Model model) {
        if (userDetails == null) {
            model.addAttribute("errorMessage", "Нужно авторизоваться");
            model.addAttribute("statusCode", "401");
            return "error";
        }
        if (userDetails.getId() != commentsService.getComment(id).getUser().getId()
                && !userDetails.getAdmin()) {
            model.addAttribute("errorMessage", "Нет доступа");
            model.addAttribute("statusCode", "403");
            return "error";
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

