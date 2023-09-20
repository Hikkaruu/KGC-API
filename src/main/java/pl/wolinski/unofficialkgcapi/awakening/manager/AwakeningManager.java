package pl.wolinski.unofficialkgcapi.awakening.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.awakening.model.Awakening;
import pl.wolinski.unofficialkgcapi.awakening.repository.AwakeningRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.Optional;

@Service
public class AwakeningManager {

    AwakeningRepository awakeningRepository;
    EntityManager entityManager;

    @Autowired
    public AwakeningManager(AwakeningRepository awakeningRepository, EntityManager entityManager) {
        this.awakeningRepository = awakeningRepository;
        this.entityManager = entityManager;
    }

    public Optional<Awakening> findById(Long id) { return awakeningRepository.findById(id); }

    public Iterable<Awakening> findAll() { return awakeningRepository.findAll(); }

    public Awakening save(Awakening awakening) { return awakeningRepository.save(awakening); }

    public Optional<Awakening> findByName(String name) { return awakeningRepository.findByName(name); }

    public Iterable<Awakening> filterAwakenings(Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Awakening> criteriaQuery = criteriaBuilder.createQuery(Awakening.class);
        Root<Awakening> root = criteriaQuery.from(Awakening.class);

        Predicate predicate = criteriaBuilder.conjunction();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("name".equals(key) && value instanceof String name)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), name));
            if ("description".equals(key) && value instanceof String fragment)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + fragment + "%"));
        }

        criteriaQuery.where(predicate);
        TypedQuery<Awakening> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }


}
