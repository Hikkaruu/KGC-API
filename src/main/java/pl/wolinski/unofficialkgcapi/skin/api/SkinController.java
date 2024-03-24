package pl.wolinski.unofficialkgcapi.skin.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.skin.manager.SkinManager;
import pl.wolinski.unofficialkgcapi.skin.model.Skin;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/skins")
@Api(tags = "Skin", description = "Skin managing")
public class SkinController {

    private SkinManager skinManager;

    @Autowired
    public SkinController(SkinManager skinManager) { this.skinManager = skinManager; }

    @GetMapping("/all")
    public Iterable<Skin> getAll() { return skinManager.findAll(); }

    @GetMapping("/index/{index}")
    public Optional<Skin> getById(@PathVariable Long index) { return skinManager.findById(index); }

    @GetMapping("/name/{name}")
    public Optional<Skin> getByName(String name) { return skinManager.findByName(name); }

    @PostMapping
    public Skin addSkin(@RequestBody Skin skin) { return skinManager.save(skin); }

    @PostMapping("/filter")
    public Iterable<Skin> filterSkins(@RequestBody Map<String, Object> filters) {
        return skinManager.filterSkins(filters);
    }
}
