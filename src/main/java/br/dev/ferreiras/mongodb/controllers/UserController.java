package br.dev.ferreiras.mongodb.controllers;

import br.dev.ferreiras.mongodb.models.dto.UserDTO;
import br.dev.ferreiras.mongodb.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Users", description = "Controller for Users")
@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(
      summary = "List users",
      description = "List all registered users",
      responses = {
          @ApiResponse(responseCode = "200", description = "List of Registered Users"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
      }
      )
  @GetMapping
  public Flux<UserDTO> getAllUsers() {

    return userService.findAll();
  }

  @Operation(
      summary = "List user",
      description = "List user given his/her id",
      responses = {
          @ApiResponse(responseCode = "200", description = "User returned by his/her id"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
      }
  )
  @GetMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable String id) {

    return userService
        .findUserById(id)
        .map(userDTO -> ResponseEntity.ok()
            .body(userDTO));
  }

  @Operation(
      summary = "Add user",
      description = "Add user, given his/her name and email",
      responses = {
          @ApiResponse(responseCode = "201", description = "User created"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
      }
  )
  @PostMapping(produces = "application/json")
  public Mono<ResponseEntity<UserDTO>> saveUser(@RequestBody UserDTO userDTO, UriComponentsBuilder builder) {

    return userService.addUser(userDTO)
        .map(newUser -> ResponseEntity
            .created(builder.path("/users/{id}")
                .buildAndExpand(newUser.getId())
                .toUri())
            .body(newUser)
        );
  }

  @Operation(
      summary = "Update user",
      description = "Update user given his/her id",
      responses = {
          @ApiResponse(responseCode = "200", description = "User returned by his/her id"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden!", content = @Content),
          @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
      }
  )
  @PutMapping(value = "/{id}", produces = "application/json")
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
  @Operation(
      summary = "Delete user",
      description = "Delete user given his/her id",
      responses = {
          @ApiResponse(responseCode = "204", description = "User deleted"),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden!", content = @Content),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      }
  )
  @DeleteMapping(value = "/{id}")
  public Mono<ResponseEntity<UserDTO>> deleteUser(@PathVariable String id) {

    return userService.deleteUser(id)
        .map(deletedUser -> ResponseEntity
            .ok()
            .body(deletedUser)
        );
  }

}
