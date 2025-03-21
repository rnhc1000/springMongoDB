package br.dev.ferreiras.mongodb.services;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.models.entities.Post;
import br.dev.ferreiras.mongodb.repositories.PostRepository;
import br.dev.ferreiras.mongodb.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;


@Service
public class PostService {

  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Flux<PostDTO> getAllPosts() {
    Flux<Post> posts = postRepository.findAll();

    return posts.map(PostDTO::new);
  }

  public Mono<PostDTO> findPostById(String id) {
    Mono<Post> entity = postRepository.findById(id)
        .switchIfEmpty(Mono.error(() -> new ResourceNotFoundException("Post not found")));

    return  entity.map(PostDTO::new);
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

