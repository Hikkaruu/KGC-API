package pl.wolinski.unofficialkgcapi.hero.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;
import pl.wolinski.unofficialkgcapi.hero.repository.HeroRepository;
import pl.wolinski.unofficialkgcapi.util.EntityUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.Optional;

@Service
public class HeroManager {

    private HeroRepository heroRepository;
    private EntityManager entityManager;

    private EntityUtils entityUtils;

    @Autowired
    public HeroManager(HeroRepository heroRepository, EntityManager entityManager, EntityUtils entityUtils) {
        this.heroRepository = heroRepository;
        this.entityManager = entityManager;
        this.entityUtils = entityUtils;
    }

    public Optional<Hero> findById(Long id) { return  heroRepository.findById(id); }

    public Iterable<Hero> findAll() { return heroRepository.findAll(); }

    public Optional<Hero> findByName(String name) { return heroRepository.findByName(name); }

    public Hero save(Hero hero) { return heroRepository.save(hero); }

//    public Iterable<Hero> findByBackupHero(Boolean backupHero) {
//        return heroRepository.findByBackupHero(backupHero);
//    }

    public Iterable<Hero> filterHeroes(Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hero> criteriaQuery = criteriaBuilder.createQuery(Hero.class);
        Root<Hero> root = criteriaQuery.from(Hero.class);

        Predicate predicate = criteriaBuilder.conjunction();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("backupHero".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("special".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("range".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "range", value);
            if ("baseHP".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "baseHP", value);
            if ("baseSpellPower".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "baseSpellPower", value);
            if ("baseAttack".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "baseAttack", value);
            if ("baseAttackSpeed".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "baseAttackSpeed", value);
            if ("movementSpeed".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "movementSpeed", value);
            if ("maxLvlHP".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "maxLvlHP", value);
            if ("maxLvlSpellPower".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "maxLvlSpellPower", value);
            if ("maxLvlAttack".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "maxLvlAttack", value);
            if ("potentialAbility4".equals(key) && value instanceof String fragment)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("potentialAbility4"), "%" + fragment + "%"));
            if ("potentialAbility8".equals(key) && value instanceof String fragment)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("potentialAbility8"), "%" + fragment + "%"));

        }

        criteriaQuery.where(predicate);
        TypedQuery<Hero> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }






}
