package br.dev.ferreiras.mongodb.controllers;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<PostDTO> getPostById(@PathVariable String id) {

    return ResponseEntity.ok(postService.findPostById(id));
  }

  @GetMapping(value = "/titlesearch")
  public ResponseEntity<List<PostDTO>> getPostByTitle(@RequestParam(value = "text", defaultValue = "") String text) {

    return ResponseEntity.ok(postService.findByTitle(text));
  }

  @GetMapping(value = "/fullsearch")
  public ResponseEntity<List<PostDTO>> fullPostSearch(
      @RequestParam(value = "text", defaultValue = "") String text,
      @RequestParam(value = "start", defaultValue = "1970-01-01T00:00:00Z") String start,
      @RequestParam(value = "end", defaultValue = "2025-12-31T23:59:59Z") String end
  ) {

    List<PostDTO> posts = postService.fullSearch(text, start, end);

    return ResponseEntity.ok().body(posts);
  }
}
