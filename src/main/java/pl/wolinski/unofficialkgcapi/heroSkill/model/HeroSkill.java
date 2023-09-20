package pl.wolinski.unofficialkgcapi.heroSkill.model;

import pl.wolinski.unofficialkgcapi.hero.model.Hero;

import javax.persistence.*;

@Entity
public class HeroSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 400)
    private String description;

    private Integer startMP;

    private Integer costMP;

    private String icon;

    @OneToOne(mappedBy = "heroSkill")
    private Hero hero;

    public HeroSkill() {

    }

    public HeroSkill(Long id, String name, String description, Integer startMP, Integer costMP, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startMP = startMP;
        this.costMP = costMP;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStartMP() {
        return startMP;
    }

    public void setStartMP(Integer startMP) {
        this.startMP = startMP;
    }

    public Integer getCostMP() {
        return costMP;
    }

    public void setCostMP(Integer costMP) {
        this.costMP = costMP;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
