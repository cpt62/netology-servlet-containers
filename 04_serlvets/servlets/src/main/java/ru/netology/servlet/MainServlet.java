package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext("ru.netology");
    final var service = context.getBean(PostService.class);
    final var repository = context.getBean("repositoryId");
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing

      if (method.equals("GET") && path.equals("/api/posts")) {
        controller.all(resp);
        return;
      }


      if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
        // easy way
        String postId = path.substring(path.lastIndexOf("/")).replace("/", "");
        final var id = Long.parseLong(postId);
        controller.getById(id, resp);
        return;
      }

      if (method.equals("POST") && path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }

      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
        // easy way
        String postId = path.substring(path.lastIndexOf("/")).replace("/", "");
        final var id = Long.parseLong(postId);
        controller.removeById(id, resp);
      }

    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}