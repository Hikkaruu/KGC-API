package pl.wolinski.unofficialkgcapi.heroClass.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.heroClass.manager.HeroClassManager;
import pl.wolinski.unofficialkgcapi.heroClass.model.HeroClass;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroClasses")
@Api(tags = "Hero Class", description = "Hero Class managing")
public class HeroClassController {

    private HeroClassManager heroClassManager;

    @Autowired
    public HeroClassController(HeroClassManager heroClassManager) { this.heroClassManager = heroClassManager; }

    @GetMapping("/all")
    public Iterable<HeroClass> getAll() { return heroClassManager.findAll(); }

    @GetMapping("/index/{index}")
    public Optional<HeroClass> getById(@PathVariable Long index) { return heroClassManager.findById(index); }

    @GetMapping("/name/{name}")
    public Optional<HeroClass> getByName(@PathVariable String name) { return heroClassManager.findByName(name); }

    @PostMapping
    public HeroClass addHeroClass(@RequestBody HeroClass heroClass) { return heroClassManager.save(heroClass); }

}
