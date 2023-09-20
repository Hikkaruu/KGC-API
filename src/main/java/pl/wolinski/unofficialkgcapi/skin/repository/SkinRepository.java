package pl.wolinski.unofficialkgcapi.skin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wolinski.unofficialkgcapi.skin.model.Skin;

import java.util.Optional;

@Repository
public interface SkinRepository extends CrudRepository<Skin, Long> {
    Optional<Skin> findByName(String name);
}
