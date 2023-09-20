package pl.wolinski.unofficialkgcapi.awakening.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.awakening.manager.AwakeningManager;
import pl.wolinski.unofficialkgcapi.awakening.model.Awakening;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/awakenings")
public class AwakeningController {

    private AwakeningManager awakeningManager;

    @Autowired
    public AwakeningController(AwakeningManager awakeningManager) { this.awakeningManager = awakeningManager; }

    @ApiOperation(value = "Get all awakenings")
    @GetMapping("/all")
    public Iterable<Awakening> getAll() { return awakeningManager.findAll(); }

    @ApiOperation(value = "Get awakening by id")
    @GetMapping("/index/{index}")
    public Optional<Awakening> getById(@ApiParam(value = "unique id of awakening", example = "123") @PathVariable Long index) {
        return awakeningManager.findById(index);
    }

    @GetMapping("/name/{name}")
    public Optional<Awakening> getByName(@PathVariable String name) { return awakeningManager.findByName(name); }

    @PostMapping("/add")
    public Awakening addAwakening(@RequestBody Awakening awakening) { return awakeningManager.save(awakening); }

    @PostMapping("/filter")
    public Iterable<Awakening> filterAwakenings(@RequestBody Map<String, Object> filters) {
        return awakeningManager.filterAwakenings(filters);
    }
}
