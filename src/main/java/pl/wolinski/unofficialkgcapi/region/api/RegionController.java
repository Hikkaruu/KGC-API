package pl.wolinski.unofficialkgcapi.region.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.region.model.Region;
import pl.wolinski.unofficialkgcapi.region.manager.RegionManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
@Api(tags = "Region", description = "Region managing")
public class RegionController {

    private RegionManager regionManager;

    @Autowired
    public RegionController(RegionManager regionManager) { this.regionManager = regionManager; }

    @GetMapping("/all")
    public Iterable<Region> getAll() { return regionManager.findAll(); }

    @GetMapping("/index/{index}")
    public Optional<Region> getById(@PathVariable Long index) { return regionManager.findById(index); }

    @GetMapping("/name/{name}")
    public Optional<Region> getByName(@PathVariable String name) { return  regionManager.findByName(name); }

    @PostMapping
    public Region addRegion(@RequestBody Region region) { return regionManager.save(region); }

}
