package ru.bahusdivus.bhope.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import ru.bahusdivus.bhope.utils.UserDetailsUserImpl;

import java.util.List;

@Controller
public class PostsController {

    private final CommentsService commentsService;
    private final PostsService postsService;

    public PostsController(CommentsService commentsService, PostsService postsService) {
        this.commentsService = commentsService;
        this.postsService = postsService;
    }

    @RequestMapping("/")
    public String getIndex(@AuthenticationPrincipal UserDetailsUserImpl userDetails, Model model) {
        model.addAttribute("userDetails", userDetails);
        List<PostDto> posts = postsService.getPostsByLike();
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping("/posts")
    public String getPosts(@AuthenticationPrincipal UserDetailsUserImpl userDetails, Model model) {
        model.addAttribute("login", userDetails);
        List<PostDto> posts = postsService.getPostsOrderByDate();
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "posts";
    }

    @RequestMapping("/posts/byLike")
    public String getPostsByLike(@AuthenticationPrincipal UserDetailsUserImpl userDetails, Model model) {
        model.addAttribute("login", userDetails);
        List<PostDto> posts = postsService.getPostsOrderByLikeCount();
        UserDto userDto = new UserDto();
        PostDto postDto = new PostDto();
        model.addAttribute("postDto", postDto);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", posts);
        return "posts";
    }

    @RequestMapping("/users/{userId}/posts")
    public String getPostByUser(@PathVariable("userId") long userId,
                                @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                Model model) {
        model.addAttribute("userDetails", userDetails);
        List<PostDto> posts = postsService.getPostsByUserId(userId);
        model.addAttribute("posts", posts);
        return "postByUser";
    }

    @RequestMapping("/find/{name}")
    public String getPostByUserName(@PathVariable("name") String name,
                                    @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                    Model model) {
        model.addAttribute("userDetails", userDetails);
        List<PostDto> posts = postsService.getPostsByUserName(name);
        model.addAttribute("name", name);
        model.addAttribute("posts", posts);
        return "findByUserName";
    }

    @RequestMapping("/post")
    public String getNewPostForm(@AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                 Model model) throws IllegalAccessException {

        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
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
                                  Model model) throws IllegalAccessException {

        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        model.addAttribute("userDetails", userDetails);

        PostDto postDto = postsService.getPost(postId);
        model.addAttribute("post", postDto);
        return "editPost";
    }

    @RequestMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable("postId") long postId,
                             @AuthenticationPrincipal UserDetailsUserImpl userDetails) throws IllegalAccessException {
        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
        }
        postsService.deletePost(postId);
        return "redirect:/";
    }

    @RequestMapping(value = "savePost", method = RequestMethod.POST)
    public String savePost(@ModelAttribute PostDto postDto) {
        postsService.savePost(postDto);
        return "redirect:/";
    }

    @RequestMapping(value = "findByUserName", method = RequestMethod.POST)
    public String findByUserName(@ModelAttribute UserDto userDto) {
        return "redirect:/find/" + userDto.getName();
    }

    @RequestMapping(value = "incrementLikeCount", method = RequestMethod.POST)
    public String incrementLikeCount(@ModelAttribute PostDto postDto) {
        postsService.incrementLikeCount(postDto.getId());
        return "redirect:/";
    }

    @RequestMapping("/post/{postId}/comment")
    public String getNewCommentForm(@PathVariable("postId") long postId,
                                    @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                    Model model) throws IllegalAccessException {
        return getNewCommentFormWithParent(postId, 0, userDetails, model);
    }

    @RequestMapping("/post/{postId}")
    public String getPage(@PathVariable("postId") long postId,
                          @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                          Model model) {
        model.addAttribute("userDetails", userDetails);

        model.addAttribute("userDetails", userDetails);
        PostWithCommentsDto post = commentsService.getPostWithComments(postId);
        model.addAttribute("post", post.getPost());
        model.addAttribute("comments", post.getComments());
        return "postPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}")
    public String getEditCommentForm(@PathVariable("postId") long postId,
                                     @PathVariable("id") long id,
                                     @AuthenticationPrincipal UserDetailsUserImpl userDetails,
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
                                              @AuthenticationPrincipal UserDetailsUserImpl userDetails,
                                              Model model) throws IllegalAccessException {

        if (userDetails == null) {
            throw new IllegalAccessException("нужно зарегистрироваться");
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
                                @AuthenticationPrincipal UserDetailsUserImpl userDetails) throws IllegalAccessException {
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
