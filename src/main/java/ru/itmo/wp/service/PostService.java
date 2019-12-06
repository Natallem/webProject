package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagService tagService;

    public PostService(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void writeComment( Post post, User user, Comment comment) {
        post.addComment(comment);
        comment.setUser(user);
        postRepository.save(post);
    }

    public Post create(PostCredentials postCredentials) {
        Post post = new Post();
        post.setText(postCredentials.getText());
        post.setTitle(postCredentials.getTitle());
        Set<String> tagsStrings = new HashSet<>();
        for (String s : postCredentials.getTags().split("\\s",-1)){
            if (!s.isEmpty())
            tagsStrings.add(s);
        }
        Set<Tag> tags=tagService.saveAll(tagsStrings);
        post.setTags(tags);
        return post;
    }
}
