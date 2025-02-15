package br.dev.ferreiras.mongodb.controllers;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.services.PostService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public Flux<ResponseEntity<PostDTO>> getAllPosts() {

    return postService
        .getAllPosts()
        .map(posts -> ResponseEntity
            .ok()
            .body(posts));
  }

  @GetMapping(value = "/{id}")
  public Mono<ResponseEntity<PostDTO>> getPostById(@PathVariable String id) {

    return postService.findPostById(id)
        .map(post -> ResponseEntity
            .ok()
            .body(post));
  }

  @GetMapping(value = "/titlesearch")
  public Flux<ResponseEntity<PostDTO>> getPostByTitle(@RequestParam(value = "text", defaultValue = "") String text) {

    return postService.findByTitle(text)
        .map(post -> ResponseEntity
            .ok()
            .body(post));
  }

  @GetMapping(value = "/fullsearch")
  public Flux<ResponseEntity<PostDTO>> fullPostSearch(
      @RequestParam(value = "text", defaultValue = "") String text,
      @RequestParam(value = "start", defaultValue = "1970-01-01T00:00:00Z") String start,
      @RequestParam(value = "end", defaultValue = "2025-12-31T23:59:59Z") String end
  ) {

    return postService.fullSearch(text, start, end)
        .map(posts -> ResponseEntity
            .ok()
            .body(posts)
        );

  }
}
