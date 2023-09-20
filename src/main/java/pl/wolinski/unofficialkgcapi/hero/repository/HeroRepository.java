package pl.wolinski.unofficialkgcapi.hero.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;

import java.util.Optional;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Long> {
    Optional<Hero> findByName(String name);
    Iterable<Hero> findByBackupHero(Boolean backupHero);
}
