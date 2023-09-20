package pl.wolinski.unofficialkgcapi.heroSkill.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.heroSkill.manager.HeroSkillManager;
import pl.wolinski.unofficialkgcapi.heroSkill.model.HeroSkill;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroSkills")
public class HeroSkillController {

    private HeroSkillManager heroSkillManager;

    @Autowired
    public HeroSkillController(HeroSkillManager heroSkillManager) { this.heroSkillManager = heroSkillManager; }

    @GetMapping("/all")
    public Iterable<HeroSkill> getAll() { return heroSkillManager.findAll(); }

    @GetMapping("/index/{index}")
    public Optional<HeroSkill> getById(@PathVariable Long index) { return heroSkillManager.findById(index); }

    @GetMapping("/name/{name}")
    public Optional<HeroSkill> getByName(@PathVariable String name) { return heroSkillManager.findByName(name); }

    @PostMapping
    public HeroSkill addHeroSkill(@RequestBody HeroSkill heroSkill) { return heroSkillManager.save(heroSkill); }

    @PostMapping("/filter")
    public Iterable<HeroSkill> filterHeroes(@RequestBody Map<String, Object> filters) {
        return heroSkillManager.filterHeroSkills(filters);
    }



}
