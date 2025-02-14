package br.dev.ferreiras.mongodb.services;

import br.dev.ferreiras.mongodb.models.dto.PostDTO;
import br.dev.ferreiras.mongodb.models.entities.Post;
import br.dev.ferreiras.mongodb.models.entities.User;
import br.dev.ferreiras.mongodb.repositories.PostRepository;
import br.dev.ferreiras.mongodb.repositories.UserRepository;
import br.dev.ferreiras.mongodb.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public List<PostDTO> getAllPosts(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!!!"));

    return user.getPosts().stream().map(PostDTO::new).toList();
  }

  public PostDTO findPostById(String id) {
    Post entity = getEntityById(id);

    return new PostDTO(entity);
  }

  public List<PostDTO> findByTitle(String text) {
    List<Post> posts = postRepository.searchByTitle(text);

    return posts.stream().map(PostDTO::new).toList();
  }

  public List<PostDTO> fullSearch(String text, String start, String end) {

    Instant startMoment = convertMoment(start);
    Instant endMoment = convertMoment(end);

    List<Post> posts = postRepository.fullSearch(text, startMoment, endMoment);

    return posts.stream().map(PostDTO::new).toList();
  }

  private Instant convertMoment(String moment) {

    return Instant.parse(moment);
  }

  private Post getEntityById(String id) {
    Optional<Post> result = postRepository.findById(id);

    return result.orElseThrow(() -> new ResourceNotFoundException("Post not found!!!"));
  }


}

