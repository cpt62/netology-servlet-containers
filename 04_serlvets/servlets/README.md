# Решение домашнего задания к занятию «2.3. Spring Web MVC»

## Ход рефакторинга (ветка featureMigrationSpringMVC)
- Редактирование `POM.xml`, а именно:
    - Добавление зависимостей:
        - `spring-webmvc`;
        - `tomcat-embed-core`;
- Добавляем точку входа в программу, инициализируем и настраиваем `Tomcat`;
- Добавлен кастомный класс `MyWebApplicationInitializer`, реализующий `WebApplicationInitializer`. Метод `onStartup
  (ServletContext servletContext)` инициализирует `Spring Container` (в т.ч. сканируется base package на наличие бинов),
  `DispatcherServlet` и строится связь `Spring` и `tomcat`.
- Создан интерфейс `PostRepository` и его реализация - `PostRepositoryImpl` для возможности мокирования;
- Удалил директорию `servlet` проект не нуждается в реализации на чистых сервлетах;
- Добавлены аннотации `@Repository, @Service, @Controller` соответствующим классам проекта;
- Добавлены аннотации к методам класса контроллера. В даноме примере:
    - `@RequestMapping`
    - `@GetMapping`
    - `@DeleteMapping`
    - `@PostMapping`