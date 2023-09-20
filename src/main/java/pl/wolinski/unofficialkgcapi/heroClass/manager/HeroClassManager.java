package pl.wolinski.unofficialkgcapi.heroClass.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.heroClass.model.HeroClass;
import pl.wolinski.unofficialkgcapi.heroClass.repository.HeroClassRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

@Service
public class HeroClassManager {

    HeroClassRepository heroClassRepository;

    @Autowired
    HeroClassManager(HeroClassRepository heroClassRepository) { this.heroClassRepository = heroClassRepository; }

    public Optional<HeroClass> findById(Long id) { return heroClassRepository.findById(id); }

    public Iterable<HeroClass> findAll() { return  heroClassRepository.findAll(); }

    public HeroClass save(HeroClass heroClass) { return heroClassRepository.save(heroClass); }

    public Optional<HeroClass> findByName(String name) { return heroClassRepository.findByName(name); }



}
