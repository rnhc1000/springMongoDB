package br.dev.ferreiras.mongodb.models.entities;

import br.dev.ferreiras.mongodb.models.embedded.Author;
import br.dev.ferreiras.mongodb.models.embedded.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
public class Post {

  @Id
  private String id;
  private Instant moment;
  private String title;
  private String body;
  private Author author;

  @DocumentReference(lazy = true)
  private User user;

  private List<Comment> comments = new ArrayList<>();

  public Post() {
  }

  public Post(String id, Instant moment, String title, String body, Author author) {
    this.id = id;
    this.moment = moment;
    this.title = title;
    this.body = body;
    this.author = author;
  }

  public Post(String id, Instant moment, String title, String body, Author author, User user) {
    this.id = id;
    this.moment = moment;
    this.title = title;
    this.body = body;
    this.author = author;
    this.user = user;
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

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public Post(User user) {
    this.user = user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
