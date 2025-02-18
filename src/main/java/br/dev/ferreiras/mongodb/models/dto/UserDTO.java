package br.dev.ferreiras.mongodb.models.dto;

import br.dev.ferreiras.mongodb.models.entities.User;
import org.springframework.hateoas.RepresentationModel;

public class UserDTO extends RepresentationModel<UserDTO> {
  private String id;
  private String name;
  private String email;

  public UserDTO() {
  }

  public UserDTO(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public UserDTO(User entity) {
    id = entity.getId();
    name = entity.getName();
    email = entity.getEmail();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}
