package pl.wolinski.unofficialkgcapi.region.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wolinski.unofficialkgcapi.region.model.Region;

import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<Region, Long> {

    Optional<Region> findByName(String name);

}
