package br.dev.ferreiras.mongodb.models.dto;

import br.dev.ferreiras.mongodb.models.embedded.Author;
import br.dev.ferreiras.mongodb.models.embedded.Comment;
import br.dev.ferreiras.mongodb.models.entities.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostDTO extends RepresentationModel<PostDTO>{

  @Schema(description = "Database generated post id")
  private String id;
  private Instant moment;

  @Schema(description = "Post Title")
  private String title;
  private String body;

  @Schema(description = "Post Author")
  private Author author;

  private List<Comment> comments = new ArrayList<>();

  public PostDTO() {
  }

  public PostDTO(String id, Instant moment, String title, String body, Author author, List<Comment> comments) {
    this.id = id;
    this.moment = moment;
    this.title = title;
    this.body = body;
    this.author = author;
    this.comments = comments;
  }

  public PostDTO(Post entity) {
    id = entity.getId();
    moment = entity.getMoment();
    title = entity.getTitle();
    body = entity.getBody();
    author = entity.getAuthor();
    comments.addAll(entity.getComments());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Instant getMoment() {
    return moment;
  }

  public void setMoment(Instant moment) {
    this.moment = moment;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public List<Comment> getComments() {
    return comments;
  }

}
