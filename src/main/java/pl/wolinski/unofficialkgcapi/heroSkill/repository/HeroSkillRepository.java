package pl.wolinski.unofficialkgcapi.heroSkill.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wolinski.unofficialkgcapi.heroSkill.model.HeroSkill;

import java.util.Optional;

@Repository
public interface HeroSkillRepository extends CrudRepository<HeroSkill, Long> {
    Optional<HeroSkill> findByName(String name);
}
