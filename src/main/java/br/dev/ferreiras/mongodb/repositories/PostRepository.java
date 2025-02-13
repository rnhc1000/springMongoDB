package br.dev.ferreiras.mongodb.repositories;

import br.dev.ferreiras.mongodb.models.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
}
