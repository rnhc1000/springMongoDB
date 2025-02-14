package br.dev.ferreiras.mongodb.services;

import br.dev.ferreiras.mongodb.models.dto.UserDTO;
import br.dev.ferreiras.mongodb.models.entities.User;
import br.dev.ferreiras.mongodb.repositories.UserRepository;
import br.dev.ferreiras.mongodb.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserDTO> findAll() {

    List<User> users = userRepository.findAll();

    return users.stream().map(UserDTO::new).toList();

  }

  public UserDTO findUserById(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!!!"));

    return new UserDTO(user);
  }

  public UserDTO addUser(UserDTO dto) {

    User user = new User();

    dtoToEntity(user, dto);
    user.setId(null);

    user = userRepository.insert(user);

    return new UserDTO(user);
  }


  public UserDTO updateUser(String id, UserDTO userDTO) {

    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!!!"));
    dtoToEntity(user, userDTO);
    user.setId(null);
    user = userRepository.save(user);
    return new UserDTO(user);
  }

  private void dtoToEntity(User user, UserDTO userDTO) {

    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
  }

  public UserDTO deleteUser(String id) {

    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!!!"));
    userRepository.deleteById(id);

    return new UserDTO(user);
  }
}
