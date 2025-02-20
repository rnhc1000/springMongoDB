package br.dev.ferreiras.mongodb.services;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.models.entities.Post;
import br.dev.ferreiras.mongodb.repositories.PostRepository;
import br.dev.ferreiras.mongodb.repositories.UserRepository;
import br.dev.ferreiras.mongodb.services.exceptions.ResourceNotFoundException;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;


@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public Flux<PostDTO> getAllPosts() {
    Flux<Post> posts = postRepository.findAll();

    return posts.map(PostDTO::new);
  }

  public Mono<HalResourceWrapper<PostDTO, Void>> findPostById(String id) {
    Mono<Post> entity = postRepository.findById(id)
        .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException("Post not found")));

    Mono<PostDTO> postById = entity.map(PostDTO::new);

    return postById.map(post -> HalResourceWrapper.wrap(post)
        .withLinks(de.kamillionlabs.hateoflux.model.link.Link.of("http://127.0.0.1:8095/posts/{id}1")
            .expand(id)
            .withRel("self")));
  }

  public Flux<PostDTO> findByTitle(String text) {
    Flux<Post> posts = postRepository.searchByTitle(text);

    return posts.map(PostDTO::new);
  }

  public Flux<PostDTO> fullSearch(String text, String start, String end) {

    Instant startMoment = convertMoment(start);
    Instant endMoment = convertMoment(end);

    Flux<Post> posts = postRepository.fullSearch(text, startMoment, endMoment);

    return posts.map(PostDTO::new);
  }

  private Instant convertMoment(String moment) {

    return Instant.parse(moment);
  }

}

