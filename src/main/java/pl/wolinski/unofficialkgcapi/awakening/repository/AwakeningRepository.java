package pl.wolinski.unofficialkgcapi.awakening.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wolinski.unofficialkgcapi.awakening.model.Awakening;

import java.util.Optional;

@Repository
public interface AwakeningRepository extends CrudRepository<Awakening, Long> {
    Optional<Awakening> findByName(String name);

    Iterable<Awakening> findByDescriptionContaining(String fragment);
}
