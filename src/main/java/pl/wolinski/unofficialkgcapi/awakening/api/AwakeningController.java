package pl.wolinski.unofficialkgcapi.awakening.api;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wolinski.unofficialkgcapi.awakening.manager.AwakeningManager;
import pl.wolinski.unofficialkgcapi.awakening.model.Awakening;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/awakenings")
@Api(tags = "Awakening", description = "Awakening managing")
public class AwakeningController {

    private AwakeningManager awakeningManager;

    @Autowired
    public AwakeningController(AwakeningManager awakeningManager) { this.awakeningManager = awakeningManager; }

    @ApiOperation(value = "Get all awakenings")
    @GetMapping("/all")
    public Iterable<Awakening> getAll() { return awakeningManager.findAll(); }

    @ApiOperation(value = "Find awakening by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the awakening"),
            @ApiResponse(code = 404, message = "Awakening not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/index/{index}")
    public Optional<Awakening> getById(@ApiParam(value = "unique id of awakening", example = "1") @PathVariable Long index) {
        return awakeningManager.findById(index);
    }

    @ApiOperation(value = "Find awakenings by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved awakenings"),
            @ApiResponse(code = 404, message = "Awakenings not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/name/{name}")
    public Optional<Awakening> getByName(@ApiParam(value = "awakening name", example = "Absolute Will") @PathVariable String name) {
        return awakeningManager.findByName(name); }

    @PostMapping("/add")
    @ApiOperation(value = "Add awakening")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Awakening added successfully"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Awakening addAwakening(
            @ApiParam(name = "awakening body", value = "Add awakening using JSON body\nto add id of hero (for 1:N relation) who posses this awakening" +
                    ": add \"hero\": { \"id\": 0 }", example = "{ \"x\": 1 }")
            @RequestBody Awakening awakening) {
        return awakeningManager.save(awakening);
    }


    @PostMapping("/filter")
    @ApiOperation(
            value = "Find awakenings based on any of field (one or multiple)",
            notes = "Filters awakenings based on the provided JSON object with filter criteria.\n " +
                    "Available formats for filter values:\n" +
                    "a string (whole or part of text),\nan integer or a string containing \none of the following prefixes: " +
                    ">=, >, <=, < followed by an integer."
    )
    public Iterable<Awakening> filterAwakenings(
            @ApiParam(name = "filter", value = "JSON object with filter criteria.",
            example = "{\n" +
                    "    \"description\": \"Obtains\"\n" +
                    "}")
            @RequestBody Map<String, Object> filters) {
        return awakeningManager.filterAwakenings(filters);
    }
}
