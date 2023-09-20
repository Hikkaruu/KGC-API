package pl.wolinski.unofficialkgcapi.skin.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.skin.model.Skin;
import pl.wolinski.unofficialkgcapi.skin.repository.SkinRepository;
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
public class SkinManager {

    private SkinRepository skinRepository;

    private EntityManager entityManager;

    private EntityUtils entityUtils;

    @Autowired
    public SkinManager(SkinRepository skinRepository, EntityManager entityManager, EntityUtils entityUtils) {
        this.skinRepository = skinRepository;
        this.entityManager = entityManager;
        this.entityUtils = entityUtils;
    }

    public Optional<Skin> findById(Long id) { return skinRepository.findById(id); }

    public Iterable<Skin> findAll() { return skinRepository.findAll(); }

    public Skin save(Skin skin) { return skinRepository.save(skin); }

    public Optional<Skin> findByName(String name) { return skinRepository.findByName(name); }

    public Iterable<Skin> filterSkins(Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Skin> criteriaQuery = criteriaBuilder.createQuery(Skin.class);
        Root<Skin> root = criteriaQuery.from(Skin.class);

        Predicate predicate = criteriaBuilder.conjunction();

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if ("name".equals(key) && value instanceof String name)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), name));
            if ("description".equals(key) && value instanceof String fragment)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + fragment + "%"));
            if ("source".equals(key) && value instanceof String source)
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("source"), source));
            if ("gotPortraitAndIllustration".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("gotExclusiveEffect".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("gotExclusiveMotion".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("gotExcnlusiveSound".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("firstTimeOnly".equals(key))
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(key), value));
            if ("gemCost".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "gemCost", value);
            if ("skinTokenCost".equals(key))
                predicate = entityUtils.greaterLessEqualFiltering(criteriaBuilder, root, predicate, "skinTokenCost", value);
        }

        criteriaQuery.where(predicate);
        TypedQuery<Skin> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
