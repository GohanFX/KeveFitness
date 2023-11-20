package hu.gohanfx.kevefitness.kevefitness.repository;

import hu.gohanfx.kevefitness.kevefitness.model.Pass;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassRepository extends MongoRepository<Pass, UUID> {

    Optional<Pass> findByName(String name);
}
