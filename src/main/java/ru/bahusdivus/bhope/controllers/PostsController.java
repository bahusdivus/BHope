package ru.bahusdivus.bhope.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bahusdivus.bhope.dto.CommentDto;
import ru.bahusdivus.bhope.dto.PostWithCommentsDto;
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

    @RequestMapping("/comment")
    public String getNewCommentForm() {
        return "commentPage";
    }

    @RequestMapping("/comment/{id}")
    public String getEditCommentForm(@PathVariable("id") long id, Model model) {
        CommentDto comment = commentsService.getComment(id);
        model.addAttribute("comment", comment);
        return "commentPage";
    }

    @RequestMapping(value = "saveComment", method = RequestMethod.POST)
    public String saveComment(@RequestParam CommentDto comment) {
        return "redirect:/post/" + comment.getPost();
    }
}
