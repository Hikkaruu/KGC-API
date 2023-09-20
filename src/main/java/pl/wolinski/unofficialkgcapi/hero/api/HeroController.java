package pl.wolinski.unofficialkgcapi.hero.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;
import pl.wolinski.unofficialkgcapi.hero.manager.HeroManager;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    private HeroManager heroManager;

    @Autowired
    public HeroController(HeroManager heroManager) { this.heroManager = heroManager; }

    @GetMapping("/all")
    public Iterable<Hero> getAll() { return heroManager.findAll(); }

    @GetMapping("/index/{index}")
    public Optional<Hero> getById(@PathVariable Long index) { return heroManager.findById(index); }

    @GetMapping("/name/{name}")
    public Optional<Hero> getByName(@PathVariable String name) { return heroManager.findByName(name); }

    @PostMapping
    public Hero addHero(@RequestBody Hero hero) { return heroManager.save(hero); }

    @PostMapping("/filter")
    public Iterable<Hero> filterHeroes(@RequestBody Map<String, Object> filters) {
        return heroManager.filterHeroes(filters);
    }

//    @PostMapping("/filter")
//    public Iterable<Hero> filterHeroes(Map<String, Hero> filters) {
//        CriteriaBuilder criteriaBuilder = heroManager.getCriteriaBuilder;
//    }

//    @PostMapping("/filter")
//    public Iterable<Hero> filterHeroesByBackup(@RequestBody Map<String, Boolean> filter) {
//        Boolean backupHeroValue = filter.get("backupHero");
//        if (backupHeroValue == null) throw new IllegalArgumentException("backupHero is null!");
//        Iterable<Hero> filteredHeroes = heroManager.findByBackupHero(backupHeroValue);
//        return filteredHeroes;
//    }

}
