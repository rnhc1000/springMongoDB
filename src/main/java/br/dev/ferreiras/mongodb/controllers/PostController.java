package br.dev.ferreiras.mongodb.controllers;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.services.PostService;
import de.kamillionlabs.hateoflux.http.HalResourceResponse;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import de.kamillionlabs.hateoflux.model.link.Link;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Posts", description = "Controller for Posts")
@RestController
@RequestMapping(value = "/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @Operation(
      summary = "List posts",
      description = "List posts of all users",
      responses = {
          @ApiResponse(responseCode = "200", description = "List of Posts"),
          @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Access Denied!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)
      }
  )
  @GetMapping
  public Flux<ResponseEntity<PostDTO>> getAllPosts() {

    return postService
        .getAllPosts()
        .map(posts -> ResponseEntity
            .ok()
            .body(posts));
  }

  @Operation(
      summary = "List posts by User id",
      description = "List posts by User id",
      responses = {
          @ApiResponse(responseCode = "200", description = "List of Posts"),
          @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Access Denied!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)
      }
  )
  @SecurityRequirement(name = "bearerAuth")
  @GetMapping(value = "/{id}")
  public HalResourceResponse<PostDTO, Void> getPostById(@PathVariable String id) {

    Mono<PostDTO> postById = postService.findPostById(id);
    var finalPost = postById.map(post -> HalResourceWrapper.wrap(post)
        .withLinks(Link.of("/posts/{id}")
                .expand(id)
                .withRel("self"),
            Link.linkAsSelfOf("posts/" + id)));

    return HalResourceResponse.ok(finalPost).withContentType(MediaType.APPLICATION_JSON_VALUE)
        .withHeader("Custom-header", "value");
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
