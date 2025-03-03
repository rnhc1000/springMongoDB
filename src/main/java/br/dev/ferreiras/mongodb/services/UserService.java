package br.dev.ferreiras.mongodb.services;

import br.dev.ferreiras.mongodb.models.dto.UserDTO;
import br.dev.ferreiras.mongodb.models.entities.User;
import br.dev.ferreiras.mongodb.repositories.UserRepository;
import br.dev.ferreiras.mongodb.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Flux<UserDTO> findAll() {
    Flux<User> users = userRepository.findAll();

    return users.map(UserDTO::new);
  }

  public Mono<UserDTO> findUserById(String id) {

    return userRepository
        .findById(id).map(UserDTO::new)
        .switchIfEmpty(Mono.error(new ResourceNotFoundException("User not found!")));
  }

  public Mono<UserDTO> addUser(UserDTO dto) {

    User user = new User();

    dtoToEntity(user, dto);
    user.setId(null);

    Mono<User> result = userRepository.insert(user);

    return result.map(UserDTO::new);
  }


  public Mono<UserDTO> updateUser(String id, UserDTO userDTO) {


    return userRepository
        .findById(id)
               .flatMap(existingUser -> {
                 existingUser.setName(userDTO.getName());
                 existingUser.setEmail(userDTO.getEmail());
                 return userRepository.save(existingUser);
               })
               .map(UserDTO::new)
        .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException("Resource not found!!!")));

  }

  private void dtoToEntity(User user, UserDTO userDTO) {

    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
  }

  public Mono<UserDTO> deleteUser(String id) {

    Mono<User> user = userRepository.findById(id)
        .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException("User not found!!!")));
    userRepository.deleteById(id);
    return user.map(UserDTO::new);
  }
}
