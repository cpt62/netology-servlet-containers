package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }

    public byte[] getNotFoundTemplate() {
        String path = "templates/not_found.html";
        byte[] bytes = null;
        try {
            bytes = getClass().getClassLoader().getResourceAsStream(path).readAllBytes();
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла");
        }
        return bytes;

    }
}



