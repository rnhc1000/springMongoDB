package br.dev.ferreiras.mongodb.repositories;

import br.dev.ferreiras.mongodb.models.entities.Post;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {


  @Query("""   
      {
      'title': { $regex: ?0, $options: 'i' }
      }
      """)
  Flux<Post> searchByTitle(String text);

  @Query(
      """
      {
        $and: [
          { 'moment': { $gte: ?1 } }, { 'moment': { $lte: ?2 } }, {
            $or: [
              {'title': { $regex: ?0, $options: 'i' } },
              {'body':  { $regex: ?0, $options: 'i' } },
              {'comments.text': { $regex: ?0, $options: 'i' } }
            ]
          }
        ]
      }
      """)
  Flux<Post> fullSearch(String text, Instant startMoment, Instant endMoment);

  Flux<Post> findByTitleContainingIgnoreCase(String text);
}
