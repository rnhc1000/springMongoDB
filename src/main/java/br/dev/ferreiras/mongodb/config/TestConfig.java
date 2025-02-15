package br.dev.ferreiras.mongodb.config;

import br.dev.ferreiras.mongodb.models.embedded.Author;
import br.dev.ferreiras.mongodb.models.embedded.Comment;
import br.dev.ferreiras.mongodb.models.entities.Post;
import br.dev.ferreiras.mongodb.models.entities.User;
import br.dev.ferreiras.mongodb.repositories.UserRepository;
import br.dev.ferreiras.mongodb.repositories.PostRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {

  private final UserRepository userRepository;

  private final PostRepository postRepository;

  public TestConfig(UserRepository userRepository, PostRepository postRepository) {
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  @PostConstruct
  public void init() {

    Mono<Void> deleteUsers = userRepository.deleteAll();
    deleteUsers.subscribe();

    Mono<Void> deletePosts = postRepository.deleteAll();
    deletePosts.subscribe();

    User maria = new User(null, "Maria Brown", "maria@gmail.com");
    User alex = new User(null, "Alex Green", "alex@gmail.com");
    User bob = new User(null, "Bob Grey", "bob@gmail.com");

    Flux<User> insertUsers = userRepository.saveAll(Arrays.asList(maria, alex, bob));
    insertUsers.subscribe();


    Post postOne = new Post(null, Instant.parse("2025-02-13T14:50:00Z"),
        "Business Trip", "Travel to Sampa", new Author(maria));

    Post postTwo = new Post(null, Instant.parse("2025-02-13T15:50:00Z"),
        "In the cloud", "flying", new Author(maria));

    Comment commentOne = new Comment("Have a nice and safe trip", Instant.parse("2025-02-13T14:55:00Z"),
        new Author(alex));

    Comment commentTwo = new Comment("Enjoy it!", Instant.parse("2025-02-13T14:59:00Z"),
        new Author(bob));

    Comment commentThree = new Comment("Wow...!", Instant.parse("2025-02-13T15:55:00Z"),
        new Author(alex));

    postOne.getComments().addAll(Arrays.asList(commentOne, commentTwo));
    postTwo.getComments().add(commentThree);

    Flux<Post> insertPosts = postRepository.saveAll(Arrays.asList(postOne, postTwo));
    insertPosts.subscribe();

//    maria.getPosts().addAll(Arrays.asList(postOne, postTwo));
//
//    userRepository.save(maria);





        /*
          private String id;
  private Instant moment;
  private String title;
  private String body;
  private Author author;
         */
  }
}
