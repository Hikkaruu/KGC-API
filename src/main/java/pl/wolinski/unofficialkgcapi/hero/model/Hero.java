package pl.wolinski.unofficialkgcapi.hero.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.wolinski.unofficialkgcapi.awakening.model.Awakening;
import pl.wolinski.unofficialkgcapi.heroClass.model.HeroClass;
import pl.wolinski.unofficialkgcapi.heroSkill.model.HeroSkill;
import pl.wolinski.unofficialkgcapi.region.model.Region;
import pl.wolinski.unofficialkgcapi.skin.model.Skin;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    @JsonIgnore
    private Region region;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hero_class_id", referencedColumnName = "id")
    @JsonIgnore
    private HeroClass heroClass;

    private Integer baseHP;

    private Integer baseSpellPower;

    private Integer baseAttack;

    private Integer baseAttackSpeed;

    private Integer range;

    private Integer movementSpeed;

    private Integer maxLvlHP;

    private Integer maxLvlSpellPower;

    private Integer maxLvlAttack;
    @Column(length = 400)
    private String potentialAbility4;
    @Column(length = 400)
    private String potentialAbility8;

    private String sprite;

    private String avatar;

    private String illustration;

    @OneToMany(mappedBy = "hero")
    private Set<Awakening> awakenings = new HashSet<>();

    @OneToMany(mappedBy = "hero")
    private Set<Skin> skins = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "hero_skill_id", referencedColumnName = "id")
    private HeroSkill heroSkill;

    private LocalDate releaseDate;

    private Boolean backupHero;

    private Boolean special;

    private Integer attackCount;

    public Hero(Long id, String name, String title, Region region, HeroClass heroClass, Integer baseHP, Integer baseSpellPower,
                Integer baseAttack, Integer baseAttackSpeed, Integer range, Integer movementSpeed, Integer maxLvlHP, Integer maxLvlSpellPower,
                Integer maxLvlAttack, String potentialAbility4, String potentialAbility8, String sprite, String avatar, String illustration,
                HeroSkill heroSkill, LocalDate releaseDate, Integer attackCount, Boolean backupHero, Boolean special) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.region = region;
        this.heroClass = heroClass;
        this.baseHP = baseHP;
        this.baseSpellPower = baseSpellPower;
        this.baseAttack = baseAttack;
        this.baseAttackSpeed = baseAttackSpeed;
        this.range = range;
        this.movementSpeed = movementSpeed;
        this.maxLvlHP = maxLvlHP;
        this.maxLvlSpellPower = maxLvlSpellPower;
        this.maxLvlAttack = maxLvlAttack;
        this.potentialAbility4 = potentialAbility4;
        this.potentialAbility8 = potentialAbility8;
        this.sprite = sprite;
        this.avatar = avatar;
        this.illustration = illustration;
        this.heroSkill = heroSkill;
        this.releaseDate = releaseDate;
        this.attackCount = attackCount;
        this.backupHero = backupHero;
        this.special = special;
    }

    public Hero() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Region getRegion() {
        return region;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public Set<Awakening> getAwakenings() {
        return awakenings;
    }

    public Set<Skin> getSkins() {
        return skins;
    }

    public Integer getBaseHP() {
        return baseHP;
    }

    public Integer getBaseSpellPower() {
        return baseSpellPower;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public Integer getBaseAttackSpeed() {
        return baseAttackSpeed;
    }

    public Integer getRange() {
        return range;
    }

    public Integer getMovementSpeed() {
        return movementSpeed;
    }

    public String getPotentialAbility4() {
        return potentialAbility4;
    }

    public String getPotentialAbility8() {
        return potentialAbility8;
    }

    public String getSprite() {
        return sprite;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setAwakenings(Set<Awakening> awakenings) {
        this.awakenings = awakenings;
    }

    public HeroSkill getHeroSkill() {
        return heroSkill;
    }

    public void setHeroSkill(HeroSkill heroSkill) {
        this.heroSkill = heroSkill;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getBackupHero() {
        return backupHero;
    }

    public void setBackupHero(Boolean backupHero) {
        this.backupHero = backupHero;
    }

    public Integer getMaxLvlHP() {
        return maxLvlHP;
    }

    public void setMaxLvlHP(Integer maxLvlHP) {
        this.maxLvlHP = maxLvlHP;
    }

    public Integer getMaxLvlSpellPower() {
        return maxLvlSpellPower;
    }

    public void setMaxLvlSpellPower(Integer maxLvlSpellPower) {
        this.maxLvlSpellPower = maxLvlSpellPower;
    }

    public Integer getMaxLvlAttack() {
        return maxLvlAttack;
    }

    public void setMaxLvlAttack(Integer maxLvlAtk) {
        this.maxLvlAttack = maxLvlAtk;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public void setBaseHP(Integer baseHP) {
        this.baseHP = baseHP;
    }

    public void setBaseSpellPower(Integer baseSpellPower) {
        this.baseSpellPower = baseSpellPower;
    }

    public void setBaseAttack(Integer baseAtk) {
        this.baseAttack = baseAtk;
    }

    public void setBaseAttackSpeed(Integer baseAttackSpeed) {
        this.baseAttackSpeed = baseAttackSpeed;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setMovementSpeed(Integer movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setPotentialAbility4(String potentialAbility4) {
        this.potentialAbility4 = potentialAbility4;
    }

    public void setPotentialAbility8(String potentialAbility8) {
        this.potentialAbility8 = potentialAbility8;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public void setSkins(Set<Skin> skins) {
        this.skins = skins;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Integer getAttackCount() {
        return attackCount;
    }

    public void setAttackCount(Integer attackCount) {
        this.attackCount = attackCount;
    }
}
