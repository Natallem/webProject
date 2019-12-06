package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/post/{id}")
    public String writeCommentPost(@PathVariable String id, Model model, @Valid @ModelAttribute("comment") Comment comment,
                                   BindingResult bindingResult,
                                   HttpSession httpSession) {
        Post post = parsePostId(id);
        if (post == null) {
            putMessage(httpSession, "Unknown post!");
            return "redirect:/";
        }
        model.addAttribute("postId", post);
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "Enter first");
            return "redirect:/enter";
        }
        postService.writeComment(post, getUser(httpSession), comment);
        putMessage(httpSession, "You commented post");
        return "redirect:/post/" + id;
    }


    @Guest
    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable String id, HttpSession httpSession) {
        Post post = parsePostId(id);
        if (post == null) {
            putMessage(httpSession, "Unknown post!");
            return "redirect:/";
        }
        model.addAttribute("postId", post);
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    private Post parsePostId(String id) {
        long id_long = 0;
        try {
            id_long = Long.parseLong(id);
        } catch (NumberFormatException ignored) {
        }
        return postService.findById(id_long);
    }
}
