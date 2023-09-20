package pl.wolinski.unofficialkgcapi.skin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;

import javax.persistence.*;

@Entity
public class Skin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sprite;

    private String illustration;

    private String portrait;

    @Column(length = 400)
    private String description;

    private String source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hero_id", referencedColumnName = "id")
    @JsonBackReference
    private Hero hero;

    private Boolean gotPortraitAndIllustration;

    private Boolean gotExclusiveEffect;

    private Boolean gotExclusiveMotion;

    private Boolean gotExclusiveSound;

    private Boolean firstTimeOnly;

    private Integer gemCost;

    private Integer skinTokenCost;

    public Skin() {
    }

    public Skin(Long id, String name, String sprite, String illustration, String portrait, String description, String source, Hero hero,
                Boolean gotPortraitAndIllustration, Boolean gotExclusiveEffect, Boolean gotExclusiveMotion, Boolean gotExclusiveSound,
                Boolean firstTimeOnly, Integer gemCost, Integer skinTokenCost) {
        this.id = id;
        this.name = name;
        this.sprite = sprite;
        this.illustration = illustration;
        this.portrait = portrait;
        this.description = description;
        this.source = source;
        this.hero = hero;
        this.gotPortraitAndIllustration = gotPortraitAndIllustration;
        this.gotExclusiveEffect = gotExclusiveEffect;
        this.gotExclusiveMotion = gotExclusiveMotion;
        this.gotExclusiveSound = gotExclusiveSound;
        this.firstTimeOnly = firstTimeOnly;
        this.gemCost = gemCost;
        this.skinTokenCost = skinTokenCost;
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

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Boolean getGotPortraitAndIllustration() {
        return gotPortraitAndIllustration;
    }

    public void setGotPortraitAndIllustration(Boolean gotPortraitAndIllustration) {
        this.gotPortraitAndIllustration = gotPortraitAndIllustration;
    }

    public Boolean getGotExclusiveEffect() {
        return gotExclusiveEffect;
    }

    public void setGotExclusiveEffect(Boolean gotExclusiveEffect) {
        this.gotExclusiveEffect = gotExclusiveEffect;
    }

    public Boolean getGotExclusiveMotion() {
        return gotExclusiveMotion;
    }

    public void setGotExclusiveMotion(Boolean gotExclusiveMotion) {
        this.gotExclusiveMotion = gotExclusiveMotion;
    }

    public Boolean getGotExclusiveSound() {
        return gotExclusiveSound;
    }

    public void setGotExclusiveSound(Boolean gotExclusiveSound) {
        this.gotExclusiveSound = gotExclusiveSound;
    }

    public Boolean getFirstTimeOnly() {
        return firstTimeOnly;
    }

    public void setFirstTimeOnly(Boolean firstTimeOnly) {
        this.firstTimeOnly = firstTimeOnly;
    }

    public Integer getGemCost() {
        return gemCost;
    }

    public void setGemCost(Integer gemCost) {
        this.gemCost = gemCost;
    }

    public Integer getSkinTokenCost() {
        return skinTokenCost;
    }

    public void setSkinTokenCost(Integer skinTokenCost) {
        this.skinTokenCost = skinTokenCost;
    }
}
