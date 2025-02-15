package br.dev.ferreiras.mongodb.controllers;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.models.dto.UserDTO;
import br.dev.ferreiras.mongodb.services.PostService;
import br.dev.ferreiras.mongodb.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;
  private final PostService postService;

  public UserController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @GetMapping
  public Flux<UserDTO> getAllUsers() {

    return userService.findAll();
  }

  @GetMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable String id) {

    return userService
        .findUserById(id)
        .map(userDTO -> ResponseEntity.ok()
            .body(userDTO));
  }

  @PostMapping
  public Mono<ResponseEntity<UserDTO>> saveUser(@RequestBody UserDTO userDTO, UriComponentsBuilder builder) {

    return userService.addUser(userDTO)
        .map(newUser -> ResponseEntity
            .created(builder.path("/users/{id}")
                .buildAndExpand(newUser.getId())
                .toUri())
            .body(newUser)
        );
  }

  @PutMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> updateUser(@RequestBody UserDTO userDTO, @PathVariable String id, UriComponentsBuilder builder) {

    return userService.updateUser(id, userDTO)
        .map(updatedUser -> ResponseEntity
            .created(builder.path("/users/{id}")
                .buildAndExpand(updatedUser.getId())
                .toUri()
            )
            .body(updatedUser)
        );
  }

  @DeleteMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> deleteUser(@PathVariable String id) {

    return userService.deleteUser(id)
        .map(deletedUser -> ResponseEntity
            .ok()
            .body(deletedUser)
        );
  }

}
