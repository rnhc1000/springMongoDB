package br.dev.ferreiras.mongodb.repositories;

import br.dev.ferreiras.mongodb.models.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
