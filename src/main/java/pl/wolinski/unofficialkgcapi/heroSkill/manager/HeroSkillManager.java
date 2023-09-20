package pl.wolinski.unofficialkgcapi.heroSkill.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.heroSkill.model.HeroSkill;
import pl.wolinski.unofficialkgcapi.heroSkill.repository.HeroSkillRepository;
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
public class HeroSkillManager {

    private HeroSkillRepository heroSkillRepository;

    private EntityManager entityManager;

    private EntityUtils entityUtils;

    @Autowired
    public HeroSkillManager(HeroSkillRepository heroSkillRepository, EntityManager entityManager, EntityUtils entityUtils) {
        this.heroSkillRepository = heroSkillRepository;
        this.entityManager = entityManager;
        this.entityUtils = entityUtils;
    }

    public Optional<HeroSkill> findById(Long id) { return heroSkillRepository.findById(id); }

    public Iterable<HeroSkill> findAll() { return heroSkillRepository.findAll(); }

    public HeroSkill save(HeroSkill heroSkill) { return heroSkillRepository.save(heroSkill); }

    public Optional<HeroSkill> findByName(String name) { return heroSkillRepository.findByName(name); }

    public Iterable<HeroSkill> filterHeroSkills(Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HeroSkill> criteriaQuery = criteriaBuilder.createQuery(HeroSkill.class);
        Root<HeroSkill> root = criteriaQuery.from(HeroSkill.class);

        Predicate predicate = criteriaBuilder.conjunction();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("name".equals(key) && value instanceof String name)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), name));
            if ("description".equals(key) && value instanceof String fragment)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + fragment + "%"));
            if ("startMP".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "startMP", value);
            if ("costMP".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "costMP", value);

        }

        criteriaQuery.where(predicate);
        TypedQuery<HeroSkill> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
