package pl.wolinski.unofficialkgcapi.heroClass.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wolinski.unofficialkgcapi.heroClass.model.HeroClass;

import java.util.Optional;

@Repository
public interface HeroClassRepository extends CrudRepository<HeroClass, Long> {
    Optional<HeroClass> findByName(String name);
}
