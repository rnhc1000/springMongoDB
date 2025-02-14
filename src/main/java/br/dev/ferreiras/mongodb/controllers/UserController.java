package br.dev.ferreiras.mongodb.controllers;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.models.dto.UserDTO;
import br.dev.ferreiras.mongodb.models.entities.Post;
import br.dev.ferreiras.mongodb.services.PostService;
import br.dev.ferreiras.mongodb.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
  public ResponseEntity<List<UserDTO>> getALlUsers() {

    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {

    return ResponseEntity.ok(userService.findUserById(id));
  }

  @PostMapping
  public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
    userDTO = userService.addUser(userDTO);
    URI uri = ServletUriComponentsBuilder
        .fromCurrentRequestUri()
        .path("/{id}")
        .buildAndExpand(userDTO.getId())
        .toUri();

    return ResponseEntity.created(uri).body(userDTO);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable String id) {

    userDTO = userService.updateUser(id, userDTO);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
        .path("/{id}")
        .buildAndExpand(userDTO.getId())
        .toUri();

    return ResponseEntity.created(uri).body(userDTO);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<UserDTO> deleteUser(@PathVariable String id) {

    return ResponseEntity.ok(userService.deleteUser(id));
  }

  @GetMapping(value = "/posts/{id}")
  public ResponseEntity<List<PostDTO>> getPosts(@PathVariable String id) {

    return ResponseEntity.ok(postService.getAllPosts(id));
  }
}
