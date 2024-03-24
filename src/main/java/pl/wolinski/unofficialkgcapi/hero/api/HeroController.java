package pl.wolinski.unofficialkgcapi.hero.api;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;
import pl.wolinski.unofficialkgcapi.hero.manager.HeroManager;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroes")
@Api(tags = "Hero", description = "Hero managing")
public class HeroController {

    private HeroManager heroManager;

    @Autowired
    public HeroController(HeroManager heroManager) { this.heroManager = heroManager; }

    @ApiOperation(value = "Get all Heroes")
    @GetMapping("/all")
    public Iterable<Hero> getAll() { return heroManager.findAll(); }

    @ApiOperation(value = "Find heroes by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the hero"),
            @ApiResponse(code = 404, message = "Hero not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/index/{index}")
    public Optional<Hero> getById(@ApiParam(value = "unique hero id", example = "1") @PathVariable Long index) {
        return heroManager.findById(index); }

    @ApiOperation(value = "Find heroes by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved heroes"),
            @ApiResponse(code = 404, message = "Heroes not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/name/{name}")
    public Optional<Hero> getByName(@ApiParam(value = "hero name", example = "Shelda") @PathVariable String name) {
        return heroManager.findByName(name); }

    @ApiOperation(value = "Add hero")
    @PostMapping
    public Hero addHero(@ApiParam(value = "Add hero using JSON body") @RequestBody Hero hero) {
        return heroManager.save(hero); }

    @PostMapping("/filter")
    @ApiOperation(
            value = "Find heroes based on any of field (one or multiple)",
            notes = "Filters heroes based on the provided JSON object with filter criteria.\n " +
                    "Available formats for filter values:\n" +
                    "a string (whole or part of text),\nan integer or a string containing \none of the following prefixes: " +
                    ">=, >, <=, < followed by an integer."
    )
    public Iterable<Hero> filterHeroes(
            @ApiParam(name = "filter", value = "JSON object with filter criteria.",
                    example = "{\n" +
                    "    \"maxLvlHP\": \"<=800\",\n" +
                    "    \"range\": \">2\",\n" +
                    "    \"movementSpeed\": 150\n" +
                    "}")
            @RequestBody Map<String, Object> filters) {
        return heroManager.filterHeroes(filters);
    }








}
