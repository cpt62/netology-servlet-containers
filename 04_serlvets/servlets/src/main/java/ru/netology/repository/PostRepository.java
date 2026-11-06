package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Map;
import java.util.Optional;

public interface PostRepository {
    public Map<Long, Post> all();

    public Optional<Post> getById(long id);

    public Post save(Post post);
}
