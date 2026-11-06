package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.controller.dto.PostResponse;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepositoryImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepositoryImpl repository;

    public PostService(PostRepositoryImpl repository) {
        this.repository = repository;
    }


    public Map<Long, PostResponse> all() {
        return repository.all().values()
                .stream()
                .filter(v->!v.isRemoved())
                .collect(Collectors.toMap(Post::getId, PostResponse::new));
    }

    public PostResponse getById(long id) {
        Optional<Post> optionalPost = repository.getById(id);
        Post result;
        if (optionalPost.isPresent()) {
            result = optionalPost.get();
            if (result.isRemoved()) throw new NotFoundException();
            else return new PostResponse(result);
        } else {
            throw new NotFoundException();
        }
    }

    public Optional<Map<Long, PostResponse>> save(Post post) {
        long postId = post.getId();
        Map<Long, Post> posts = repository.getPosts();
        if (postId == 0) {
            long key = repository.getPostId().accumulateAndGet(1, Long::sum);
            post.setId(key);
            posts.put(key, post);
        } else if (posts.containsKey(postId) && !posts.get(postId).isRemoved()) {
            posts.put(postId, post);
        } else {
            throw new NotFoundException();
        }
        return Optional.of(all());
    }

    public boolean removeById(long id) {
        Optional<Post> optionalPost = repository.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (!post.isRemoved()) {
                post.setRemoved(true);
                repository.getPosts().put(id, post);
                return true;
            }
        }
        return false;
    }
}



