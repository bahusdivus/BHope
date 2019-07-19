package ru.bahusdivus.bhope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
import ru.bahusdivus.bhope.dto.UserDto;
import ru.bahusdivus.bhope.services.CommentsService;

@Controller
public class PostsController {

    private final CommentsService commentsService;

    public PostsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @RequestMapping("/post/{postId}")
    public String getPage(@PathVariable("postId") long postId, Model model) {
        PostWithCommentsDto post = commentsService.getPost(postId);
        model.addAttribute("title", "Заголовок");
        model.addAttribute("postId", postId);
        model.addAttribute("content", post.getContent());
        model.addAttribute("comments", post.getComments());
        return "postPage";
    }

    @RequestMapping("/post/{postId}/comment")
    public String getNewCommentForm(@PathVariable("postId") long postId, Model model) {
        return getNewCommentFormWithParent(postId, 0, model);
    }

    @RequestMapping("/post/{postId}/comment/{id}")
    public String getEditCommentForm(@PathVariable("postId") long postId, @PathVariable("id") long id, Model model) {
        CommentDto comment = commentsService.getComment(id);
        model.addAttribute("comment", comment);
        return "commentPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}/comment")
    public String getNewCommentFormWithParent(@PathVariable("postId") long postId, @PathVariable("id") long id, Model model) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPost(postId);
        commentDto.setParent(id);
        commentDto.setUser(new UserDto(1));
        model.addAttribute("comment", commentDto);
        return "commentPage";
    }

    @RequestMapping("/post/{postId}/comment/{id}/delete")
    public String deleteComment(@PathVariable("postId") long postId, @PathVariable("id") long id) {
        commentsService.deleteComment(id);
        return "redirect:/post/" + postId;
    }

    @RequestMapping(value = "saveComment", method = RequestMethod.POST)
    public String saveComment(@ModelAttribute CommentDto comment) {
        commentsService.saveComment(comment);
        return "redirect:/post/" + comment.getPost();
    }
}
