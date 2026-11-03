package ru.netology.service;

import org.springframework.stereotype.Component;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public ConcurrentHashMap<Long, Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Optional<ConcurrentHashMap<Long, Post>> save(Post post) {
        long postId = post.getId();
        ConcurrentHashMap<Long, Post> posts = repository.getPosts();
        if (postId == 0) {
            long key = repository.getPostId().accumulateAndGet(1, Long::sum);
            posts.put(key, new Post(key, post.getContent()));
        } else if (repository.getPosts().containsKey(postId)) {
            posts.put(postId, post);
        } else {
            System.err.println("Сервер получил некорректный запрос");
            return Optional.empty();
        }
        return Optional.of(posts);
    }

    public boolean removeById(long id) {
        return repository.removeById(id);
    }

    public byte[] getNotFoundRemoveId() {
        String path = "templates/not_found_remove_id.html";
        byte[] bytes = null;
        try {
            bytes = getClass().getClassLoader().getResourceAsStream(path).readAllBytes();
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла");
        }
        return bytes;
    }
}



