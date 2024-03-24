package pl.wolinski.unofficialkgcapi.awakening.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;

import javax.persistence.*;

@Entity
public class Awakening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(length = 400)
    private String description;

    private String icon;

    @ManyToOne
    @JoinColumn(name = "hero_id", referencedColumnName = "id")
    @JsonBackReference
    @ApiModelProperty(hidden = true)
    private Hero hero;

    public Awakening() {
    }

    public Awakening(Long id, String name, String icon, String description, Hero hero) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.hero = hero;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public Hero getHero() {
        return hero;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
