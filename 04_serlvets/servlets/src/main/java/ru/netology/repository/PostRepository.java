package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private AtomicLong postId = new AtomicLong(0);

    public ConcurrentHashMap<Long, Post> getPosts() {
        return posts;
    }

    public AtomicLong getPostId() {
        return postId;
    }

    public ConcurrentHashMap<Long, Post> all() {
        return getPosts();
    }

    public Optional<Post> getById(long id) {
        return Optional.empty();
    }

    public Post save(Post post) {
        return post;
    }

    public void removeById(long id) {
    }
}
