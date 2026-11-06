package ru.netology.controller.dto;

import ru.netology.model.Post;

public class PostResponse {

    private final long id;
    private final String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

