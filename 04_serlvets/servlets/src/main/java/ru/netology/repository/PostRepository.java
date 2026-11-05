package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface PostRepository {
    public ConcurrentHashMap<Long, Post> all();

    public Optional<Post> getById(long id);

    public Post save(Post post);

    public boolean removeById(long id);
}
