package pl.wolinski.unofficialkgcapi.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.awakening.model.Awakening;
import pl.wolinski.unofficialkgcapi.awakening.repository.AwakeningRepository;
import pl.wolinski.unofficialkgcapi.hero.model.Hero;
import pl.wolinski.unofficialkgcapi.hero.repository.HeroRepository;
import pl.wolinski.unofficialkgcapi.heroClass.model.HeroClass;
import pl.wolinski.unofficialkgcapi.heroClass.repository.HeroClassRepository;
import pl.wolinski.unofficialkgcapi.heroSkill.model.HeroSkill;
import pl.wolinski.unofficialkgcapi.heroSkill.repository.HeroSkillRepository;
import pl.wolinski.unofficialkgcapi.region.model.Region;
import pl.wolinski.unofficialkgcapi.region.repository.RegionRepository;
import pl.wolinski.unofficialkgcapi.skin.model.Skin;
import pl.wolinski.unofficialkgcapi.skin.repository.SkinRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class DataBaseIni {

    private final AwakeningRepository awakeningRepository;
    private final HeroRepository heroRepository;
    private final HeroClassRepository heroClassRepository;
    private final RegionRepository regionRepository;
    private final SkinRepository skinRepository;
    private final HeroSkillRepository heroSkillRepository;

    public DataBaseIni(AwakeningRepository awakeningRepository, HeroRepository heroRepository, HeroClassRepository heroClassRepository,
                       RegionRepository regionRepository, SkinRepository skinRepository, HeroSkillRepository heroSkillRepository) {
        this.awakeningRepository = awakeningRepository;
        this.heroRepository = heroRepository;
        this.heroClassRepository = heroClassRepository;
        this.regionRepository = regionRepository;
        this.skinRepository = skinRepository;
        this.heroSkillRepository = heroSkillRepository;
    }

    // Regions
    private final Region regionCentral = new Region(1L, "Central", "https://drive.google.com/uc?id=1ioo0bsclP8F7GLsT1WNRI2UfvZEIJuVK");
    private final Region regionEast = new Region(2L, "East", "https://drive.google.com/uc?id=1YTTMrm0GxcwvR-hsZTqmJalQAUm7idY-");
    private final Region regionNorth = new Region(3L, "North", "https://drive.google.com/uc?id=1uS0DoHIkCItqAgrHmDLMNd4nCSf4Sr6b");
    private final Region regionSouth = new Region(4L, "South", "https://drive.google.com/uc?id=1bM9pHzRKLH986Ns1_3aG2NcbLXhJWNMH");
    private final Region regionWest = new Region(5L, "West", "https://drive.google.com/uc?id=1dJuhrrOqSrcSZpTH171SejDs9Cma8uJZ");

    // Hero Classes
    private final HeroClass heroClassTenacity = new HeroClass(1L, "Tenacity", "https://drive.google.com/uc?id=11H8s6Cu5J8O2GIsOg5uAWdjWugColNwb");
    private final HeroClass heroClassCourage = new HeroClass(2L, "Courage", "https://drive.google.com/uc?id=1yRMVpzwIlRQaIYrLnxvVozkh7ppENxL9");
    private final HeroClass heroClassElement = new HeroClass(3L, "Element", "https://drive.google.com/uc?id=1kssT9ep8lD7WvejHcgAcd9aSJJCrMP4h");
    private final HeroClass heroClassSwiftness = new HeroClass(4L, "Swiftness", "https://drive.google.com/uc?id=1GgPoL0su2BGQcXQdxoIC5WNz7xKsH66q");
    private final HeroClass heroClassMystique = new HeroClass(5L, "Mystique", "https://drive.google.com/uc?id=1SKDBjUwDep40ksOwzypUR3at3OTYh4ck");
    private final HeroClass heroClassShadow = new HeroClass(6L, "Shadow", "https://drive.google.com/uc?id=1Tx3lDCJKc4DYVoRmVX5VlSQGKX0wUe0J");

    // Hero Skills
    private final HeroSkill skillShelda = new HeroSkill(1L, "Iron Will", "Generates 30+[100% Spell Power] Protection upon herself with her willpower.",
            100, 100, "https://drive.google.com/uc?id=1FgOx7cKeXx73-OeTDIdvojaNA3FK4zXt");
    private final HeroSkill skillDaniel = new HeroSkill(2L, "Judgement of Light", "Smashes his hammer down with all his might, dealing " +
            "50+[100% Spell Power] damage to all enemies within 1[Tier depended] range.",0, 75,
            "https://drive.google.com/uc?id=1Ejya1bELDVuvcAhiKHmJXLwOMaWUBV1i");
    private final HeroSkill skillLeonhardt = new HeroSkill(3L, "Smite", "Smites enemies with his shield, dealing 30+[100% Spell Power] damage and " +
            "stunning all enemies within 1[Tier depended] horizontal range in front of him for 1.5 sec.", 0, 100,
            "https://drive.google.com/uc?id=1GXiyL6ctvdgidjsu3fqf6e0osyTa-CX1");
    private final HeroSkill skillEvan = new HeroSkill(4L, "Crescent Slash", "Unleash a piercing aura with his sword, dealing 20+[100% Spell Power] " +
            "damage to enemies within 1[Tier depended] horizontal range in front of him.", 0, 60,
            "https://drive.google.com/uc?id=15-xeSC5ZilhxD6RHwu_gkOU0ZrYH5ez9");
    private final HeroSkill skillPriya = new HeroSkill(5L, "Blizzard", "Deals 30+[100% Spell Power] damage, freezing the target and all " +
            "nearby enemies within 3X3[Tier depended] range for 1 sec.", 0, 120,
            "https://drive.google.com/uc?id=1Bx8UqQfHQ-rC1jE-mCm_dhLmvjdsGoFR");
    private final HeroSkill skillAramis = new HeroSkill(6L, "Headshot", "Deals [100% Spell Power] damage to a single target",
            0, 90, "https://drive.google.com/uc?id=1zpSYHGT8ma4nxxCKSMXfXQmY6rJydX3e");
    private final HeroSkill skillAlberon = new HeroSkill(7L, "Radiance of Life", "Recovers himself and all nearby allies HP " +
            "within 3X3[Tier depended] range by 236", 0, 70,
            "https://drive.google.com/uc?id=1IFGjFc7W_DIC1ndM3_-Yrp3PleOTA9n5");
    private final HeroSkill skillZuoYun = new HeroSkill(8L, "Enrage", "Unleashe the boiling rage and increase his " +
            "AttackSpeed by (50+[100% Spell power])% for 3 sec.", 0, 80,
            "https://drive.google.com/uc?id=1qvWCzkmdE-QWFgR5aH0LZlVOdLcyWsU6");
    private final HeroSkill skillDraco = new HeroSkill(9L, "Flamebreath", "Breathes out flame, dealing 10+[100% Spell Power] damage to all " +
            "enemies within 3[Tier depended] range in front of him 10 time(s).", 35, 70,
            "https://drive.google.com/uc?id=1RyQY26tv8sVENX-dTMpahb1TMFnkCh7-");
    private final HeroSkill skillBardrey = new HeroSkill(10L, "Lilting Melody", "Recovers MP of all nearby allies withing 3X3[Tier depended]" +
            " range by 10+[100% Spell Power].", 0, 30, "https://drive.google.com/uc?id=1gKeSYX_QRGTZeO2HqtVs-olEH-KF2rup");
    private final HeroSkill skillMara = new HeroSkill(11L, "Backstab", "Ambush the target from their back, dealing [100% Spell Power] " +
            "damage.", 75, 75, "https://drive.google.com/uc?id=10Zu78GOJMar3VcLDcr76scoEJvCT6YWp");
    private final HeroSkill skillHansi = new HeroSkill(12L, "Rapid Arrows", "Rapidly shoots additional 1 arrows on every attack during the round." +
            " (Max 2[Tier depended] arrows)", 0, 75, "https://drive.google.com/uc?id=1RhotV3rQtO85W9_v-joaGNcqiBWBpPyo");
    private final HeroSkill skillLyca = new HeroSkill(13L, "Natural Instinct", "Transform into a werewolf increasing his HP by 200% and ATK by (125%).",
            0, 120, "https://drive.google.com/uc?id=1NeGYhnWjRSluXoJHgDvPQSW5QH6aLHhH");
    private final HeroSkill skillLycanthrope = new HeroSkill(14L, "Wild Fury", "Dash towards a target, dealing [100% Spell Power] damage" +
            " and stuns the target for 1 sec.", 120, 120, "https://drive.google.com/uc?id=1d3qGENZyB06vfDJiJ-WyoY-jGzDpWCVq");
    private final HeroSkill skillJol = new HeroSkill(15L, "Avatar", "Unleash the power of the Most High, reducing damage taken by 6% and " +
            "becomeing immune to all crowd controls for 4 sec. While the skill is active, deals [100% Spell Power] damage to all enemies within " +
            "3X3[Tier depended] range instead of doing normal attacks and recovers HP by the same amount of the damage dealt.",
            0, 100, "https://drive.google.com/uc?id=1_5IHzfKK5raYCx4i54ch9rkjmmmsk2kZ");
    private final HeroSkill skillLily = new HeroSkill(16L, "Summon Golem", "Summon Sha-sha the Golem with 150% HP and 150% ATK and mount on it",
            130, 130, "https://drive.google.com/uc?id=1BNLYImUgMDEM_IWjPF8Fq-ndtgi-gyCp");
    private final HeroSkill skillLilyAndShaSha = new HeroSkill(17L, "Vitality of Nature", "Draw out the vitality of nature, recovering Sha-sha's" +
            " HP by 20+[100% Spell Power].", 0, 60, "https://drive.google.com/uc?id=1kp1OWBTzsTsbRhKomqUgO8yW2HAltbVh");
    private final HeroSkill skillZupitere = new HeroSkill(18L, "Crackle", "Discharge streaks of lightning that bounces between enemies for 1 times " +
            "dealing 5+[100% Spell Power] damage.", 0, 15, "https://drive.google.com/uc?id=1omuxD7P87K1C-a0K1kQTb9ELblv0Vq6E");
    private final HeroSkill skillAsiaq = new HeroSkill(19L, "Soul Link", "Link 3 targets with spiritual chains and make them share damage " +
            "dealt on them, stun effects, and slow effects. The chains remain for 7 sec and break when the shared damage reaches " +
            "50+[100% Spell Power], dealing additional 12.5+[25% Spell Power] damage.", 45, 90,
            "https://drive.google.com/uc?id=1AE1ZHHLH6D6AwshM8L7xv_1lSuI5bJyp");
    private final HeroSkill skillBehemus = new HeroSkill(20L, "Reckless Protection", "Lands in front of an ally at the lead, dealing" +
            " 40+[100% Spell Power] damage to all enemies withing 3X3[Tier depended] range and pushing them back by 2 blocks.",
            50, 100, "https://drive.google.com/uc?id=1ZLXC1jKsfPgxH73XrdDjnQmxFrco7IKo");
    private final HeroSkill skillYeon = new HeroSkill(21L, "Calm and Stormy", "Launch a wave that hits both nearby enemy and ally back" +
            " and forth for 4 times dealing 30+[100% Spell Power] damage upon the enemy and recovers the ally's " +
            "HP by the same amount.", 0, 75, "https://drive.google.com/uc?id=1XqnEaCiytuIO0tvqVd0ImzuxMam35YTP");
    private final HeroSkill skillMel = new HeroSkill(22L, "Advent of the Light", "Strikes the Holy Blade down, dealing [100% Spell Power] damage" +
            " to enemies upfront then attacks with the Holy Blade for 5 sec. The ATK of the Holy Blade increases" +
            " by (125+[1.5% Spell Power])%.", 0, 80, "https://drive.google.com/uc?id=1YNRdgE0RS7zvIGMeWPDh6k2zEkb6t6fY");
    private final HeroSkill skillBombie = new HeroSkill(23L, "Rain of Bombs", "Bombie throws bombs from his bag to random targets" +
            " with extra 60% Attack Speed for 3+[2% Spell Power] sec.", 40, 80,
            "https://drive.google.com/uc?id=1Gjq4SHMyYmKu-QK4EAXbswkSmfRyXrQr");
    private final HeroSkill skillLuniare = new HeroSkill(24L, "Blessing of the Blue Moon", "Concentrates for 5 sec and grants 15+[100% Spell Power] " +
            "Protection to the linked target, and gives 90% of ATK and Spell Power converted into the target Hero's base stats" +
            " (50% efficiency when applied to the same hero).", 0, 75,
            "https://drive.google.com/uc?id=135uxmymHpLFrtO--YrY7rPCKcynx2ZHk");
    private final HeroSkill skillRossette = new HeroSkill(25L, "Giant Smite", "Leaps into the air to strike, dealing ([100% Spell Power] + 20% of" +
            " the target's Max HP) damage to an enemy. (Max Damage [300% Spell Power])", 30, 60,
            "https://drive.google.com/uc?id=1k-g9ehwO03vBul_FLw7Mk2cSoiSkxNcx");
    private final HeroSkill skillCain = new HeroSkill(26L, "Circus of Death", "Cain deals 5+[100% Spell Power] damage to the enemy with the lowest" +
            " HP ratio. The damage increases by 4% per 1% of the target's lost HP.", 0, 75,
            "https://drive.google.com/uc?id=1dp76VqcVEE5DglJAzKAMupjRX7y_vHxh");
    private final HeroSkill skillChungAh = new HeroSkill(27L, "Horizontal Dragon Edge", "Deals 10+[100% Spell Power] damage to all enemies within " +
            "3[Tier depended] horizontal range in front of her. After casting <Horizontal Dragon Edge> 3 times, casts <Azure Dragon Slash> that deals " +
            "2.5/3/3.5/4 times more damage in a wide range.", 0, 15,
            "https://drive.google.com/uc?id=1nvelaIvhG_24P8QJdhAz_SmUn4EuZm6y");
    private final HeroSkill skillRahawk = new HeroSkill(28L, "Hunt Command", "Deals 20+[100% Spell Power] damage to the farthest enemy and " +
            "reduces the target's total Attack Speed by 15% for 5 sec (max 2 stacks).", 45, 45,
            "https://drive.google.com/uc?id=1qlDe1CmRvCQCPBC1Q1o9j3yTMQi4sbDh");
    private final HeroSkill skillRen = new HeroSkill(29L, "Full Bloom", "Deals [100% Spell Power] damage 1 times to all enemies within " +
            "3X3[Tier depended] range in front of her. Increases next <Full Bloom>'s attack counts by 1 (Max 6 times).",
            0, 30, "https://drive.google.com/uc?id=11sVV1_9dUq2pkJhyDe8I7nqygxX6z4hA");
    private final HeroSkill skillAgathe = new HeroSkill(30L, "Protection of Radiance", "Creates a barrier withing 2[Tier depended] range " +
            "for 3+[1% Spell Power] sec, taking 40% of damage taken by all allies. However, takes 10% reduced damage.",
            0, 80, "https://drive.google.com/uc?id=17wz6lsYRC1SaAmObRN1guc4YVaLsbRJq");
    private final HeroSkill skillHela = new HeroSkill(31L, "Summon Meteor", "Instead of doing normal attacks, concentrates for 3 sec and drops meteors" +
            " that deals 15+[100% Spell Power] damage to enemies within 2[Tier depended] range for 3 times starting with the" +
            " nearest enemy.", 0, 0, "https://drive.google.com/uc?id=1lAo89AtoAyYJ_YF657U4Ajyqmc7OLnj6");
    private final HeroSkill skillZuoBai = new HeroSkill(32L, "Crimson Slash", "Moves 3 times consecutively behind enemies to deal 10+[100% Spell Power] " +
            "damage each time, starting with the nearest enemy.", 0, 75,
            "https://drive.google.com/uc?id=1KgK0FHpTGOFQZeHkMpoWcqzHGhbCjBud");
    private final HeroSkill skillTia = new HeroSkill(33L, "Gust", "If the target is withing 3[Tier depended] range, moves 3 block(s) backwards " +
            "and does 3 normal attacks. If the target is not withing 3[Tier depended] range, deals 140% of ATK as damage 3 times.",
            0, 45, "https://drive.google.com/uc?id=14Oqsrui2XiLdI-fBgv1cdFV62v2mOVUr");
    private final HeroSkill skillMirsyl = new HeroSkill(34L, "Verdant Garden", "Forms a magical garden withing 3X3[Tier depended] range around the" +
            " target. The garden reamins for 3sec, dealing 30+[100% Spell Power] damage to all enemies on the garden and recovering allies' HP by the same" +
            " amount (each Mirsyl can summon up to 2 gardens).", 35, 70,
            "https://drive.google.com/uc?id=1aVPzKOjK5nQvlGBRzXipf4nc12d49Scj");
    private final HeroSkill skillTaesan = new HeroSkill(35L, "Power of Great Tiger", "Deals 30+[100% Spell Power] damage to all enemies within" +
            " 3[Tier depended] horizontal range in front of him and pulls them by 2 block(s). Afterward, absorbs the power of the Great Tiger" +
            " and obtains Protection equal to 30% of Max HP.", 45, 90,
            "https://drive.google.com/uc?id=1a51Rh4QEVizuZjPoC3yKGA4jnWYX_qxt");
    private final HeroSkill skillNeria = new HeroSkill(36L, "Annihilation Time", "Shoots enhanced arrows 4 times that deal (300+[10% Spell Power])%" +
            " of ATK. During <Annihilation Time>, Neria's Attack Speed is fixed to 75% and her Range limit is lifted.", 0, 60,
            "https://drive.google.com/uc?id=1fNqY5gEQHH7J1SxW7o7U5-eJuenih5IR");
    private final HeroSkill skillHaerang = new HeroSkill(37L, "Wave Catcher", "Sends concentrated waves 2 times that deal 10+[100% Spell Power]" +
            " damage and stun for 1.25 sec, starting with the nearest enemy.", 0, 75,
            "https://drive.google.com/uc?id=1joRVHaopQgN35Zkpj-uAmwqDvjLqN2Ie");
    private final HeroSkill skillGidnil = new HeroSkill(38L, "Holy Protection", "Generates Spell Shield upon self 2 time(s). Spell Shield blocks" +
            " the spell damage of the enemy's skill 1 time and blocks the harmful effect of the spell damage.", 75, 75,
            "https://drive.google.com/uc?id=1XVCS8JT-UNZGfSHVlbiAfUkT1zz8ZDHG");
    private final HeroSkill skillKanak = new HeroSkill(39L, "Soul Rip", "Deals [100% Spell Power] damage to the target. Deals 20% additional damage on" +
            " Kanak's normal attack for every curse stacked on the target (max 6 times).", 0, 45,
            "https://drive.google.com/uc?id=1ICRaSicYnHRgBLWZeo3F8kKRMn88sdy0");
    private final HeroSkill skillRie = new HeroSkill(40L, "Order: Elimination", "Summons up to 3 doll(s) and makes them charge at enemies to" +
            " deal [100% Spell Power] damage. The doll(s) deal [10% Spell Power] damage on normal attacks, and all damage dealt by the doll(s) is" +
            "calculated as Rie's damage.", 0, 45, "https://drive.google.com/uc?id=1fY4VFQVk4mPia0FJPxn8giDlpfLVI2dY");
    private final HeroSkill skillMachina = new HeroSkill(41L, "Obedience: Elimination", "Machina deals damage equal to 10% of Rie's Spell Power" +
            " upon normal attack, and has 10% of Rie's HP and 100% of Rie's Spell Power, Attack Speed, and Movement Speed.",
            0, 0, "https://drive.google.com/uc?id=10VMoGcrocGJMbQmoOwB6OppzhpLMIfIN");
    private final HeroSkill skillNibella = new HeroSkill(42L, "Desert Charge", "Charges toward the back of the farthest enemy, swinging her" +
            " great sword and dealing 20+[100% Spell Power] damage to all enemies withing 3X3[Tier depended] range in the path" +
            " of the charge.", 0, 90, "https://drive.google.com/uc?id=1tlTqxmBBwB6zJO8gX5pm5by0w4nHpS6R");
    private final HeroSkill skillTaebaek = new HeroSkill(43L, "White Tiger Kick", "Provokes 3 nearest enemies for 4 sec and deals a total of" +
            " [100% Spell Power] damage to 5X5[Tier depended] range. After 4 sec, deals damage equal to [100% Spell Power] + 30% of damage taken" +
            " to the same range.", 0, 60, "https://drive.google.com/uc?id=1gD1HgXVL_caSLvbBRZkFjEuOh0tUF8lu");
    private final HeroSkill skillCathy = new HeroSkill(44L, "Dancing Bullet", "Attacks the target with normal attack enhanced by 100% 15 time(s)" +
            " for 1 sec. After attacking, a bullet rocichets to the nearest enemy on the opposite side of the target and deals 70% of the first bullet's damage.",
            35, 70, "https://drive.google.com/uc?id=1_5DRvi0ibKlu4AQYlsGmqT8ate76Ei8X");
    private final HeroSkill skillEsthea = new HeroSkill(45L, "Sacred Censer", "Heals the ally with the lowest HP ratio in 7X7[Tier depended] range" +
            " by 160% of Esthea's ATK, and increases the next normal attack of the target by 160% of Esthea's ATK.", 0, 45,
            "https://drive.google.com/uc?id=1Lnyuva__RjWiAqZT1dD0hEfSbOpHSxlN");
    private final HeroSkill skillBaldir = new HeroSkill(46L, "Oddity of the Forest", "When placed at the front row (front 2 rows), transforms into" +
            " Nature's Fury form with 180% HP. When placed at the back row (back 2 rows), transforms into Eternal Wisdom form with 50% Attack Speed" +
            " and 200% increased Spell Power.", 0, 75, "https://drive.google.com/uc?id=1ezQRwxqeAVMGUq6Q5jDI6wuyYx4GX4C4");
    private final HeroSkill skillNaturesFury = new HeroSkill(47L, "Timber Split", "The next 3 normal attack(s) deal damage equal to ATK+" +
            "[100% Spell Power] to enemies withing 3x3[Tier depended] range in front, 40% HP Drain (Attack/Spell damage) is applied to the damage.",
            0, 75, "https://drive.google.com/uc?id=1vD_YTSVMVnDz60s_2vsCSxOOjPkdQcVc");
    private final HeroSkill skillEternalWisdom = new HeroSkill(48L, "Energy of the Forest", "Concentrates the mana of the forest and unleashes it" +
            " as droplets to deal 20+[100% Spell Power] damage to up to 3[Tier depended] nearest enemies", 0, 75,
            "https://drive.google.com/uc?id=1YLyFpxFQDFzSUhgB42YLqiuAerIOFNZY");
    private final HeroSkill skillIan = new HeroSkill(49L, "Stride of Steel", "Concentrates for 1.5 sec and charges to the target with enhanced" +
            " normal attack that deals damage equal to (100+[10% Spell Power])% of ATK to all enemies withing 3x3[Tier depended] range in" +
            " front 2 time(s).", 0, 0, "https://drive.google.com/uc?id=1IgPeBItMFslaAeQwyG3-2J6QuYKXkavD");
    private final HeroSkill skillOphelia = new HeroSkill(50L, "Blood and Soul", "Deals [100% Spell Power] damage 2/4/6/9 times to the enemy with the" +
            " highest tier within 7X7[Tier depended] range and recovers HP by the same amount of the damage dealt. While absorbing HP, reduces the enemy's" +
            " Spell Power by 65.", 0, 60, "https://drive.google.com/uc?id=1vK2PmbXZLtqYM7u8VKy6Cz1D9o6zA1pm");
    private final HeroSkill skillKirdan = new HeroSkill(51L, "Storm Assault", "Deals damage 10[Tier depended] time(s) in total with " +
            "40+[100% Spell Power] damage each time to all nearby enemies within 3X3 range for 3.5sec. Deals damage 1 more time for every " +
            "21%[Tier depended] of additional Attack Speed. When using the skill, moves to the location where Kirdan can attack the most enemies.",
            0, 30,
            "https://drive.google.com/uc?id=1RI-qKARF8nomXZyKMMn8SiDriWq8c2fi");

    //HeroSkill skill = new HeroSkill(0L, "x", "x", 0, 0, "https://drive.google.com/uc?id=");



    // Heroes
    private final Hero heroShelda = new Hero(1L, "Shelda", "Shield of Knights", regionCentral, heroClassTenacity, 220, 40, 15,
            91, 1, 150, 1298, 236, 89, "+50% for all obtained Protection",
            "-20 MP Cost of <Iron Will>", "https://drive.google.com/uc?id=1gXs2biFMEoyJDWSjLT833meIDMVBLoBn",
            "https://drive.google.com/uc?id=1ayY8QMZh87zOhzQU31gWV2NWnAnY87r-","https://drive.google.com/uc?id=1zY-MMWNjguAiDDYlma2ukrKrk_Nmefnl",
            skillShelda, LocalDate.of(2020, 10, 19), 1, false, false);
    private final Hero heroDaniel = new Hero(2L, "Daniel", "Hammer of the Holy Church", regionSouth, heroClassCourage, 250,
            20, 20, 100, 1, 150, 1475, 118, 118,
            "+1.5 sec Stun effect on <Judgement of Light>", "+20% Max HP",
            "https://drive.google.com/uc?id=195SrtYi1hn2Ry2FstkbLbIrhAlLBiFG5", "https://drive.google.com/uc?id=1xg4WrO5ouW_S4FXfHRUipz0tpNsdFegm",
            "https://drive.google.com/uc?id=1drFXYXvMBZEt4l84g_QsIJ6_6UWM_P_g", skillDaniel, LocalDate.of(2020, 10, 19),
            1, false, false);
    private final Hero heroLeonhardt = new Hero(3L, "Leonhardt", "Heart of Lion", regionNorth, heroClassCourage, 300, 15,
            15, 91, 1, 150, 1770, 89, 89,
            "-10% Incoming Damage", "Reflects 20% incoming damage (halved for spell damage)",
            "https://drive.google.com/uc?id=1eMR5bNE6-D8LN9ds35R6wFPUxe8KhNeO", "https://drive.google.com/uc?id=1Wc1NLB36RKTwrhT-l9-PCrPpMWcBDZRH",
            "https://drive.google.com/uc?id=176tBjiq5pJjxgjYK6s2HXIG3K8tJGzQ8", skillLeonhardt, LocalDate.of(2020, 10, 19),
            1, false, false);
    private final Hero heroEvan = new Hero(4L, "Evan", "Sword of Valor", regionCentral, heroClassCourage, 250, 20, 25,
            120,1, 150, 1475, 118, 148, "Recover 100% of MP at the start of the battle",
            "+40% Spell Power", "https://drive.google.com/uc?id=1BUV0Kt93e78C0g2-dGL4Z50r3Zgl_UGb", "https://drive.google.com/uc?id=1pAnK5VoG44DWjDbNDEoaaIV_5BUQXcSx",
            "https://drive.google.com/uc?id=1XdE1lbTZCJU7r9ngqRKEONxv3THIWFbn", skillEvan, LocalDate.of(2020, 10, 19),
            1, false, false);
    private final Hero heroPriya = new Hero(5L, "Priya", "Mage of Freezing", regionCentral, heroClassElement, 120, 65, 15,
            100, 3, 150, 708, 384, 89, "Stun duration time +1 sec on <Blizzard>",
            "- 20 MP Cost of <Blizzard>", "https://drive.google.com/uc?id=17tXYnDdrzbyWk-85keyjrvNpBg2tdLwd", "https://drive.google.com/uc?id=17Ju2Xi6vZwEW83J4Pn8gVNNhpgTJmRkR",
            "https://drive.google.com/uc?id=1H8sH_CuzmWQBEP2TsaD7yR8ZRvDTtp3K", skillPriya, LocalDate.of(2020, 10, 19),
            1, false, false);
    private final Hero heroAramis = new Hero(6L, "Aramis", "Bullseye", regionCentral, heroClassSwiftness, 150, 50, 35,
            67, 6, 150, 885, 295, 207, "+1 Range",
            "+20% Attack Speed", "https://drive.google.com/uc?id=1ZAPgfq3D4-gaSZeIXT4qL0PIJ3IwuwS1", "https://drive.google.com/uc?id=1-x86Sjf29q-VRly9GRCuVCQp6Gao7KuW",
            "https://drive.google.com/uc?id=1hykRGI6pxBDg0smyGcyxPd17bWrTL79j", skillAramis, LocalDate.of(2020, 10, 19),
            1, false, false);
    private final Hero heroAlberon = new Hero(7L, "Alberon", "Hand of Divinity", regionSouth, heroClassMystique, 120, 40, 10,
            100, 2, 150, 708, 236, 59, "+30% HP Recovery on <Radiance of Life>",
            "Convert 25% of exceeded HP Recovery to Protection on <Radiance of Life>", "https://drive.google.com/uc?id=1yvSel-iACNfXNj2p_KTj4JfBrKbzuNuv",
            "https://drive.google.com/uc?id=1vA6HnltfGjgNRz0u35ytQOzCm2s1BVuy", "https://drive.google.com/uc?id=1pfmoYGLZlmMkrUQ-R69XyDGUcV5xE3S3",
            skillAlberon, LocalDate.of(2020, 10, 19), 1, true, false);
    private final Hero heroZuoYun = new Hero(8L, "Zuo Yun", "Crimson Spear", regionEast, heroClassTenacity, 180, 10, 20,
            125, 2, 150, 1062, 59, 118, "+30% Attack Speed during <Enrage>",
            "+20% HP Drain (Attack damage)", "https://drive.google.com/uc?id=1SgEBycja4NPhkmCIOzPdEeZk51CROB4X",
            "https://drive.google.com/uc?id=15ZTBwS8nh2i2VZIuk4dE2yczIWSP8qpn",
            "https://drive.google.com/uc?id=1glwGb-GOqPsjpn8hmfyn7eBFvcRGULtb",
            skillZuoYun, LocalDate.of(2020, 10, 19), 1, false, false);
    private final Hero heroDraco = new Hero(9L, "Draco", "Half-Dragon", regionWest, heroClassElement, 180, 8, 20,
            100,
            2, 150, 1062, 47, 118,
            "When all targets of <Flamebreath> are killed, MP is returned in proportion to the remaining skill damage count",
            "Drain +50% of the Spell Power damage as HP",
            "https://drive.google.com/uc?id=1dHCFXYi51-OEEMmNs4KJ2LUIyoSFpcuU", "https://drive.google.com/uc?id=1vFK5EApXbNw1OEm0fELNabRDw8D5WC4X",
            "https://drive.google.com/uc?id=1Mzd6j67UdbDrNBtkXdiRWXdTeWnuEoIF",
            skillDraco, LocalDate.of(2021, 10, 19), 1, false, false);
    private final Hero heroBardrey = new Hero(10L, "Bardrey", "Bard", regionSouth, heroClassSwiftness, 150, 5, 12,
            100, 2, 150, 885, 30, 71, "+5 MP Recovery of <Lilting Melody>",
            "+20% Attack Speed", "https://drive.google.com/uc?id=1Pwwh6mZxkfpnNrBogQPc7-R-kcIWZYTG", "https://drive.google.com/uc?id=1Fp3DUe0ENihz_85ZPDS1GXc336i_WZvt",
            "https://drive.google.com/uc?id=1JAuQaZP-uP6c3hkH_3GqkesOG4c9YMMK", skillBardrey, LocalDate.of(2020, 10, 19), 1,
            true, false);
    private final Hero heroMara = new Hero(11L, "Mara", "Dark Blade", regionNorth, heroClassShadow, 140, 60, 11,
            100,1, 220, 826, 354, 65, "+1.5 sec Stun effect on <Backstab>",
            "+1 Mighty Block after <Backstab>", "https://drive.google.com/uc?id=1nijTaofgLbIqxX1k0kZSkTmCmb2-uya8",
            "https://drive.google.com/uc?id=1NpuCW5zQcGwmICGTT25kSqcdyD5OsR5w", "https://drive.google.com/uc?id=1AICBLSa4LjF7zf9nafi4ln6k1Q6oc_Hv",
            skillMara, LocalDate.of(2020, 10, 19), 4, false, false);
    private final Hero heroHansi = new Hero(12L, "Hansi", "Scarlet Arrow", regionEast, heroClassSwiftness, 150, 0, 7,
            125, 3, 150, 885, 0, 41,
            "Use <Rapid Arrows> at the beginning of the battle", "+1 Range",
            "https://drive.google.com/uc?id=1PCi2kA8OCM56LfuX1Zuc6AGclZNh4_8X", "https://drive.google.com/uc?id=1cUFU1yTOMFQiOth93n6FTQUXt7ktzERZ",
            "https://drive.google.com/uc?id=1ND8_ttO1DQcFZARokk9cfQkMsFV0sS96", skillHansi, LocalDate.of(2020, 10, 19), 2,
            false, false);
    private final Hero heroLyca = new Hero(13L, "Lyca", "Werewolf", regionCentral, heroClassShadow, 140, 30, 20,
            125, 2, 150, 826, 177, 118,
            "+20% Max HP (maintains even after the end of transformation)", "-20 MP cost of <Natural Instinct> and -40 MP cost of <Wild Fury>",
            "https://drive.google.com/uc?id=1D_nr7c3OQFJQWRlcp2J8nrmgzKz8zqf1", "https://drive.google.com/uc?id=1aWG-pjXwpUmy3CmR07jtaUkx0EEv2wUy",
            "https://drive.google.com/uc?id=1sbnRdVd8CCdhWQ9RLclyQ5w01JwRV03W", skillLyca, LocalDate.of(2020, 10, 19), 1,
            true, false);
    private final Hero heroLycanthrope = new Hero(14L, "Lycanthrope", "Cursed Wolf", regionCentral, heroClassShadow, 280, 30,
            25, 125, 1, 150, 1652, 177, 148,
            "+20% Max HP (maintains even after the end of transformation)", "-20 MP cost of <Natural Instinct> and -40 MP cost of <Wild Fury>",
            "https://drive.google.com/uc?id=1WNSJIAen8WKAutGZpOyQdhH9dYkCHl9a", "https://drive.google.com/uc?id=1EUxtsmlMl2OC7oEvHv-NcyIopvUTTh75",
            "https://drive.google.com/uc?id=1sbnRdVd8CCdhWQ9RLclyQ5w01JwRV03W",
            skillLycanthrope, LocalDate.of(2020, 10, 19), 1, false, true);
    private final Hero heroJol = new Hero(15L, "Jol", "Elven Prince", regionWest, heroClassCourage, 220, 14, 15,
            100,  1, 150, 1298, 83, 89, "Recover 90% of MP at the start of the battle",
            "Duration time +1 sec on <Avatar>", "https://drive.google.com/uc?id=1k1YXRw81ga_6Kln90F_eAtAtkkQMCQkv",
            "https://drive.google.com/uc?id=1tN5x_b8mmr36eJPGQb1xdlBu3lNBKykj", "https://drive.google.com/uc?id=1od-zLfP1_XfdzdaKiutnxHH2m4HhSKcs",
            skillJol, LocalDate.of(2020, 11, 6), 1, false, false);
    private final Hero heroLily = new Hero(16L, "Lily", "The Forest Mystic", regionWest, heroClassElement, 100, 50, 10,
            100, 2, 150, 590, 295, 59, "Increase Sha-sha's HP to 200% of Lily's HP",
            "-30 MP Cost of <Summon Golem> and <Vitality of Nature>", "https://drive.google.com/uc?id=1BTegEs0OwW2_cJCu4MFfw_8ghAFa4U4U",
            "https://drive.google.com/uc?id=1WSYg7V1JNvvuCQ2myK6ZSmkI4YE5xsm5", "https://drive.google.com/uc?id=1_4b3P4gTGlucC0GTXMOLEpvFQsvZCPYe",
            skillLily, LocalDate.of(2020, 11, 19), 1, true, false);
    private final Hero heroLilyAndShaSha = new Hero(17L, "Lily and Sha-sha", "Forest Spirit", regionWest, heroClassElement, 150, 50,
            15, 91, 1, 150, 885, 295, 89, "Increase Sha-sha's HP to 200% of Lily's HP",
            "-30 MP Cost of <Summon Golem> and <Vitality of Nature>", "https://drive.google.com/uc?id=1r2zUffcJ4-mES6fqg5zvH1Nxt5l73n1H",
            "https://drive.google.com/uc?id=1M4rE2gbl8JQm7M3WgvCixrf2hG0qvpbb", "https://drive.google.com/uc?id=1_4b3P4gTGlucC0GTXMOLEpvFQsvZCPYe",
            skillLilyAndShaSha, LocalDate.of(2020, 11, 19), 1, false, true);
    private final Hero heroZupitere = new Hero(18L, "Zupitere", "Fierce Lightning", regionSouth, heroClassElement, 150, 9, 9,
            100, 2, 150, 885, 53, 53, "+100% Damage to the first target of <Crackle>",
            "+1 Target of <Crackle>", "https://drive.google.com/uc?id=1VfvKSanGpFqfzlXjNdzeC4ggrvMcq-q0",
            "https://drive.google.com/uc?id=1vcI1-Bn3ZzTMsO3SxIUW-Cz3f6gNftjE",
            "https://drive.google.com/uc?id=16feR_FTF0Ar7jmA71Fg3nYn7s6_6Xz5w", skillZupitere, LocalDate.of(2020, 12, 2),
            1, false,false);
    private final Hero heroAsiaq = new Hero(19L, "Asiaq", "Shaman of the North", regionNorth, heroClassMystique, 140, 70, 10,
            100, 3, 150, 826, 413, 59,
            "Stuns linked enemies for 1.5 sec if one enemy under <Soul Link> gets killed", "+50% Attack Speed during <Soul Link>",
            "https://drive.google.com/uc?id=1NXRx52LiWtx44hMEO7_UaOkYNHZKe_5f", "https://drive.google.com/uc?id=1zTyulzU_gjhNtS5K8M3agvrrp1_tcApI",
            "https://drive.google.com/uc?id=1JZV9jfx3pjX3DK_4zlgYaWL3jrCYZaac", skillAsiaq, LocalDate.of(2020, 12, 22), 1,
            true, false);
    private final Hero heroBehemus = new Hero(20L, "Behemus", "Black Armor", regionSouth, heroClassTenacity, 250, 20, 13,
            100, 1, 150, 1475, 118, 77,
            "Push back range +1 for <Reckless Protection>",
            "Grant Protection with 100% of dealt damage as its duration upon himself and the targeted ally of <Reckless Protection>",
            "https://drive.google.com/uc?id=14X7UW7P1mK1lElvaVTEuGKiQcACowlRs", "https://drive.google.com/uc?id=1V4DkNdXwEaqJIpYprw8fPMGFoZeSxirh",
            "https://drive.google.com/uc?id=1XcZF3BASbw8_vJcmJLxp3TZnPNjLofG2", skillBehemus, LocalDate.of(2021, 1, 15),
            1, false, false);
    private final Hero heroYeon = new Hero(21L, "Yeon", "Wave of Equilibrium", regionEast, heroClassElement, 150, 40, 15,
            100, 3, 150, 885, 236, 89,
            "20% HP Recovery upon ally of <Calm and Stormy>",
            "Up to 2 waves of <Calm and Stormy> can be on the battlefield at the same time",
            "https://drive.google.com/uc?id=1-pJ1HTsv986RwfdjLFUInLLj9SlH_w8b", "https://drive.google.com/uc?id=13z0BIPvllIe8QtYLQW9GTJt2F5dgSE73",
            "https://drive.google.com/uc?id=18Z_B0feEnEyYdFuSkkL7GVFnmAGXVUU0", skillYeon, LocalDate.of(2021, 1, 29),
            1, false, false);
    private final Hero heroMel = new Hero(22L, "Mel", "Wandering Warrior", regionCentral, heroClassCourage, 220, 50, 30,
            83, 1, 150, 1298, 295, 177, "+30% Movement Speed on Mel",
            "+2 sec Stun effect on enemies hit by the shockwave of <Holy Blade Garna>",
            "https://drive.google.com/uc?id=1vZvcaUwPyC0TSJzNXQZpPhqcHr0agc1x", "https://drive.google.com/uc?id=1vnlh5e0h1AMi6-0GRq5VU0jx5EpzZB94",
            "https://drive.google.com/uc?id=1yM2_DKRpEd-zmHZoc1YMLnFVH0Llp3Jr", skillMel, LocalDate.of(2021, 3, 12),
            1, false, false);
    private final Hero heroBombie = new Hero(23L, "Bombie", "Mercenary Bomber", regionNorth, heroClassSwiftness, 150, 10, 20,
            100, 3, 150, 885, 59, 118,
            "Deals 50% of damage to nearby enemies in 3X3 of the target while casting <Rain of Bombs>",
            "Deals 50% of damage to nearby enemies in 3X3 of the target on normal attack",
            "https://drive.google.com/uc?id=10sozWw8-vlntJTssJeIqnDGYJ3aXq6Y2", "https://drive.google.com/uc?id=1AajoVjrvtrWSA_H7qk-vKU-I_TT5s8r1",
            "https://drive.google.com/uc?id=1tsZdrT6e1w9nWywao8xzGmD4c_It4N0k", skillBombie, LocalDate.of(2021, 4, 16),
            1, false, false);
    private final Hero heroLuniare = new Hero(24L, "Luniare", "Blue Moonlight", regionNorth, heroClassMystique, 150, 15, 15,
            100, 3, 150, 885, 89, 89,
            "<Blessing of the Blue Moon> Grants 1 Mighty Block to the target",
            "After the end of <Blessing of the Blue Moon>, buff remains for an additional 2 sec",
            "https://drive.google.com/uc?id=1Us2o5eILYs4myUKTnT0y9syyUszYqLpw", "https://drive.google.com/uc?id=1n7_7QcllL6nJtfvXGHXzLXFGGz1LrU3O",
            "https://drive.google.com/uc?id=1MWAER0g738958eVf0gqNQ2Yy5vs-xQ6X", skillLuniare, LocalDate.of(2021, 6, 4),
            1, true, false);
    private final Hero heroRossette = new Hero(25L, "Rossette", "Axe of the Empire", regionSouth, heroClassTenacity, 300, 50,
            20, 77, 1, 150, 1770, 295, 118,
            "-20% damage taken from the target she is attacking with normal/skill attack",
            "-10% damage dealt by the target she is attacking with normal/skill attack",
            "https://drive.google.com/uc?id=1IrFzs9jcj1BMihuV-Lgftlr3AsZoVBuE", "https://drive.google.com/uc?id=1DJ_xGrp6KPXiwlCGadQTw5vOTbTaHhV3",
            "https://drive.google.com/uc?id=1klw55qQAyvepMePTicHriynptyQ-jvlh", skillRossette, LocalDate.of(2021, 6, 18),
            1, false, false);
    private final Hero heroCain = new Hero(26L, "Cain", "Ashen Slayer", regionNorth, heroClassShadow, 150, 30, 12,
            125, 1, 220, 885, 177, 71,
            "Deals excess damage to enemies withing 2 range of the hero when a target is killed with <Circus of Death>",
            "Obtains 1 Silver when a target is killed with <Circus of Death>",
            "https://drive.google.com/uc?id=16Uo4XpAuJXTQWryYnprbc77F0kJGPuTJ", "https://drive.google.com/uc?id=1E1y-hlouc-Twq6JRwGZ-m0Allox0r9XF",
            "https://drive.google.com/uc?id=1bMzFchNNbvlD9U8SdlSrC67cEeeOARXZ", skillCain, LocalDate.of(2021, 7, 2),
            2, false, false);
    private final Hero heroChungAh = new Hero(27L, "Chung Ah", "Successor of the Azure Dragon", regionEast, heroClassTenacity, 250,
            12, 15, 125, 1, 150, 1475, 71, 89,
            "-1 <Horizontal Dragon Edge> needed for casting <Azure Dragon Slash>",
            "+60% Spell Drain on <Azure Dragon Slash>",
            "https://drive.google.com/uc?id=18tdKZC6E0nqS7yY4g6O6DESoLuLFLT59", "https://drive.google.com/uc?id=1KDuKQwYvfxlimmeQzIFrptOZOsLrEJvN",
            "https://drive.google.com/uc?id=18xMvx15es1kK1EI8A8dLmoObw4IOg5-C", skillChungAh, LocalDate.of(2021, 8, 6),
            1, false, false);
    private final Hero heroRahawk = new Hero(28L, "Rahawk", "Hunter of the Northern Plains", regionNorth, heroClassSwiftness, 150, 30,
            15, 125, 4, 150, 944, 177, 89,
            "+35% Attack Speed after using <Hunt Command> (max 3 stacks)",
            "+10% damage to <Hunt Command> target (max 2 stacks, effect halved to bosses)",
            "https://drive.google.com/uc?id=1lCBZbzqFYzkdd8SIr__9n6iIPVzcIiql", "https://drive.google.com/uc?id=11_dE6YvzuSDOM4R-TXMpw21rDds5L7qQ",
            "https://drive.google.com/uc?id=1rlpXqrDwrdy6ZbWCC792dea4LFPo5KEm", skillRahawk, LocalDate.of(2021, 9, 10),
            1, false, false);
    private final Hero heroRen = new Hero(29L, "Ren", "Blooming Sword", regionSouth, heroClassShadow, 250, 20, 20,
            100, 1, 150, 1475, 118, 118,
            "+100% Movement Speed for 1 sec after killing a target",
            "+1 Mighty Block per target when <Full Bloom> is used (max +2)",
            "https://drive.google.com/uc?id=1x13_PKfgmXenUUZkzTXTupAn4LqEl2yv", "https://drive.google.com/uc?id=1KuTiHrlxkaNBhDAL2tm6Bip08R6wkopy",
            "https://drive.google.com/uc?id=1ZqwRrboC1Y8fx4Ken5q6j7Lm2Lu-iO4P", skillRen, LocalDate.of(2021, 10, 1),
            1, false, false);
    private final Hero heroAgathe = new Hero(30L, "Agathe", "Vanguard of the Holy Church", regionSouth, heroClassTenacity, 350, 10,
            17, 100, 2, 150, 2065, 59, 100,
            "Change the range of <Protection of Radiance> to 5x5",
            "Recover 50% of MP at the beginning of a battle",
            "https://drive.google.com/uc?id=1x77r3FBfLBmKEF4C7eTa3lgKkkK2fpen", "https://drive.google.com/uc?id=1dye5zro1CYXwkfqjwBYUMXhNKFWzNvt1",
            "https://drive.google.com/uc?id=1rU86XhQ5Pqcc695fgaYGUvTL3hp0F9nU", skillAgathe, LocalDate.of(2021, 11, 19),
            1, false, false);
    private final Hero heroHela = new Hero(31L, "Hela", "Catasthrophic Fire", regionSouth, heroClassElement, 130, 18, 0,
            33, 4, 150, 767, 106, 0,
            "+50% damage to 1 block at the center of <Summon Meteor>",
            "-0.1 sec concentration time of <Summon Meteor> per 10% Extra Attack Speed (max 1.5 sec)",
            "https://drive.google.com/uc?id=1R-NtdJkRLIlMnDbTaMqsSl_5b8JgPjh7", "https://drive.google.com/uc?id=1HSrXFx3hXTB9WUEGr1GaBA1nXusEfmGX",
            "https://drive.google.com/uc?id=1pl0IyNA64FgyHtej5vk5UXT-dDkIpUE8", skillHela, LocalDate.of(2022, 1, 21),
            1, false, false);
    private final Hero heroZuoBai = new Hero(32L, "Zuo Bai", "Scarlet Sword", regionEast, heroClassSwiftness, 200, 28, 20,
            125, 1, 150, 1180, 165, 118,
            "-15% damage taken for 5 sec when using <Crimson Slash>",
            "After using <Crimson Slash>, the next 3 normal attacks deal extra damage equal to Spell Power",
            "https://drive.google.com/uc?id=1hsWMg4qWEylK44eLPTGnyCgQSlha7uwR", "https://drive.google.com/uc?id=1Rb6OdigIltOltxJaQm4A7TU5-Un_7aKD",
            "https://drive.google.com/uc?id=1QsiwtO0prJZuCfwTkSRuyktexzljOBeb", skillZuoBai, LocalDate.of(2022, 3, 31),
            1, false, false);
    private final Hero heroTia = new Hero(33L, "Tia", "Watcher of Eternity", regionWest, heroClassSwiftness, 160, 0, 20,
            100, 5, 150, 944, 0, 118,
            "+20% damage dealt to enemies within 3 range",
            "Next <Gust> shoots double the amount of arrows when the target is killed",
            "https://drive.google.com/uc?id=1kUAnvdXaXtS7bpR5fDfN0JgfStNh_wn6", "https://drive.google.com/uc?id=1ixb-MX381MGVNPNSJXvgesB_3X3L4xn3",
            "https://drive.google.com/uc?id=1OfTJyyu2hcGwisfi2UMXEzHqxZiKBhrw", skillTia, LocalDate.of(2022, 4, 21),
            1, false, false);
    private final Hero heroMirsyl = new Hero(34L, "Mirsyl", "Druid of the Deep Forest", regionWest, heroClassMystique, 150, 35,
            16, 77, 4, 150, 885, 207, 94,
            "+30% HP Recovery on <Verdant Garden>",
            "Upon normal attack to the target within <Verdant Garden>, exposes the 'weakness' of that target, increasing damage" +
                    " taken by 30% for 5 sec (effect halved for bosses)",
            "https://drive.google.com/uc?id=11A6psVqOfyKJCg1L_mKe4XWC7ISMmslJ", "https://drive.google.com/uc?id=1AjijNsAqKFMnXgxO2D1W0i2HqcRbM0GT",
            "https://drive.google.com/uc?id=1LxLGP1f2-DAWMYpz1sTkcgyNRwNYmXyd", skillMirsyl, LocalDate.of(2022, 5, 12),
            1, false, false);
    private final Hero heroTaesan = new Hero(35L, "Taesan", "Martial King of Mount White Tiger", regionEast, heroClassTenacity, 200,
            20, 30, 83, 1, 150, 1180, 118, 177,
            "After using <Power of Great Tiger>, the next normal attack deals damage to enemies withing 3x3 range in front",
            "After using <Power of Great Tiger>, the next normal attack deals extra damage equal to 20% of current Protection",
            "https://drive.google.com/uc?id=1rbr2J8I8XcNq93zl8w8uBh-FAIWfjgsF", "https://drive.google.com/uc?id=1dLmb4x0toDbedroZV5z8SqZJXBVAsEmh",
            "https://drive.google.com/uc?id=1uoktVI3DkfR61ASFbjD13KVSQxhqj1Ir", skillTaesan, LocalDate.of(2022, 6, 9),
            1, false, false);
    private final Hero heroNeria = new Hero(36L, "Neria", "Hunter of the Red Moon", regionNorth, heroClassShadow, 140, 15,
            16, 125, 4, 150, 826, 89, 94,
            "+1% damage per 1% HP lost by target of <Annihilation Time> (max +60%)",
            "+1% <Annihilation Time> damage per 2% extra Attack Speed (max 50%)",
            "https://drive.google.com/uc?id=10Rpuw7aDKsW35w73myxz9Btm9olsXE1T", "https://drive.google.com/uc?id=1U30OVP_apzHsLFdNAoTHLU97rbF4bw2s",
            "https://drive.google.com/uc?id=1Kdn5RYVMssSnMu2KWNt8xcZmVddqwNUR", skillNeria, LocalDate.of(2022, 7, 7),
            1, false, false);
    private final Hero heroHaerang = new Hero(37L, "Haerang", "Great Wave of the East", regionEast, heroClassElement, 140, 15,
            15, 91, 3, 150, 826, 89, 89,
            "Stun duration time +1.25 sec on the first two concentrated waves of <Wave Catcher>",
            "Recovers 50% of MP at the beginning of a battle",
            "https://drive.google.com/uc?id=1L8Kt2b-P2yY323weN0lR8duedybACPa5", "https://drive.google.com/uc?id=1ex-Mr1TLLj3t4zivFSp0KkebxLk8yDdx",
            "https://drive.google.com/uc?id=1jh08Mpl8QGh1C4kAOcR_fDQYVHuDS7WC", skillHaerang, LocalDate.of(2022, 8, 4),
            1, false, false);
    private final Hero heroGidnil = new Hero(38L, "Gidnil", "Protector of the Creed", regionSouth, heroClassMystique, 250, 0,
            15, 100, 1, 150, 1475, 0, 89,
            "If Spell Block nullifies the spell damage of a skill that completely restricts movement, stuns the user that cast the skill for 1.5 sec",
            "-15% damage taken for 3 sec when using <Holy Protection>",
            "https://drive.google.com/uc?id=1aQCESZZoP3pFHMPEzHlthcfD3Xc4TnpQ", "https://drive.google.com/uc?id=1wXjuGpOjmJLwYjOBu3eFCHP0JMlyxXJC",
            "https://drive.google.com/uc?id=1Quc0p_i9DGs5wagV7S72U4Lezm-SpBJq", skillGidnil, LocalDate.of(2022, 9, 1),
            1, false, false);
    private final Hero heroKanak = new Hero(39L, "Kanak", "Manipulator od Dark Spirits", regionNorth, heroClassMystique, 140, 50,
            10, 125, 3, 150, 826, 295, 59,
            "+50% Attack Speed in the battle round when an enemy with curse stacks is killed (max 3 times)",
            "+8% damage dealt per <Soul Rip> curse stack",
            "https://drive.google.com/uc?id=1CZNnQgkojVBKgEnAFTX8IocxtLtQP6As", "https://drive.google.com/uc?id=1c7MWADX400cgQ6XOyozeOsjtUNrtB1Hk",
            "https://drive.google.com/uc?id=1VEYVU_ZENJrMXIfT5HjBspK2oQ_Oe9nq", skillKanak, LocalDate.of(2022, 9, 29),
            1, false, false);
    private final Hero heroRie = new Hero(40L, "Rie", "Silent Puppeteer", regionSouth, heroClassShadow, 140, 35, 15,
            100, 3, 150, 826, 207, 89,
            "+200% charge damage of the doll that was summoned last",
            "Returns 15 MP when a doll is destroyed",
            "https://drive.google.com/uc?id=1g__F7piL-lAhcf0oHHSJX5cCG3I8caeD", "https://drive.google.com/uc?id=12xEH_FJQ-I8hOF0OyJSnKKdFDaL337T7",
            "https://drive.google.com/uc?id=1VJfGcnbnaySvdz14mkCsYmT5IwVffuzn", skillRie, LocalDate.of(2022, 10, 27),
            1, false, false);
    private final Hero heroMachina = new Hero(41L, "Machina", "Doll of Darkness", regionSouth, heroClassShadow, 14, 35, 4,
            100, 1, 150, 83, 207, 21,
            "+200% charge damage of the doll that was summoned last",
            "Returns 15 MP when a doll is destroyed",
            "https://drive.google.com/uc?id=1VsCX1RDcgwBODYkRh8w4lBvnbHA8_3U8", "https://drive.google.com/uc?id=1M2g5Cxlq-mNsEmDDYuDRSnIONRUfJ4Kx",
            "https://drive.google.com/uc?id=1VJfGcnbnaySvdz14mkCsYmT5IwVffuzn", skillMachina, LocalDate.of(2022, 10, 27),
            1, false, true);
    private final Hero heroNibella = new Hero(42L, "Nibella", "Ferocious Sandstorm", regionNorth, heroClassCourage, 230, 25,
            25, 91, 1, 150, 1357, 148, 148,
            "Doubles MP recovery upon every normal attack for 4 sec at the beginning of the battle",
            "After using <Desert Charge>, deals extra damage equal to 200% of Spell Power on the first normal attack to the next target",
            "https://drive.google.com/uc?id=1ReQwvhTByVMJcqHepQt0GgSqlrIaED-V", "https://drive.google.com/uc?id=15uWfJRkK6R2rYsduM8kAo2jLIpSnqsus",
            "https://drive.google.com/uc?id=1jmCGArkc2i-5jwmqTA_JwQizCsg5ZZhS", skillNibella, LocalDate.of(2022, 11, 24),
            1, false, false);
    private final Hero heroTaebaek = new Hero(43L, "Taebaek", "Challenger of the White Tiger Clan", regionEast, heroClassCourage, 280,
            10, 20, 91, 1, 150, 1652, 59, 118,
            "Limits the range of provoked enemies to 5X5",
            "-50% damage taken from provoked enemies",
            "https://drive.google.com/uc?id=1MS8qErUXERTGcSF4gZAs3n2MKVO7sOXz", "https://drive.google.com/uc?id=1E1H8aoI2P-CvSeNyViriiJAIdQOKtcVs",
            "https://drive.google.com/uc?id=1XKyY8iIjkm0lSkGE1NJvLHx36bzHkecN", skillTaebaek, LocalDate.of(2023, 1, 19),
            1, false, false);
    private final Hero heroCathy = new Hero(44L, "Cathy", "Bounty Hunter", regionCentral, heroClassShadow, 160, 0, 6,
            125, 3, 150, 944, 0, 35,
            "+2 skill attack counts when a target is killed with the skill",
            "+1 <Dancing Bullet> count every 30% of additional Attack Speed (max 5 times, Attack Speed increased from Tier progression is excluded)",
            "https://drive.google.com/uc?id=1UuYeDUwWup_KLHvZ0yNvaFnimxzHATLt", "https://drive.google.com/uc?id=1vQqzcsozwgvfRKJBxskrj-51QrZsz6VO",
            "https://drive.google.com/uc?id=1kCWprmaDRVqnDMLlfDbaiHRgTYqErtmZ", skillCathy, LocalDate.of(2023, 2, 16),
            2, false, false);
    private final Hero heroEsthea = new Hero(45L, "Esthea", "Purifying Flame", regionSouth, heroClassMystique, 150, 0, 30,
            100, 3, 150, 885, 0, 177,
            "Enhances the skill target's next normal attack to push back the enemy by 2 blocks",
            "+35% Attack Speed for reach equipment on Esthea",
            "https://drive.google.com/uc?id=1ZQwTCGiC-xr7TPXSy3Dx0d3Vh9rHIcMi", "https://drive.google.com/uc?id=1NyL845rt0elOxtOPdxsT88K_FKR-OPHu",
            "https://drive.google.com/uc?id=1k--WMsqT_ALkmACC0haHv_F6ddmAPN0y",
            skillEsthea, LocalDate.of(2023, 3, 16), 1, false, false);
    private final Hero heroBaldir = new Hero(46L, "Baldir", "Walker of Eternity", regionWest, heroClassMystique, 150, 12, 20,
            83, 1, 150, 885, 71, 118,
            "Recover 100% of MP at the start of the battle in <Nature's Fury> form",
            "-30 MP cost in <Eternal Wisdom> form",
            "https://drive.google.com/uc?id=1ZTEmLrcI_WBQyW-0be0kFgkGVppaIvHV", "https://drive.google.com/uc?id=12WxpmpcEh2uNy75Ocbw9wlPamHNSfXVi",
            "https://drive.google.com/uc?id=1AYoE0QHj9t2AazUyFBSGyUHb9ZVSZIxm", skillBaldir, LocalDate.of(2023, 4, 13),
            1, false, false);
    private final Hero heroBaldirNatureFuryForm = new Hero(47L, "Baldir Nature's Fury form", "Nature's Fury", regionWest, heroClassMystique, 270,
            12, 20, 83, 1, 150, 1593, 71, 118,
            "Recover 100% of MP at the start of the battle in <Nature's Fury> form",
            "-30 MP cost in <Eternal Wisdom> form",
            "https://drive.google.com/uc?id=1Avyw7QWFX3cfktFPbtT8NM69r7eUdHiF", "https://drive.google.com/uc?id=1VqChHyU_yi2qyPtxTvir5P5zEmL7qzGy",
            "https://drive.google.com/uc?id=1AYoE0QHj9t2AazUyFBSGyUHb9ZVSZIxm",
            skillNaturesFury, LocalDate.of(2023, 4, 13), 1, false, true);
    private final Hero heroBaldirEternalWisdomForm = new Hero(48L, "Baldir Eternal Wisdom form", "Eternal Wisdom", regionWest, heroClassMystique,
            150, 36, 20, 125, 3, 150, 885, 212, 118,
            "Recover 100% of MP at the start of the battle in <Nature's Fury> form",
            "-30 MP cost in <Eternal Wisdom> form",
            "https://drive.google.com/uc?id=1fZergjx7G5RENB-FCJ9oi2p6W39HCEbV", "https://drive.google.com/uc?id=15NtlrNzfstIydAa-xL96M-UehplgQw4-",
            "https://drive.google.com/uc?id=1AYoE0QHj9t2AazUyFBSGyUHb9ZVSZIxm",
            skillEternalWisdom, LocalDate.of(2023, 4, 13), 1, false, true);
    private final Hero heroIan = new Hero(49L, "Ian", "Knight of Inheritance", regionCentral, heroClassCourage, 200, 8, 30,
            67, 3, 150, 1180, 47, 177, "+50% damage to <Stride of Steel> target",
            "-0.1/0.2/0.3/0.5 sec concentration time of <Stride of Steel> according to Tier",
            "https://drive.google.com/uc?id=1aP-c_thpq7eEHHkjeorHZ1WPEYxfgUp1", "https://drive.google.com/uc?id=1fdpipsOqYT_8d5mGNS6Jp8G1x3lB46CG",
            "https://drive.google.com/uc?id=1kkuc5JbeZLwbCh9PF7dRuJucqoP5bUew", skillIan, LocalDate.of(2023, 6, 8),
            1, false, false);
    private final Hero heroOphelia = new Hero(50L, "Ophelia", "Bloodstained Nightmare", regionNorth, heroClassShadow, 250, 15,
            18, 100, 1, 150, 1475, 89, 106,
            "Upon killing the target of <Blood and Soul>, MP is returned in proportion to the remaining skill damage count",
            "When Ophelia's HP is 70% or above, increases her Spell Power by 50% of the Spell Power she reduced",
            "https://drive.google.com/uc?id=1vbypd-DZi0ci7EJ2XECIhnFdDOq9QBwe", "https://drive.google.com/uc?id=16_JmUVGDuWJwZu1U9GZpNjZotyioUagG",
            "https://drive.google.com/uc?id=1eavBAOxFRSpc-Oq3wQwlQhUkjiFDIosp", skillOphelia, LocalDate.of(2023, 7, 6),
            1, false, false);

    private final Hero heroKirdan = new Hero(51L, "Kirdan", "Raging Blade Storm", regionNorth, heroClassTenacity, 230, 18,
            20, 100, 1, 150, 1357, 106, 118,
            "Becomes immune to crowd control while using <Storm Assault>",
            "+70% Movement Speed while using <Storm Assault>",
            "https://drive.google.com/uc?id=1npe6CjItvJ-5JwQg-cmTu2iflRQRfHkd", "https://drive.google.com/uc?id=173unEel_sOXeAuwpXfV92lCrtg3A5iDs",
            "https://drive.google.com/uc?id=14dwykaKfbGTgPxQTkZdxVdy4u0BMZP3d", skillKirdan, LocalDate.of(2023, 8, 3),
            1, false, false);
    /*
    private final Hero hero = new Hero(0L, "name", "title", region, heroClass, 0, 0, 0, 0,
            0, 150, 0, 0, 0, "potential4", "potential8",
            "https://drive.google.com/uc?id=", "https://drive.google.com/uc?id=", "https://drive.google.com/uc?id=",
            skill, LocalDate.of(2023, 10, 19), false, false);
     */



    // Awakenings
    private final Awakening awakeningShelda1 = new Awakening(1L, "Absolute Will","https://drive.google.com/uc?id=1_gCMRP367EuTdCkoQFmEv8YeEn1yZEeE",
            "Obtains 3/5/7/9 Mighty Blocks when using <Iron Will> (can't be stacked)", heroShelda);
    private final Awakening awakeningShelda2 = new Awakening(2L, "Explosive Will","https://drive.google.com/uc?id=1SVIV3FIACjiUcrfXsbhJapT9Rk4ttFiC",
            "Consumes current Protection when using <Iron Will>. Deals damage equal to consumed Protection + 50% of Spell Power to nearby" +
                    " enemies in 5X5 range upon consuming/depleting Protection", heroShelda);
    private final Awakening awakeningDaniel1 = new Awakening(3L, "Divine Bash","https://drive.google.com/uc?id=1txxqVOivuKH9LOCBUQVfqyfeNDYq-Aa-",
            "Deals 3 times more damage to each enemy on the first normal attack, stunning them for 1 sec and recovering 30 MP", heroDaniel);
    private final Awakening awakeningDaniel2 = new Awakening(4L, "Self-Recovery","https://drive.google.com/uc?id=1daDGrYMD_Nz9FoHm4-QkofOQqJk7Zh5i",
            "Recovers 30% HP per second for 3 sec when HP falls below 40% for the first time after the beginning of the battle", heroDaniel);
    private final Awakening awakeningLeonhardt1 = new Awakening(5L, "Barrier","https://drive.google.com/uc?id=1E_yuYx_3L40vD0HJR3AGuHy4nsHx5Pfy",
            "Obtains Protection equal to the total damage for each target of <Smite>", heroLeonhardt);
    private final Awakening awakeningLeonhardt2 = new Awakening(6L, "Break Formation","https://drive.google.com/uc?id=1lKRm5Ll5u2GSRThX12SuUMnU-VHUIhgR",
            "Pushes back <Smite> target by 1/2/3/4 block(s) instead of stun", heroLeonhardt);
    private final Awakening awakeningEvan1 = new Awakening(7L, "Wave Slash", "https://drive.google.com/uc?id=1tXtzrGoxZXbFPMP0cT6EkHOog4odmBib",
            "+20% damage for each enemy hit by <Crescent Slash> (max +100%)", heroEvan);
    private final Awakening awakeningEvan2 = new Awakening(8L, "Unleash Sword Aura","https://drive.google.com/uc?id=1Q_owBdi4rrGukX6Fj2Eo-xctCcUfXPrP",
            "Emits aura with his sword that deals damage equal to 30% of Spell Power to enemies within 3 horizontal blocks " +
                    "on normal attacks", heroEvan);
    private final Awakening awakeningPriya1 = new Awakening(9L, "Intense Cold","https://drive.google.com/uc?id=1A4eCC4ReTea2IZwXxy4xyD2-pq4RB3_D",
            "Creates an icy area in the range of <Blizzard> that deals additional 20% damage per second for 3 sec", heroPriya);
    private final Awakening awakeningPriya2 = new Awakening(10L, "Freezing Armor","https://drive.google.com/uc?id=1ki8W3e2qBKYn1Eb3ziBnAGNJaGwilQBo",
            "Becomes frozen with 40% of HP for 2 sec when taking lethal damage for the first time after the " +
                    "beginning of the battle", heroPriya);
    private final Awakening awakeningAramis1 = new Awakening(11L, "Take Aim","https://drive.google.com/uc?id=1T3MkYHPMgSJGtOWNyhhkbIMV2VbcFD2T",
            "Sets the enemy in front of self as the target of normal/skill attack at the beginning of the battle +40% damage to that target",
            heroAramis);
    private final Awakening awakeningAramis2 = new Awakening(12L, "Quickshot","https://drive.google.com/uc?id=1u7X9hr8uwERT3NiuWE1i35_iUwT_A81x",
            "Uses <Triple Shot> that quickly deals damage equal to 90/100/110/120% of ATK for 3 times instead of <Headshot>", heroAramis);
    private final Awakening awakeningAlberon1 = new Awakening(13L, "Hymn","https://drive.google.com/uc?id=1RL1NH2GYMB36w3tiqEhzS5YXXVvxiWQW",
            "Target ally of <Radiance of Life> deals additional damage equal to 15% of recovery on the next normal attack", heroAlberon);
    private final Awakening awakeningAlberon2 = new Awakening(14L, "Salvation","https://drive.google.com/uc?id=1iXKiTiHGHyUbrL_5ZNnOK7xL-synTYaW",
            "Changes <Radiance of Life> target to the ally with the lowest HP The target has +25% HP Recovery for the skill", heroAlberon);
    private final Awakening awakeningZuoYun1 = new Awakening(15L, "Fury","https://drive.google.com/uc?id=1wwsuxwXPEH6dOMNJQLQqOIT96od6JGKo",
            "+1 attack count for every 3 attacks during <Enrage>", heroZuoYun);
    private final Awakening awakeningZuoYun2 = new Awakening(16L, "Craving","https://drive.google.com/uc?id=1jnTqLioJMwq-w2_76W2wN6aG9ZAcGD2C",
            "Increases Attack HP Drain by 1% for each 1.5% HP lost during <Enrage>", heroZuoYun);
    private final Awakening awakeningDraco1 = new Awakening(17L, "Ignite","https://drive.google.com/uc?id=1zeKGjBUP9nh7BnambAdrBL3MawexJapb",
            "Gradually increases damage by 15% while casting <Flamebreath> (max 235%)", heroDraco);
    private final Awakening awakeningDraco2 = new Awakening(18L, "Concentrated Flame","https://drive.google.com/uc?id=1Rz8iYGnJclnmzldqL4hLA6CAoAmbFpar",
            "-45 MP Cost and +70% Spell Power for <Flamebreath> Total count of <Flamebreath> is reduced to 4", heroDraco);
    private final Awakening awakeningBardrey1 = new Awakening(19L, "Amplify","https://drive.google.com/uc?id=1hnFALPCJc3WvqJB-xlqztVlpJj4fqzwc",
            "+1 Skill Range of <Lilting Melody>", heroBardrey);
    private final Awakening awakeningBardrey2 = new Awakening(20L, "Discord","https://drive.google.com/uc?id=1xwuFknyqx73swx5pG7b18uIyU_edimCr",
            "Upon normal attack to each enemy for the first time, increases MP cost of the target's next skill by 80% (can't be stacked)",
            heroBardrey);
    private final Awakening awakeningMara1 = new Awakening(21L, "Expose Weakness","https://drive.google.com/uc?id=1iXcQawCCo5ydIWmL4689oGHP9YVYJGo3",
            "Exposes the 'weakness' of the <Backstab> target, increasing damage taken by 30% for 10 sec (effect halved for bosses)", heroMara);
    private final Awakening awakeningMara2 = new Awakening(22L, "Phantom Illusion","https://drive.google.com/uc?id=1Q0xD0A98NaL65oxrGejceLY0NXEVK1bZ",
            "Creates an illusion of Mara with 10% of ATK, Spell Power and Max HP when using <Backstab> Mara's Max HP reduces by 20%", heroMara);
    private final Awakening awakeningHansi1 = new Awakening(23L, "Rainstorm","https://drive.google.com/uc?id=1rNNlg-ze4DW4IGTRmgEI3RwCEMbcFaRp",
            "After reaching max stacks of <Rapid Arrows>, use <Rainstorm> that deals 150% of ATK damage withing 2 range for the total number" +
                    " of attack counts.", heroHansi);
    private final Awakening awakeningHansi2 = new Awakening(24L, "Concentrate","https://drive.google.com/uc?id=1of6rcnxsSQjLVbhyspfmO7fyPdGn8cLo",
            "Range -1, +20% ATK per second when Hansi is not moving (max 80%)", heroHansi);
    private final Awakening awakeningLyca1 = new Awakening(25L, "Wild Nature","https://drive.google.com/uc?id=17d8z_pSqNL3RqALMCuOPGCXtYD75DTG_",
            "+8 MP recovery on normal attacks as Lyca and Lycanthrope", heroLyca);
    private final Awakening awakeningLyca2 = new Awakening(26L, "Obsession","https://drive.google.com/uc?id=1Iae14tl_QJQ2XpHtAl9KpYE4c7STT3oI",
            "+40% damage of <Wild Fury>, <Wild Fury> is used to the current attack target starting from the second use after transformation",
            heroLyca);
    private final Awakening awakeningJol1 = new Awakening(27L, "Eternity","https://drive.google.com/uc?id=1jT2DGlwrHa6dAHlnBdqns-S8FRxxPIxX",
            "+1 sec duration time when a target is killed during <Avatar> (max +4 sec)", heroJol);
    private final Awakening awakeningJol2 = new Awakening(28L, "Blink of an Eye","https://drive.google.com/uc?id=18Rz33joLAY2TD-ZabicQu9PzeWM2SuH5",
            "Moves behind the closest enemy within 4 range when using <Avatar>", heroJol);
    private final Awakening awakeningLily1 = new Awakening(29L, "Communing with Nature","https://drive.google.com/uc?id=1Bvu1VK1-hSj_UwGBhsHNpw4orwuY-UFT",
            "+15 MP Recovery for each second of duration time for Lily's <Summon Golem> after Sha-sha is dismissed (max 60)", heroLily);
    private final Awakening awakeningLily2 = new Awakening(30L, "Charge!","https://drive.google.com/uc?id=1a_8g_6BNCgk2tLT8HTd504xCLWZdK5ZB",
            "Upon using <Summon Golem>, charges 3 blocks and deals damage to all enemies within 3x3 range equal to 100% of Spell Power," +
                    " stunning them for 2 sec", heroLily);
    private final Awakening awakeningZupitere1 = new Awakening(31L, "Infinite Diffusion","https://drive.google.com/uc?id=1uEf9SJa06alkjcywFAYSSaj63coHcxXc",
            "Doubles the target of <Crackle> on every third cast. Can be cast again to the attacked target", heroZupitere);
    private final Awakening awakeningZupitere2 = new Awakening(32L, "Overload","https://drive.google.com/uc?id=1VFUeWI55qWM00vUQLR7gw5qzkqxX766p",
            "+2 Range, +80% Spell Power, +15 MP Cost for <Crackle>", heroZupitere);
    private final Awakening awakeningAsiaq1 = new Awakening(33L, "Cursed Link","https://drive.google.com/uc?id=19e02wrbrFq7Lj8L7uNHueXcOO2mL5j4O",
            "+10% of max shared damage dealt as spell damage to all linked targets of <Soul Link> when they take damage", heroAsiaq);
    private final Awakening awakeningAsiaq2 = new Awakening(34L, "Another Link","https://drive.google.com/uc?id=1TSc9zEudTp1w--qoUzMYKGRZIGpO5x1u",
            "Additionally links the nearest enemy when using <Soul Link>", heroAsiaq);
    private final Awakening awakeningBehemus1 = new Awakening(35L, "Empire's Provocation","https://drive.google.com/uc?id=17H1rLdqbmEUheV4BxpkMwupAcObHkVk1",
            "Provokes the enemy attacking the target ally of <Reckless Protection>. -35% damage taken for 4 sec", heroBehemus);
    private final Awakening awakeningBehemus2 = new Awakening(36L, "Powerful Tremor","https://drive.google.com/uc?id=1whHUCQX5eeV-6x8d7_KcdrEuz0chPgDZ",
            "The damage range of <Reckless Protection> is fixed to be within 2 range for all Tiers. Push back range becomes 2/3/4/5 blocks" +
                    " depending on the skill tier.", heroBehemus);
    private final Awakening awakeningYeon1 = new Awakening(37L, "Overflow","https://drive.google.com/uc?id=1VnG-eBU9tAHnT2LbPCiBXvJH9RrzLwNr",
            "Additional hit happens equal to 40% of Spell Power to 5X5 range of the last target hit by <Calm and Stormy>", heroYeon);
    private final Awakening awakeningYeon2 = new Awakening(38L, "Backflow","https://drive.google.com/uc?id=1TnsG0VVnL_oJsmNcRqbOaNQq8AwYk0xj",
            "An additional wave hits another enemy 1 time when <Calm and Stormy> hits an enemy. -25% damage and HP Recovery for all waves", heroYeon);
    private final Awakening awakeningMel1 = new Awakening(39L, "Wave of the Holy Blade","https://drive.google.com/uc?id=1I5UxvTL440OpuMQHWQVmiYhMekGRHUd8",
            "Activates shockwave on attacks, which deals damage equal to the Spell Power with a 20% chance during <Holy Blade Garna>", heroMel);
    private final Awakening awakeningMel2 = new Awakening(40L, "Unleash","https://drive.google.com/uc?id=1O1Nsco0abU79bAOqK4I_2BcNcAaGi9Av",
            "Removes shockwave when <Holy Blade Garna> is cast. +2.5 sec skill duration", heroMel);
    private final Awakening awakeningBombie1 = new Awakening(41L, "Mega Bomb","https://drive.google.com/uc?id=1a9qIWNh0NQOXrTG_2Bj02FYOiOhXj74p",
            "Upon every 10th normal attack, throws <Mega Bomb> that deals triple damage in 5x5 range", heroBombie);
    private final Awakening awakeningBombie2 = new Awakening(42L, "Bang Snaps","https://drive.google.com/uc?id=1i8P8txgLk4wLFe338TLbArAA26j_LGnI",
            "Throws bombs to two enemies during <Rain of Bombs>. -45% bomb damage", heroBombie);
    private final Awakening awakeningLuniare1 = new Awakening(43L, "Swift Blessing","https://drive.google.com/uc?id=1JM_B1m8Ov2O_ed6ak6LaZJNTm5fa59Pr",
            "Luniare recovers 100% MP if the target is behind Luniare at the beginning of the battle +20% Movement Speed on the target of" +
                    " <Blessing of the Blue Moon> (50%efficiency when applied to the same hero)", heroLuniare);
    private final Awakening awakeningLuniare2 = new Awakening(44L, "Protection of the Moon","https://drive.google.com/uc?id=1t4gtvT7cZuRI9TU_VzHIpjCYqXjbGBhr",
            "No longer grants Protection when <Blessing of the Blue Moon> is cast. +2 Mighty Blocks to the target" +
                    " of <Blessing of the Blue Moon>", heroLuniare);
    private final Awakening awakeningRossette1 = new Awakening(45L, "Expose Weakness","https://drive.google.com/uc?id=1gwnhZZNMe3YjLGcQUbWMX-USO15bGu5f",
            "Exposes the 'weakness' of the target of <Giant Smite>, increasing damage taken by 30% for 10 sec (effect halved for bosses)",
            heroRossette);
    private final Awakening awakeningRossette2 = new Awakening(46L, "Bloody Execution","https://drive.google.com/uc?id=1NKYLDa5ac3btz3zltDdP4fkrtAI2f7f4",
            "Recovers full HP when a target is killed with <Giant Smite>", heroRossette);
    private final Awakening awakeningCain1 = new Awakening(47L, "Extort","https://drive.google.com/uc?id=1cZUxYhyhjwJ_E5IHzh7PVZbZN37VcJVM",
            "Obntains Tier 1 equipment instead of Silver with a 50% chance when a target is killed with <Circus of Death>", heroCain);
    private final Awakening awakeningCain2 = new Awakening(48L, "Solo Dance","https://drive.google.com/uc?id=1TU-Xbm26myKuGKMTAhkMId7TZiUJZAMJ",
            "Instantly uses <Circus of Death> again with 80% damage when a target is killed with <Circus of Death>", heroCain);
    private final Awakening awakeningChungAh1 = new Awakening(49L, "awk3","https://drive.google.com/uc?id=1Y258kC_fmyXXbnJWHRQ8byhkR3MmiSts",
            "Uses <Azure Dragon Slash> instantly when a target is killed with <Horizontal Dragon Edge>. The instant " +
                    "<Azure Dragon Slash> deals 40% additional damage.", heroChungAh);
    private final Awakening awakeningChungAh2 = new Awakening(50L, "Advent of Thunder Dragon","https://drive.google.com/uc?id=1I6ls0FupQvMfJSBv16C71EWhxl4LP-kx",
            "Instead of using <Azure Dragon Slash> on the third time, uses <Advent of Thunder Dragon> that ignores Mighty/Spell Block" +
                    " and deals 10 times the damage of Horizontal Dragon Edge to a single target", heroChungAh);
    private final Awakening awakeningRahawk1 = new Awakening(51L, "Double Hunt","https://drive.google.com/uc?id=1XE4tYpCg9uNjOZLLV-SoC93tY_v-Ikny",
            "When <Hunt Command> is used, cast <Hunt Command> once again, dealing 60% of its damage to an extra target", heroRahawk);
    private final Awakening awakeningRahawk2 = new Awakening(52L, "Rigid Claws","https://drive.google.com/uc?id=1Iuy5FCRodjTowfKSG9gqRIXYy-gqn9jk",
            "Removes the reduction effect of <Hunt Command> +70% damage of <Hunt Command>", heroRahawk);
    private final Awakening awakeningRen1 = new Awakening(53L, "Full Blossom","https://drive.google.com/uc?id=1OkBCI9H2aq0W5O2ZHy_-PX2wzGCwEgfl",
            "+2 stacks when a target is killed with <Full Bloom> Additionally attacks using excess stacks when using <Full Bloom>", heroRen);
    private final Awakening awakeningRen2 = new Awakening(54L, "Sanguine Flowers","https://drive.google.com/uc?id=1cq6062Mf_HXJSol1iO7r7JPtwQSQAGAh",
            "Upon reaching max <Full Bloom> stacks, consumes 2 stacks and uses <Sanguine Flowers> that deals 6 times of Spell Power" +
                    " as damage to all enemies", heroRen);
    private final Awakening awakeningAgathe1 = new Awakening(55L, "Sacrifice","https://drive.google.com/uc?id=13-IUDYnU7TWgX9h6BwP63WsDo0FqguLF",
            "Recovers HP of all allies withing 5x5 range equal to 20% of Agathe's Max HP range when she is killed", heroAgathe);
    private final Awakening awakeningAgathe2 = new Awakening(56L, "Retribution","https://drive.google.com/uc?id=1G4tkjJRHScAn5ZELg7VpMqNIqZpoQCxs",
            "Damage take and damage reduction values of <Protection of Radiance> are fixed to 40% and 10% respectively. " +
                    "When <Protection of Radiance> ends, the next normal attack deals extra 150/200/250/300% damage of the" +
                    " total damage taken for allies", heroAgathe);
    private final Awakening awakeningHela1 = new Awakening(57L, "Supernova","https://drive.google.com/uc?id=1cKAWyyTdZQApalqGs3nzuwa7yGtsmXZJ",
            "Changes one random meteor from <Summon Meteor> to <Supernova> that deals 200% damage to enemies within 3 range", heroHela);
    private final Awakening awakeningHela2 = new Awakening(58L, "Focus Fire","https://drive.google.com/uc?id=1tjiTK5-Snc38zhjC6QoO9auI0w07Jkga",
            "+50% meteor damage. Removes the area damage of meteors. The target of meteors is fixed to the nearest single enemy.", heroHela);
    private final Awakening awakeningZuoBai1 = new Awakening(59L, "Consecutive Slashes","https://drive.google.com/uc?id=1gzC39RIqq4nL9_M6k1m8VyfBhXykM1Ry",
            "Instantly increases <Crimson Slash> attack count by 1 when a target is killed with <Crimson Slash> (max 3)", heroZuoBai);
    private final Awakening awakeningZuoBai2 = new Awakening(60L, "Advanced Crimson Slash","https://drive.google.com/uc?id=1pvS7RocBzNF4aNhX6pJMqjI_dCgK2VTT",
            "<Crimson Slash> deals extra damage equal to 80% of ATK>", heroZuoBai);
    private final Awakening awakeningTia1 = new Awakening(61L, "Avoid Risk","https://drive.google.com/uc?id=1-UIEksc-tWSlXnw-2R1m9vBQGBczv0xd",
            "Recovers 100% MP instantly when receiving damage from an enemy within 2 range for the first time after the" +
                    " beginning of the battle", heroTia);
    private final Awakening awakeningTia2 = new Awakening(62L, "Breeze","https://drive.google.com/uc?id=1c8w27K3CLbTihYz-VWU5nboz7khojaZ7",
            "If there is no enemy within 3 range when Tia has max MP, +120/140/160/180% ATK instead of using <Gust>.", heroTia);
    private final Awakening awakeningMirsyl1 = new Awakening(63L, "Dew of Purification","https://drive.google.com/uc?id=1FYGRYIjirciLKRnJtAHqq-uywo9L6BLM",
            "Removes crowd control on allies within range and grants them crowd control immunity for 1 sec when using <Verdant Garden>", heroMirsyl);
    private final Awakening awakeningMirsyl2 = new Awakening(64L, "Early Spring","https://drive.google.com/uc?id=1DXGsLtRofYhffmkGa1Y-1oBKOD4k50cA",
            "Deals damage equal to 35% of Mirsyl's Spell Power to enemies and heals allies by the same amount within range when using" +
                    " <Verdant Garden>", heroMirsyl);
    private final Awakening awakeningTaesan1 = new Awakening(65L, "Tiger Striker","https://drive.google.com/uc?id=1oZavo-tDKTwAtTGq6xf3zdarRCC2VuKi",
            "If there is only one target within the range when using the skill, Taesan uses <Tiger Strike> that deals 200% damage of" +
                    " <Power of Great Tiger> and stuns the target for 1/2/3/4 sec", heroTaesan);
    private final Awakening awakeningTaesan2 = new Awakening(66L, "Tenacity","https://drive.google.com/uc?id=1VVMZl5tuJxX25FkckAEK9mY_OlWcXtV0",
            "Instantly uses <Power of Great Tiger> when Taesan's HP falls below 50% for the first time after the beginning" +
                    " of the battle", heroTaesan);
    private final Awakening awakeningNeria1 = new Awakening(67L, "Everlasting Night","https://drive.google.com/uc?id=1C0pp_XZjjniMqjfLKG7o20HeO9rmsytP",
            "+1 attack count of <Annihilation Time> that is being cast when a target is killed by the skill (max 3)", heroNeria);
    private final Awakening awakeningNeria2 = new Awakening(68L, "Shroud of Night","https://drive.google.com/uc?id=1DPg0vRKwSmkz4eRDugTIDtkS-s_MVjen",
            "Hides for 1.5 sec when using <Annihilation Time>, becoming untargetable to attacks for the duration", heroNeria);
    private final Awakening awakeningHaerang1 = new Awakening(69L, "High Tide","https://drive.google.com/uc?id=1n3jTB6-AkT5onK3N75Bqh9siw0SCMr2N",
            "The waves ripple to the left and right of the target of <Wave Catcher>, dealing damage to the enemies hit and stunning them." +
                    " The damage and the stun duration are 30% of the original skill.", heroHaerang);
    private final Awakening awakeningHaerang2 = new Awakening(70L, "Frozen Sea","https://drive.google.com/uc?id=1P8yAZzDMnNMQ-Wsls3xN58Mt1jKe2HX0",
            "The first 2 concentrated waves freeze the enemy for 2 sec instead of stunning them when using <Wave Catcher> (" +
                    "Max 2 freeze effects inflicted with Wave Catcher in a single battle)", heroHaerang);
    private final Awakening awakeningGidnil1 = new Awakening(71L, "Indomitable","https://drive.google.com/uc?id=1gQZKMWQxH0mtqNtu9Wj5S_B5oc_eJM0P",
            "-50% skill damage taken for 3 sec when all Spell Blocks are destroyed", heroGidnil);
    private final Awakening awakeningGidnil2 = new Awakening(72L, "Holy Expansion","https://drive.google.com/uc?id=1X-mx0UjSgrOv8y26OdbiJiAtM20XkyxB",
            "Gidnil can only receive half of the Spell Blocks when using <Holy Protection> The other half of Spell Blocks are granted " +
                    "to all ally heroes within 7x7 range near self, starting with the nearest unit", heroGidnil);
    private final Awakening awakeningKanak1 = new Awakening(73L, "Remnant of Spirits","https://drive.google.com/uc?id=1LEXRq-uAHXApcFw5SSJa1kf1rLJIVR-6",
            "When an enemy is killed, grants 40% of curse stacks on the enemy to Kanak's next attack target", heroKanak);
    private final Awakening awakeningKanak2 = new Awakening(74L, "Spirit's Curse","https://drive.google.com/uc?id=1NbYqpNCTyDQ0PL0OVoSow2yrUT9M7ZOf",
            "Reduces damage by 4% for every curse stack on the target (effect halved for bosses)", heroKanak);
    private final Awakening awakeningRie1 = new Awakening(75L, "Order: Sacrifice","https://drive.google.com/uc?id=105VQczq-adBmDFYDVLJ_eRkXnJkifW2I",
            "Deals damage equal to 100% Spell Power to all enemies within 3X3 range when a doll is destroyed", heroRie);
    private final Awakening awakeningRie2 = new Awakening(76L, "Command: Modification","https://drive.google.com/uc?id=1XAYnwC4QdSa83mQqH8JiGsD8NiBoKLp7",
            "+200% normal attack damage and -40% charge damage for dolls. Grants 2 Mighty Blocks to dolls upon summon", heroRie);
    private final Awakening awakeningNibella1 = new Awakening(77L, "Sandsword Gale","https://drive.google.com/uc?id=18oTJacpGYqw40pDYZlsHHoxuSY25-qPF",
            "After using <Desert Charge>, the next 3 normal attacks generate <Sandsword Gale>, which deals extra damage equal to 100% of" +
                    " ATK within 5x5 range", heroNibella);
    private final Awakening awakeningNibella2 = new Awakening(78L, "Recurrence","https://drive.google.com/uc?id=1vPoSOGCd86VzZ8LGZZFCZuGev1I20Xnb",
            "Casts <Desert Charge> 2 times consecutively -40% damage dealt by <Desert Charge>", heroNibella);
    private final Awakening awakeningTaebaek1 = new Awakening(79L, "Lightning Kick","https://drive.google.com/uc?id=19HAcymdlP2cgm5ge9mb4AfzXOPEKbVJ_",
            "When <White Tiger Kick> ends, emits a shockwave of 3 horizontal blocks in 4 directions. Deals damage equal to Spell Power to " +
                    "enemies hit and reduces their Attack Speed and Movement Speed by 20% for 3 sec.", heroTaebaek);
    private final Awakening awakeningTaebaek2 = new Awakening(80L, "No Retreat","https://drive.google.com/uc?id=1f56WoXqSKqx-sp1ttD5TityfGN0JLRCj",
            "When taking lethal damage while using <White Tiger Kick>, becomes invincible until the skill is finished. (HP Recovery and" +
                    " Protection unavailable during invincibility). -1 sec <White Tiger Kick> duration", heroTaebaek);
    private final Awakening awakeningCathy1 = new Awakening(81L, "Finale","https://drive.google.com/uc?id=1M37Ew5m59xdQxwKgvvo13sFtFE118Yvq",
            "Fires a powerful bullet at the final hit of <Dancing Bullet> that deals damage equal to 500/650/800/1000% of ATK.", heroCathy);
    private final Awakening awakeningCathy2 = new Awakening(82L, "Strafe","https://drive.google.com/uc?id=1J5NW93BQiNELTzd5B2QPbCjSUYKEtYEK",
            "Changes the ricochet target of <Dancing Bullet> to a random enemy. Ricochet deals 120% damage instead of 70%", heroCathy);
    private final Awakening awakeningEsthea1 = new Awakening(83L, "Spreading Flame","https://drive.google.com/uc?id=1_WqsUPxpRUhPsHWUEgMsom87M_mMe7B8",
            "Esthea casts <Sacred Censer> on herself upon every third <Sacred Censer>", heroEsthea);
    private final Awakening awakeningEsthea2 = new Awakening(84L, "Torch of Embrace","https://drive.google.com/uc?id=1aoDtbT7-5VCI9trVjqE3vzJ8JmVRkkBf",
            "Removes the next normal attack enhancement effect of <Sacred Censer> and grants Protection equal to 96/114/132/150% of ATK to the" +
                    " skill target. -15 MP cost for <Sacred Censer>", heroEsthea);
    private final Awakening awakeningBaldir1 = new Awakening(85L, "Echo of the Forest","https://drive.google.com/uc?id=1v-apFboL48b57ftse4vLgGGDG-rYrCAq",
            "The skill of <Eternal Wisdom> form generates a mark on the target. When another Baldir in <Nature's Fury> form attacks a marked enemy" +
                    " with a normal attack, deals damage equal to 75% of Baldir's Spell Power in <Eternal Wisdom> form and recovers 25% HP of Baldir in" +
                    " <Nature's Fury> form", heroBaldir);
    private final Awakening awakeningBaldir2 = new Awakening(86L, "Solitude of the Forest","https://drive.google.com/uc?id=1_cXc5Nl68LAtR8Uw3vSXQOlhplS5I2RC",
            "If only 1 Baldir is in the ally troop during battle, becomes immune to crowd control for 2 sec upon dealing spell damage and" +
                    " obtains Protection equal to 40% of Max HP upon using skill", heroBaldir);
    private final Awakening awakeningIan1 = new Awakening(87L, "Fatal Blow","https://drive.google.com/uc?id=1868KPDSuJuUB6vwqB2mPKRgN_7g_1KA1",
            "Attack range increases to 5x5 and damage increases by 30% every time <Stride of Steel> designates a new target", heroIan);
    //private final Awakening awakeningIan2 = new Awakening(0L, "awk3","https://drive.google.com/uc?id=", "awk3description", heroIan);
    private final Awakening awakeningOphelia1 = new Awakening(88L, "Bloodstained Land","https://drive.google.com/uc?id=17xz_x8DrBmtQjCKnYtOEtuEy2AukHvTL",
            "For every attack with <Blood and Soul>, creates 'Bloodstained Land' that deals damage equal to 20% of Spell Power withing 5x5 range" +
                    " of Ophelia and recovers HP equal to the damage dealt.", heroOphelia);
    private final Awakening awakeningKirdan1 = new Awakening(89L, "Eye of the Storm", "https://drive.google.com/uc?id=1agkiSpmfK65EJnvmeuz2h4-U9P0CkwL4",
            "-50% damage taken and +50% HP Drain (Spell damage) only when HP is at 30% or below", heroKirdan);
    private final Awakening awakeningIan2 = new Awakening(90L, "Confrontation", "https://drive.google.com/uc?id=188CTar2dhvViueneGfXxsTWzCt6K2AqD",
            "Removes the area damage of <Stride of Steel> While casting <Stride of Steel> for a single time, gradually increases damage by " +
                    "10% for each hit", heroIan);
    //private final Awakening awakeningOphelia2 = new Awakening(0L, "awk3","https://drive.google.com/uc?id=", "awk3description", heroOphelia);

    //private final Awakening awk3 = new Awakening(0L, "awk3","https://drive.google.com/uc?id=", "awk3description", hero);



    // Skins
    private final Skin skinShelda1 = new Skin(1L, "Shelda the Hero",
            "https://drive.google.com/uc?id=1QoVj_jgvoWuQ0WLzb9-BtSm7w3orBO9m",
            "https://drive.google.com/uc?id=1ang5UWnLnRkasdn6mevhUWXYAT04Uk30",
            "https://drive.google.com/uc?id=1U47I5LS_30Rlm6e8wVyYPU2lsGJdbnWt",
            "Shelda, who led countless battles to victory, is now called a Celestial Guardian. For her followers, her shield is a symbol" +
                    " of invincibility",
            "Foundation Day Festival", heroShelda,
            true, true, true, false, false, 139, 28);
    private final Skin skinShelda2 = new Skin(2L, "Angel Shelda",
            "https://drive.google.com/uc?id=1bEtjkM-x4MmiwO5MHA5fLBeZ8IvfECKm",
            "",
            "",
            "2022 Divine Festival Skin",
            "Divine Festival", heroShelda,
            false, true, true, false, false, 119,24);
    private final Skin skinShelda3 = new Skin(3L, "Knight Queen Shelda",
            "https://drive.google.com/uc?id=1nCWmLggrTvVJEGs2_jfut-G3BgmkNC7z",
            "https://drive.google.com/uc?id=1Q4ZcHhTwIsPlFaY_PmEOtQAnMpwk6rkU",
            "https://drive.google.com/uc?id=1gMze5JCGYCHne68GWasqy5r9gq7W8tph",
            "Fear not, for I shall have your back! Advance, for I shall open the way! Embrace the light, for I shall be with you!",
            "King God Pass", heroShelda,
            true, true, true, true, true, -1, -1);
    private final Skin skinShelda4 = new Skin(4L, "Fishing Festival Shelda",
            "https://drive.google.com/uc?id=13oMmlhEhRtHGwnvbL_GCo5nlWZaTVYtE",
            "",
            "",
            "Shelda expected to win with a 'sizable' lobster she caught at the fishing festival held in the coastal village during summer." +
                    " But when she came to the stage, a giant shark and huge crab left her speechless.",
            "Wave Festival", heroShelda,
            false, true, true, false, false, 119, 24);
    private final Skin skinShelda5 = new Skin(5L, "Royal Guard Shelda",
            "https://drive.google.com/uc?id=1iI6sJnP5uiDIXrwRx2Z5yw5DXMOGFtxi",
            "",
            "",
            "Once a Royal Guard, Shelda is now known as the Shield of Knights. But many still remember the days when she was in the guard.",
            "Event", heroShelda,
            false, false, false, false, false, 99, 20);
    private final Skin skinDaniel1 = new Skin(6L, "Gravekeeper Daniel",
            "https://drive.google.com/uc?id=1sxzxjJ1MC_XtyRKDbYVJoSwZEogN211l",
            "",
            "",
            "2021 Halloween Event Skin",
            "Halloween Festival", heroDaniel,
            false, true, true, false, false, 119, 24);
    private final Skin skinDaniel2 = new Skin(7L, "Paladin King Daniel",
            "https://drive.google.com/uc?id=1Ok4Qu7nGehKX3lBezf8eIP-P575qbFGu",
            "https://drive.google.com/uc?id=1gagNN2jNiP-O2xFSorTxr3Kkf2Z0lw2I",
            "https://drive.google.com/uc?id=1f5VLgOTAGjdwpcCuDt_-87oddCa0HnV2",
            "skindescription",
            "King God Pass", heroDaniel,
            true, true, true, true, true, -1, -1);
    private final Skin skinDaniel3 = new Skin(8L, "Heretic Punisher Daniel",
            "https://drive.google.com/uc?id=1keJDBWdoJv2DyL_lq47fdij_E8PO8ABX",
            "",
            "",
            "Daniel became the smiting hammer for the enemies of the Holy Church. His set of cold steel armor matches his stern face, and" +
                    " he shows no mercy to those who violate the church's creed",
            "Divine Festival", heroDaniel,
            false, true, false, false, false, 119, 24);
    private final Skin skinDaniel4 = new Skin(9L, "Blacksmith Daniel",
            "https://drive.google.com/uc?id=1VJMYXdAyp-rjJ3bVBSjKUpJfI3UcMLbm",
            "",
            "",
            "Daniel, a blacksmith blessed by the Most High, wields a hammer embracing the light. His hammer is fearsome for his enemies during" +
                    " the battle; however, it is reliable to his fellows' weapons and armor after the battle, giving ally troops endless reassurance.",
            "", heroDaniel,
            false, true, false, false, false, 119, 24);
    private final Skin skinDaniel5 = new Skin(10L, "White Rose Daniel",
            "https://drive.google.com/uc?id=1lHonZZtW9273bKMvwvxHpw3dwDULglSG",
            "",
            "",
            "2022 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroDaniel,
            false, true, false, false, false, 119, 24);
    private final Skin skinLeonhardt1 = new Skin(11L, "Fishing Festival Leonhardt",
            "https://drive.google.com/uc?id=1HOqkKfLC-rUkbLbaxcVJzaW3Rt3LVUTC",
            "",
            "",
            "Leonhardt participates in the fishing festival every year and always makes a bet with Rossette, his subordinate in the Imperial elite" +
                    " troop, to see who catches a bigger fish. In the last festival he attended, he surprised everyone with a huge crab that nobody" +
                    " had ever seen before.",
            "Wave Festival", heroLeonhardt,
            false, true, true, false, false, 119, 24);
    private final Skin skinLeonhardt2 = new Skin(12L, "Lionheart Leonhardt",
            "https://drive.google.com/uc?id=1juBjEvVjsixOvHWV8GdrFuDnu5lUzhDA",
            "https://drive.google.com/uc?id=1jLh563zfkHhgkvfJilqhm39PyDtMrop0",
            "https://drive.google.com/uc?id=1VjFtSZOQk9njgv8_kWA5sDNoaPGUB6bf",
            "Wearing an impenetrable mane of gold he lets out a ferocious roar that makes the heaven and earth tremble." +
                    " O Leonhardt, the almighty lionheart",
            "King God Pass", heroLeonhardt,
            true, true, true, true, true, -1, -1);
    private final Skin skinLeonhardt3 = new Skin(13L, "Leonhardt the Shield of Victory",
            "https://drive.google.com/uc?id=1w4E7gNoAtRgq34qqZ3hBuj5pQYlXIeWm",
            "",
            "",
            "Leonhardt, who was called the Lion Heart, overwhelmes the enemy with his unbreakable spirit, no matter how many enemis" +
                    " there are or how brutal the battle might be",
            "Foundation Day Festival", heroLeonhardt,
            false, false, false, false, false, 99, 20);
    private final Skin skinLeonhardt4 = new Skin(14L, "Santa Leonhardt",
            "https://drive.google.com/uc?id=1K0ssyvskbET9yIcZ9X706CIyncVf5bkI",
            "",
            "",
            "2021 Holiday Skin",
            "Holiday Event", heroLeonhardt,
            false, true, true, true, true, -1, -1);
    private final Skin skinLeonhardt5 = new Skin(15L, "Imperial Guard Captain Leonhardt",
            "https://drive.google.com/uc?id=1FI95igpaMWYFCXIm3jDRRmJmE6iAW0HQ",
            "",
            "",
            "2023 Foundation Day Festival Skin",
            "Foundation Day Festival", heroLeonhardt,
            false, true, true, true, false, 139, 28);
    private final Skin skinEvan1 = new Skin(16L, "Blood Moon Evan",
            "https://drive.google.com/uc?id=1D-7a2wUMa3CupVS4J8SGaD-xn7B-qZim",
            "",
            "",
            "Evan is obsessed with bloodthirst and looking for his new victim to wield his bloody sword, leaving" +
                    " justice and honor behind. His sword radiates a bloody red aura everywhere in hunger",
            "Halloween Festival", heroEvan,
            false, true, true, false, false, 139, 28);
    private final Skin skinEvan2 = new Skin(17L, "King Evan",
            "https://drive.google.com/uc?id=1LfvXpJv8Tp_fYxoHvbVDtFhx2wVYlRdK",
            "https://drive.google.com/uc?id=1WA4bnOlPpTyRppvuqfyiYtmmSNpfz5kM",
            "https://drive.google.com/uc?id=1o3ImFfOZW-V-sEnYfcTr1MOiF5Wm7uUR",
            "The days of chaos are over, my subjects. I command thee: protect all beings of this land and enjoy what it offers",
            "King God Pass", heroEvan,
            true, true, true, true, true, -1, -1);
    private final Skin skinEvan3 = new Skin(18L, "Dark Knight Evan",
            "https://drive.google.com/uc?id=1GmPmZCxh7EzqZbRYLuvV0yBp9gu8mhJn",
            "https://drive.google.com/uc?id=1_nmF7MShtYUcY9PzWn4jjNWrbl8_TJ1M",
            "https://drive.google.com/uc?id=13LxE4lKTbB1TAT3lxpyqRKcBymhF6Bph",
            "Not letting anyone pass here -that is my purpose",
            "Eldor Festival", heroEvan,
            true, true, true, true, true, -1, -1);
    private final Skin skinEvan4 = new Skin(19L, "Evan the Blade of the Empire",
            "https://drive.google.com/uc?id=1WTDf8py6CohWMd3KeesSeq1wBR4Ucsjc",
            "",
            "",
            "Evan's reputation reached the Emperor's ears and he was then made an Imperial Knight. Now he is the Blade of the Empire that defeats" +
                    " anyone who stands in his way",
            "Foundation Day Festival", heroEvan,
            false, true, false, false, false, 99, 20);
    private final Skin skinEvan5 = new Skin(20L, "Lunar New Year Greetings Evan",
            "https://drive.google.com/uc?id=1rQ0V9SiydqtcJaUzEXtUmeCEzEKYRxX4",
            "",
            "",
            "2022 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroEvan,
            false, true, true, false, false, 119, 24);

    private final Skin skinPriya1 = new Skin(21L, "Priya the Archmage",
            "https://drive.google.com/uc?id=1srWId99rksXfHVwuWAI99t-HsEOcjlL9",
            "",
            "",
            "After spending almost a lifetime to understand the truth of mana, there is no one else more deserving of the title of" +
                    " Archmage than Priya",
            "", heroPriya,
            false, false, false, false, false, 99, 20);
    private final Skin skinPriya2 = new Skin(22L, "Frost Queen Priya",
            "https://drive.google.com/uc?id=1XmyFQJbx6yunSBMWmtJmSWP21Rd7vAI4",
            "https://drive.google.com/uc?id=1QGKqfClxHzVqyfLHKN8_4eghVU7aqkP-",
            "https://drive.google.com/uc?id=1T3UxDL50T4sQ4w15R9xcQBXkum7e49eT",
            "The sharp ice spear penetrates enemies and a swirling frost storm stops the whole world",
            "King God Pass", heroPriya,
            true, true, true, true, true, -1, -1);
    private final Skin skinPriya3 = new Skin(23L, "Black Witch Priya",
            "https://drive.google.com/uc?id=1PotsvWzOjaOxaT-SLgKFrJom0986r2KE",
            "",
            "",
            "2021 Halloween Event Skin",
            "Halloween Festival", heroPriya,
            false, true, true, false, false, 119, 24);
    private final Skin skinPriya4 = new Skin(24L, "Snowball Fight Priya",
            "https://drive.google.com/uc?id=1zj4ZxGn0hOLbt1SrfPWZ2KQaR--WA6z8",
            "",
            "",
            "2021 Holiday Skin",
            "Holiday Event", heroPriya,
            false, true, true, true, true, -1, -1);
    private final Skin skinPriya5 = new Skin(25L, "Lunar New Year Greetings Priya",
            "https://drive.google.com/uc?id=1nWmbVOo13kPzy0cQRoCffp0l_bhK6MtY",
            "",
            "",
            "2022 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroPriya,
            false, true, false, false, false, 119, 24);
    private final Skin skinPriya6 = new Skin(26L, "Priya the Queen of Eldor",
            "https://drive.google.com/uc?id=1wmA2476_wetuObnTP3c5W3KpVzBtY4vP",
            "https://drive.google.com/uc?id=1zP4F0foCwYaSSxqu4Hwzy0adb84Oc2uK",
            "https://drive.google.com/uc?id=1gwLNZSV21r9CJOuYtQo2Z2UV6-XikW0r",
            "As the Queen of Eldor, I officially appoint you as the Warrior of Eldor!",
            "Eldor Festival", heroPriya,
            true, true, true, true, true, -1, -1);
    private final Skin skinPriya7 = new Skin(27L, "Priya in Wonderland",
            "https://drive.google.com/uc?id=1xcNBilp-Zs7LCLkiAK5WnvkSZahissAi",
            "",
            "",
            "2022 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroPriya,
            false, true, true, true, false, 119, 24);
    private final Skin skinAramis1 = new Skin(28L, "Aramis of the Resistance",
            "https://drive.google.com/uc?id=1Dzv1YhB90OHu4c7DGVZ09JmsPktGN5Ju",
            "",
            "",
            "Since joining the resistance against the Empire, Aramis has become the worst nightmare to the Empire's forces",
            "Foundation Day Festival", heroAramis,
            false, true, false, false, false, 99, 20);
    private final Skin skinAramis2 = new Skin(29L, "Aramis the Musketeer of the Empire",
            "https://drive.google.com/uc?id=1fVKMbDnzLgesqQXTvhymyea5pDPhrZIy",
            "",
            "",
            "Aramis has a history of infiltrating the Imperial Army during his days in the resistance. He still never missed any shots, " +
                    "but they were often not fatal",
            "Foundation Day Festival", heroAramis,
            false, true, true, false, false, 119, 24);
    private final Skin skinAramis3 = new Skin(30L, "Frozen Land Hunter Aramis",
            "https://drive.google.com/uc?id=12UyeZq-gYQW7FhZ9Z5P-eOf1Jz-dGzby",
            "",
            "",
            "Huntingis Aramis' longtime hobby. When he heard that a bear appeared in a snowy mountain that chopped down every tree and bear in it's" +
                    " way with it's giant axe, he came over to the north to hunt his new prey",
            "Holiday Event", heroAramis,
            false, true, false, false, false, 119, 24);
    private final Skin skinAramis4 = new Skin(31L, "Aramis the King of Retribution",
            "https://drive.google.com/uc?id=1282O7S-b_ttvj5z2CelSSnEDtqKOdwbe",
            "https://drive.google.com/uc?id=1TwABhDICo-r71y5cCg9Ph8DdRVkbjXda",
            "https://drive.google.com/uc?id=1Tgw0_NIblr7lmHJSacyDHJgNrjTb1HPv",
            "Let them escape. They will pay the price of their crimes and much more",
            "King God Pass", heroAramis,
            true, true, true, true, true, -1, -1);
    private final Skin skinAramis5 = new Skin(32L, "Knight Aramis",
            "https://drive.google.com/uc?id=13ZA7pd0sMMHNCBvFRxpRo-TybNHN2W1j",
            "",
            "",
            "2022 Foundation Day Festival Skin",
            "Foundation Day Festival", heroAramis,
            false, true, false, false, false, 119, 24);
    private final Skin skinAramis6 = new Skin(33L, "Nutcracker Doll Aramis",
            "https://drive.google.com/uc?id=1nFAfsO4ZBeXIZyEkzrhg4tWpcUZPPlrk",
            "",
            "",
            "2022 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroAramis,
            false, true, true, false, false, 119, 24);
    private final Skin skinAlberon1 = new Skin(34L, "Alberon the Cleric",
            "https://drive.google.com/uc?id=18G1WDqSm0IgjFMn8Tju7Q8_kyMYo8B5G",
            "",
            "",
            "Alberon spent his whole life following the teachings of the Holy Church and now preaches about faith as a priest and a crucial" +
                    " member of the Church",
            "Divine Festival", heroAlberon,
            false, false, false, false, false, 99, 20);
    private final Skin skinAlberon2 = new Skin(35L, "Battle Cleric Alberon",
            "https://drive.google.com/uc?id=1uxa23nI2RZoSYkihKypzNWc3qxNYxKL8",
            "",
            "",
            "Alberon knows well that doing good deeds is not the only way to honor the Most High's will. To defeat the evil that he has defined" +
                    " he tooks off his robe and headed to the battlefield with his companions",
            "Foundation Day Festival", heroAlberon,
            false, false, false, false, false, 99, 20);

    private final Skin skinAlberon3 = new Skin(36L, "Holy King Alberon",
            "https://drive.google.com/uc?id=1k-5OP66CyACIioFs5sSiazQF-BL0vu9J",
            "https://drive.google.com/uc?id=1e1rUf2rfsYXwb9AP58BUkUf_9NsYLcVX",
            "https://drive.google.com/uc?id=1dOgWrNjOiIELxm_qdnX7LjEWc-H-xUdd",
            "O holy light, may you tend and heal the injured heroes. May you soothe their tired spirits and grant courage to bear arms.",
            "King God Pass", heroAlberon,
            true, true, true, true, true, -1, -1);

    private final Skin skinAlberon4 = new Skin(37L, "Archpriest Alberon",
            "https://drive.google.com/uc?id=1ifirvgYy10TuCgMfBsZgadoif3gj_Q1w",
            "https://drive.google.com/uc?id=10L9wpQJLUo0xZ-OFpmzwNXwMR4RYK2w3",
            "https://drive.google.com/uc?id=1WtF5eZRNEfeD0uZwg0QZY0UnGxX5Jkeo",
            "I wish you peace and tranquility forevermore",
            "Eldor Festival", heroAlberon,
            true, true, false, false, true, -1, -1);

    private final Skin skinAlberon5 = new Skin(38L, "Lunar New Year Greetings Alberon",
            "https://drive.google.com/uc?id=1FnC4mkDIr3FfQ4Bjn8HC7M0plSB_Cg0z",
            "",
            "",
            "2023 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Skin", heroAlberon,
            false, true, false, false, false, 119, 24);
    private final Skin skinZuoYun1 = new Skin(39L, "Knight Zuo Yun",
            "https://drive.google.com/uc?id=1uyGYUbZR6GQkzL2iQbKC5HhO9jQjoz6w",
            "",
            "",
            "Impressed by a knight's pride during his journey, Zuo Yun has decided to join them. Now he wields his spear with the honor and" +
                    " arts of the Eastern Kingdom",
            "Foundation Day Festival", heroZuoYun,
            false, true, true, false, false, 119, 24);
    private final Skin skinZuoYun2 = new Skin(40L, "Overlord Zuo Yun",
            "https://drive.google.com/uc?id=1_tFyiYyYyLU-KiRU1j4oHv9CCmIkJGtg",
            "https://drive.google.com/uc?id=1L8Lhs5GbL2l7UurqTTHGoW9n6J4jfqm0",
            "https://drive.google.com/uc?id=1t_ZtvTjGItAo81wwFoQDo-VwrQperi2u",
            "On heaven and earth, I alone am honored - 天上天下唯我獨尊",
            "King God Pass", heroZuoYun,
            true, true, true, true, true, -1, -1);
    private final Skin skinZuoYun3 = new Skin(41L, "Pristine Winter Zuo Yun",
            "https://drive.google.com/uc?id=1-b1a-H_h19bPTyqx9fWaKRrn6Bpeh_aT",
            "",
            "",
            "2022 Winter Festival Skin",
            "Winter Festival", heroZuoYun,
            false, true, true, false, false, 119, 24);
    private final Skin skinZuoYun4 = new Skin(42L, "Heretic Punisher Zuo Yun",
            "https://drive.google.com/uc?id=19g7cb2XKAVwsLqj432fgrByifV4sHazd",
            "",
            "",
            "2022 Divine Festival Skin",
            "Divine Festival", heroZuoYun,
            false, true, true, false, false, 119, 24);
    private final Skin skinZuoYun5 = new Skin(43L, "Wanderer Zuo Yun",
            "https://drive.google.com/uc?id=1Hx-QXc2-7u0WTwfAu8tteBIXb35va3P0",
            "",
            "",
            "2023 East Martial Tournament Skin",
            "East Martial Tournament", heroZuoYun,
            false, true, true, false, false, 119, 24);
    private final Skin skinDraco1 = new Skin(44L, "Draco the Beast",
            "https://drive.google.com/uc?id=1zXE7C_iKvb5ZGlpZ_jzpOA2hc6qlAACB",
            "",
            "",
            "Draco honed his wildness and strength in a dangerous northern forest teeming with evil creatures. His ruthless flame annihilates" +
                    " every living soul that steps into his domain",
            "", heroDraco,
            false, false, false, false, false, 99, 20);
    private final Skin skinDraco2 = new Skin(45L, "Blue Flame Draco",
            "https://drive.google.com/uc?id=1FCbuJ1LHQ17iA9EXbN6dGIfWA6Q1zncp",
            "",
            "",
            "Draco, a half-dragon and half-human, could use the power of an adult dragon at a much younger age than a pure dragon. As" +
                    " he pursue destructive power, his flame was concentrated and enveloped in blue mana, becoming even hotter than before",
            "Winter Festival", heroDraco,
            false, true, true, false, false, 139, 28);
    private final Skin skinDraco3 = new Skin(46L, "Sage Draco",
            "",
            "",
            "https://drive.google.com/uc?id=1C2LweaCLOk4PcjW2ugnuLLsgCDafDmxA",
            "2022 East Martial Tournament Skin",
            "East Martial Tournament", heroDraco,
            false, true, true, false, false, 119, 24);
    private final Skin skinDraco4 = new Skin(47L, "Draco the Black Flame King",
            "https://drive.google.com/uc?id=14RRTPKKGH1YgE8CC2f5JjkBJ7sIKI7CO",
            "https://drive.google.com/uc?id=1bgi6grsmCCN_4POJ-6DMPSbIphL7Km_a",
            "https://drive.google.com/uc?id=14O8bJZWvhD9k_w2jaeNb6CJAMhRziI5N",
            "It is rare to find something hotter than a dragon's breath. Yet the black flame of the half-dragon king who is neither a pure human" +
                    " nor a dragon engulfs the entire world like a ravenous and previously caged evil beast hunting its prey",
            "King God Pass", heroDraco,
            true, true, true, true, true, -1, -1);
    private final Skin skinBardrey1 = new Skin(48L, "Bardrey the Court Musician",
            "https://drive.google.com/uc?id=1XXrz5b4WhgdHYDQcoQhGNNsjWbHx2gOS",
            "",
            "",
            "Bardrey wants to test if her music can make kings and nobles, who are surrounded by the most amazing entertainment, dance." +
                    " She hopes to discover rare instruments and melodies on her journeys",
            "Foundation Day Festival", heroBardrey,
            false, false, false, false, false, 99, 20);
    private final Skin skinBardrey2 = new Skin(49L, "Guardian Angel Bardrey",
            "https://drive.google.com/uc?id=1LWQknHGXGfjFRbhCqaqOvF64uU76XKut",
            "",
            "",
            "When Bardrey plays her lyre, those who listen are calmed and soothed from their rage, becoming peaceful. She is deemed as a " +
                    "guardian angel who protects the world without taking arms",
            "Divine Festival", heroBardrey,
            false, true, true, false, false, 139, 28);
    private final Skin skinBardrey3 = new Skin(50L, "Snow Fairy Bardrey",
            "https://drive.google.com/uc?id=1DFe0wR4pZVUOx0FyYqyhW4rHxBXUD9lZ",
            "",
            "",
            "2021 Holiday Skin",
            "Holiday Event", heroBardrey,
            false, true, false, false, false, 119, 24);
    private final Skin skinBardrey4 = new Skin(51L, "Bardrey the Queen of Melody",
            "https://drive.google.com/uc?id=1wsqlTfAdr71jxpLaJUaf3Fz4zMtena87",
            "https://drive.google.com/uc?id=1lnNkLKbSl5lcKyBHU_rWwzx3EBw_bCHw",
            "https://drive.google.com/uc?id=1_ct08gwzsaviU5R2dSyHUMwc380plXOJ",
            "Let us move faster, vivace! Once again with overflowing mana, encore! Now, let us roar on the battlefield, ensemble!",
            "King God Pass", heroBardrey,
            true, true, true, true, true, -1, -1);
    private final Skin skinMara1 = new Skin(52L, "Mara the Dual Blades",
            "https://drive.google.com/uc?id=1O179IPTqHp9FjNYgDUqb19IZviqig-04",
            "",
            "",
            "Mara was raised by a poor hunter who was a retired war veteran. After her hometown was engulfed in the flames of war, she had to" +
                    " learn to protect herself and wield a sword to survive in the wilderness",
            "", heroMara,
            false, true, true, false, false, 139, 28);
    private final Skin skinMara2 = new Skin(53L, "Corrupted Leader Mara",
            "https://drive.google.com/uc?id=1WnUkjP7jcsVIqZTDouN_sHOacqyRE02B",
            "",
            "",
            "Mara happened to get a necklace with black color while looting a wagon, the necklace emitted constant dark energy even after being" +
                    " moved to her camp, and the bandits became increasingly imbued with madness",
            "Corruption", heroMara,
            false, true, false, false, false, 119, 24);
    private final Skin skinMara3 = new Skin(54L, "Blue Wave Mara",
            "https://drive.google.com/uc?id=1FUV-ak6k-M-TTabY0im4i_TepFuJTS_S",
            "",
            "",
            "2021 Wave Festival Limited Skin",
            "Wave Festival", heroMara,
            false, true, true, false, true, -1, -1);
    private final Skin skinMara4 = new Skin(55L, "Black Rose Mara",
            "https://drive.google.com/uc?id=1GsecnIKDBEBKt7N2sU2D9bfHEQvTG4l3",
            "",
            "",
            "2022 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroMara,
            false, true, false, false, false, 119, 24);
    private final Skin skinMara5 = new Skin(56L, "Blade Queen Mara",
            "https://drive.google.com/uc?id=1iCW7nANBaSx9SprLd6tGhoH3z3gYIi4z",
            "https://drive.google.com/uc?id=1gRv0HgNAvz0qMVOFHQ7j5sfPXMDU7GBO",
            "https://drive.google.com/uc?id=1_UVY61Xzt8GE4hAK5H-hHmbht2g7IZQU",
            "Shake away the shadow behind you, and fend off thousands of blades in front of you. Only then I shall spare your life. Now, dance!",
            "King God Pass", heroMara,
            true, true, true, true, true, -1, -1);
    private final Skin skinMara6 = new Skin(57L, "Captain Hook Mara",
            "https://drive.google.com/uc?id=16GnJ-RA3WfXIEAfvUvyqqiFYIl2n9UbW",
            "",
            "",
            "2023 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroMara,
            false, true, false, false, false, 119, 24);
    private final Skin skinHansi1 = new Skin(58L, "Hansi the Watcher",
            "https://drive.google.com/uc?id=17LK31VfVuxY5yP2sws8QBDcLFECz1R1Y",
            "",
            "",
            "",
            "Divine Festival", heroHansi,
            false, true, false, false, false, 119, 24);
    private final Skin skinHansi2 = new Skin(59L, "Wind Arrow Hansi",
            "https://drive.google.com/uc?id=1MZ447dzi-emx1HkQlJRW2EkMAumh01wU",
            "",
            "",
            "Hansi was bestowed with a magical bow made by an ancient artisan elf for her exceptional service in the defense against monsters" +
                    " at the eastern front. This bow, which contains the essence of wind magic, gathers and sharpens air to shoot it like an arrow",
            "East Martial Tournament", heroHansi,
            false, true, false, false, false, 119, 24);
    private final Skin skinHansi3 = new Skin(60L, "Phoenix Queen Hansi",
            "https://drive.google.com/uc?id=1rwy7lJpWeGSxWjk-QgQsyUKTBcdm9ZxR",
            "https://drive.google.com/uc?id=1ZPRuBUjeVcsRsMDxO5skE8FbS-AL1Wg-",
            "https://drive.google.com/uc?id=1_8fFRFLGdDxe-JTOk8HitFmSZf4jOK4p",
            "Bring me my bow. Only their ashes blown by the wind may leave this land",
            "King God Pass", heroHansi,
            true, true, true, true, true, -1, -1);
    private final Skin skinHansi4 = new Skin(61L, "Hansi the Shadow of the Empire",
            "https://drive.google.com/uc?id=1_yGUoiFsDjvHcEzSJnvgR7iu-9IPXOsS",
            "",
            "",
            "2022 Foundation Day Festival Skin",
            "2022 Foundation Day Festival Skin", heroHansi,
            false, true, true, false, false, 119, 24);
    private final Skin skinHansi5 = new Skin(62L, "Snow White Hansi",
            "https://drive.google.com/uc?id=1t-HbxBm8l1HXQrLut2VU6gW171sBiIeh",
            "",
            "",
            "2022 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroHansi,
            false, true, false, false, false, 119, 24);
    private final Skin skinHansi6 = new Skin(63L, "Black Rabbit Hansi",
            "https://drive.google.com/uc?id=1WDyEyE2ATxPQV01Utg1bTYEaMMFuybN9",
            "",
            "",
            "2023 Year of the Black Rabbit Skin",
            "Event", heroHansi,
            false, false, false, false, false, 23, 2023);
    private final Skin skinLyca1 = new Skin(64L, "Crimson Wolf Lyca",
            "https://drive.google.com/uc?id=128S47WHWmhkgzIrlbDIXC566sFwWvViR",
            "",
            "",
            "Deep inside Lyca's heart, a hatred for humans who ostracized him still burns. His flaming rage turns into madness when he " +
                    "transforms into his wolf form. When that happens, no one, not even Lyca himself, can control him",
            "", heroLyca,
            false, true, false, false, false, 119, 24);
    private final Skin skinLyca2 = new Skin(65L, "Frost Prison Lyca",
            "https://drive.google.com/uc?id=15gcFboNsUoyqTM98uq7xCUxHIqGNnjm7",
            "",
            "",
            "Lyca was imprisoned in a frost prison in the depths of the north after going berserk and destroying several villages. The" +
                    " northern chill couldn't cool down Lyca's rage, but it did give him back cold reasoning",
            "", heroLyca,
            false, true, false, false, false, 119, 24);
    private final Skin skinLyca3 = new Skin(66L, "Northern King Lyca",
            "https://drive.google.com/uc?id=18w4eyr_KeSTOA-dBiuLnThpX-e2PDjhF",
            "https://drive.google.com/uc?id=1MsQlvd-ZIVD5jOuToTWwS0l6ausswSSp",
            "https://drive.google.com/uc?id=1NrrXW--T5Qw9Lr7pZ2Y9KzZVY3iIHfxY",
            "...It's a full moon",
            "King God Pass", heroLyca,
            true, true, true, true, true, -1, -1);
    private final Skin skinLyca4 = new Skin(67L, "Lunar New Year Greetings Lyca",
            "https://drive.google.com/uc?id=1vKqxngHRgyayeuYIj_cDXxCLvgRb7jbu",
            "",
            "",
            "2023 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroLyca,
            false, true, true, true, false, 139, 28);
    private final Skin skinLyca5 = new Skin(68L, "Disguised Lyca",
            "https://drive.google.com/uc?id=1PCGSHUWDgNtaCMotP0EeWCRl3dZDV2tb",
            "",
            "",
            "2023 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroLyca,
            false, true, false, false, false, 119, 24);
    private final Skin skinLycanthrope1 = new Skin(69L, "Crimson Wolf Lycanthrope",
            "https://drive.google.com/uc?id=1sy7IvfQgoHA3FHyg8dsCKa36D6HfOIKI",
            "",
            "",
            "Deep inside Lyca's heart, a hatred for humans who ostracized him still burns. His flaming rage turns into madness when he " +
                    "transforms into his wolf form. When that happens, no one, not even Lyca himself, can control him",
            "source", heroLycanthrope,
            false, true, false, false, false, 119, 24);
    private final Skin skinLycanthrope2 = new Skin(70L, "Frost Prison Lycanthrope",
            "https://drive.google.com/uc?id=18y20aREhOzy1QN8zEG_i468QAFdffPNY",
            "",
            "",
            "Lyca was imprisoned in a frost prison in the depths of the north after going berserk and destroying several villages. The" +
                    " northern chill couldn't cool down Lyca's rage, but it did give him back cold reasoning",
            "", heroLycanthrope,
            false, true, false, false, false, 119, 24);
    private final Skin skinLycanthrope3 = new Skin(71L, "Northern King Lycanthrope",
            "https://drive.google.com/uc?id=13w5phIkw1rCxa1k7P_XIt-rKWRjhG3js",
            "",
            "",
            "...It's a full moon",
            "King God Pass", heroLycanthrope,
            true, true, true, true, true, -1, -1);
    private final Skin skinLycanthrope4 = new Skin(72L, "Lunar New Year Greetings Lycanthrope",
            "https://drive.google.com/uc?id=1efYhFL_V1tG9gv8xfsQpmaN39D1UVsoE",
            "",
            "",
            "2023 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroLycanthrope,
            false, true, true, true, false, 139, 28);
    private final Skin skinLycanthrope5 = new Skin(73L, "Disguised Lycanthrope",
            "https://drive.google.com/uc?id=16wxpp0E3CNCWfBMafxmRaIKwACYGtq1s",
            "",
            "",
            "2023 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroLycanthrope,
            false, true, false, false, false, 119, 24);
    private final Skin skinJol1 = new Skin(74L, "Warlord Jol",
            "https://drive.google.com/uc?id=1pIHxEYbceE3ZhyJlz-CnJPdyLja5pB7O",
            "",
            "",
            "Jol has conquered the Eternal Forest on the Western reach of the continent and how marches to dominate who oppose him all in " +
                    "his shining armor. His sword is glowin with the power of the Most High and it shall shake all human and the monsters of the North",
            "", heroJol,
            false, false, false, false, false, 99, 20);
    private final Skin skinJol2 = new Skin(75L, "Corrupted Prince Jol",
            "https://drive.google.com/uc?id=1xC-Wb3acAM1B1ITtC8KBRHe9FCWuxzKI",
            "",
            "",
            "Jol, who was reversed as the next monarch, touched a forbidden force for the revival of the Elves. Wounds from his sword were no" +
                    " longer recovered and nothing was left where his army pass through",
            "Corruption", heroJol,
            false, true, false, false, false, 119, 24);
    private final Skin skinJol3 = new Skin(76L, "Blue Wave Jol",
            "https://drive.google.com/uc?id=1b21udKUmGfjOHiTR9UDb8rwU9xiK_eIz",
            "",
            "",
            "2021 Wave Festival Limited Skin",
            "Wave Festival", heroJol,
            false, true, false, false, true, -1, -1);
    private final Skin skinJol4 = new Skin(77L, "Eternity King Jol",
            "https://drive.google.com/uc?id=12G_yLX2SWjEUuu0hhuqWU1Z57NjGQBRy",
            "https://drive.google.com/uc?id=1LxhIslc-kcDSE3L_JR1rTRuwFoahhrR6",
            "https://drive.google.com/uc?id=1Aw0BJCzJ6ywUIh2CsmKeYW7N2tPdM8Sl",
            "The all-slashing sword. Armor of the Eternal Forest. Undying will. Might of the Most High. I swear on my powers that I will " +
                    "annihilate you all",
            "King God Pass", heroJol,
            true, true, true, true, true, -1, -1);
    private final Skin skinJol5 = new Skin(78L, "Red Rose Jol",
            "https://drive.google.com/uc?id=1L4bB3ShLPyrMGO6muqN34xiGQWzgrMRA",
            "",
            "",
            "2022 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroJol,
            false, true, false, false, false, 119, 24);
    private final Skin skinJol6 = new Skin(79L, "Lunar New Year Greetings Jol",
            "https://drive.google.com/uc?id=1SMKEn08dn-ZALpb1RicHQiPgj6UShyg3",
            "",
            "",
            "2023 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroJol,
            false, true, false, false, false, 119, 24);
    private final Skin skinLily1 = new Skin(80L, "Spring Messenger Lily",
            "https://drive.google.com/uc?id=1xtKo9MsJSSBz__j02_IGFiKxOZg6FhuO",
            "",
            "",
            "When spring comes to the west Eternal Forest, fairies change their clothes and celebrate the season of birth. Lily also prays" +
                    " for the blooming of the forest with Shasha. May the darkness of the North permeate the Eternal Forest next spring",
            "Flower Blooming Festival", heroLily,
            false, true, false, false, false, 119, 24);
    private final Skin skinLily2 = new Skin(81L, "Water Spirit Lily",
            "https://drive.google.com/uc?id=1FcSTK2-Z4Ct5gp8nzUNkZ21rjpamUdVE",
            "",
            "",
            "When black mana stains the atmosphere, there comes a time when it won't rain for several months even in the verdant Eternal Forest," +
                    " Sha-sha stores water from the Spring of Vitality and goes on a journey with Lily to help the withered beings in the forest",
            "Wave Festival", heroLily,
            false, true, false, false, false, 119, 24);
    private final Skin skinLily3 = new Skin(82L, "Snowball Fight Lily",
            "https://drive.google.com/uc?id=1Uwlo1SzRiXlk7UhHYYZfNEcdOf_AaS34",
            "",
            "",
            "2021 Holiday Skin",
            "Holiday Event", heroLily,
            false, true, false, true, false, 119, 24);
    private final Skin skinLily4 = new Skin(83L, "Frost Tribe Lily",
            "https://drive.google.com/uc?id=18bR0Z6iBezDwuVUXKjZ8Va_Q17ujiYZ9",
            "",
            "",
            "The Frost Tribe came across a mystic who visited the depths of the north. Her warm magic briefly summoned the energy of spring even" +
                    " in the north, and the tribe members eventually accepted her as their guest",
            "Northern Monster Festival", heroLily,
            false, true, false, false, false, 119, 24);
    private final Skin skinLily5 = new Skin(84L, "Lunar New Year Greetings Lily",
            "https://drive.google.com/uc?id=17jVToYYtNijP7u34Z2tXGvZ8t02IYooJ",
            "",
            "",
            "2022 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroLily,
            false, true, true, false, false, 119, 24);
    private final Skin skinLily6 = new Skin(85L, "Queen Lily",
            "https://drive.google.com/uc?id=1dJCJulzKDYTpjFexftrGVtn58agn4p35",
            "https://drive.google.com/uc?id=1Fjk8YGYC7VrZcLFgBRTV1ataClp_Q160",
            "https://drive.google.com/uc?id=10isuk_KrkTwps4_fknoeIU4SC0t7OSL0",
            "Your wait shall finally be over, my loyal subjects! Let us charge and claim our victory! Rise, my castle, rise!",
            "King God Pass", heroLily,
            true, true, true, true, true, -1, -1);
    private final Skin skinShasha1 = new Skin(86L, "Spring Messenger Lily and Shasha",
            "https://drive.google.com/uc?id=1XeX6_mwLq8iuO-uMDYmMoc9KqHngsfy5",
            "",
            "",
            "When spring comes to the west Eternal Forest, fairies change their clothes and celebrate the season of birth. Lily also prays" +
                    " for the blooming of the forest with Shasha. May the darkness of the North permeate the Eternal Forest next spring",
            "Flower Blooming Festival", heroLilyAndShaSha,
            false, true, false, false, false, 119, 24);
    private final Skin skinShasha2 = new Skin(87L, "Water Spirit Lily and Shasha",
            "https://drive.google.com/uc?id=18KpmUWqPMmkWJpzh2J4o48uqUTTKUUd5",
            "",
            "",
            "When black mana stains the atmosphere, there comes a time when it won't rain for several months even in the verdant Eternal Forest," +
                    " Sha-sha stores water from the Spring of Vitality and goes on a journey with Lily to help the withered beings in the forest",
            "Wave Festival", heroLilyAndShaSha,
            false, true, false, false, false, 119, 24);
    private final Skin skinShasha3 = new Skin(88L, "Snowball Fight Lily and Shasha",
            "https://drive.google.com/uc?id=1tduPkR8RXAzRbkAzdbuyjSCS1GN0QQA1",
            "",
            "",
            "2021 Holiday Skin",
            "Holiday Event", heroLilyAndShaSha,
            false, true, false, true, false, 119, 24);
    private final Skin skinShasha4 = new Skin(89L, "Frost Tribe Lily and Shasha",
            "https://drive.google.com/uc?id=18Udg8XKimiGK5UIGEINJQ7HIBrGp2lhR",
            "",
            "",
            "The Frost Tribe came across a mystic who visited the depths of the north. Her warm magic briefly summoned the energy of spring even" +
                    " in the north, and the tribe members eventually accepted her as their guest",
            "Northern Monster Festival", heroLilyAndShaSha,
            false, true, false, false, false, 119, 24);
    private final Skin skinShasha5 = new Skin(90L, "Lunar New Year Greetings Lily and Shasha",
            "https://drive.google.com/uc?id=1JI4Bwbg3Jcr1OEkpyF3g5t1W-bbTkBfg",
            "",
            "",
            "2022 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroLilyAndShaSha,
            false, true, true, false, false, 119, 24);
    private final Skin skinShasha6 = new Skin(91L, "Queen Lily and Shasha",
            "https://drive.google.com/uc?id=12V0MzxcAjPI4N9FzCwOuvaIGTA6iAGMA",
            "",
            "",
            "Your wait shall finally be over, my loyal subjects! Let us charge and claim our victory! Rise, my castle, rise!",
            "King God Pass", heroLilyAndShaSha,
            true, true, true, true, true, -1, -1);
    private final Skin skinZupitere1 = new Skin(92L, "Mage Guild Zupitere",
            "https://drive.google.com/uc?id=1b4VDQXghlmwI6eBn3uQ0Pf5IMBUsTyyS",
            "",
            "",
            "To learn more powerful magic, Zupitere went to the Mage Guild, located between the West and the South. She showed her overwhelming" +
                    " talent among the elves and human cadets. Zupitere is expected to become a archmage in the future",
            "", heroZupitere,
            false, false, false, false, false, 99, 20);
    private final Skin skinZupitere2 = new Skin(93L, "Devil Zupitere",
            "https://drive.google.com/uc?id=1fA7_JzkGVjEdXwsI_L7M1u4BKfbgPswX",
            "",
            "",
            "Zupitere, a genius from birth, despises the incompetent. On the battlefield, she electrocuted her frail enemies countless times" +
                    " and laughed at them. That's why She is called a devil with the face of a girl",
            "Divine Festival", heroZupitere,
            false, true, true, false, false, 119, 24);
    private final Skin skinZupitere3 = new Skin(94L, "Thunder Queen Zupitere",
            "https://drive.google.com/uc?id=1Zclk-FR0LBtjuPNmJCfAaFnZ3wMzKJ4u",
            "https://drive.google.com/uc?id=15tj4M0hnLV3bYvzTmUVfV_JWI079IxRe",
            "https://drive.google.com/uc?id=1E3PKp2AQ-Q-WGkiJ-gTlvG9sHirsDP8A",
            "You can run, but how will you escape the sky above?",
            "King God Pass", heroZupitere,
            true, true, true, true, true, -1, -1);
    private final Skin skinZupitere4 = new Skin(95L, "Ghost Zupitere",
            "https://drive.google.com/uc?id=1WABOki9PM92dco8YgIjILywb5qQJ6Fkp",
            "",
            "",
            "2022 Halloween Event Skin",
            "Halloween Festival", heroZupitere,
            false, true, true, false, false, 119, 24);
    private final Skin skinAsiaq1 = new Skin(96L, "Night Crow Asiaq",
            "https://drive.google.com/uc?id=1vJMv8zwGsYV_6d0Li3GAB3silVuqZ7ga",
            "",
            "",
            "People of Asiaq's hometown in the northeast have been hunting demons for their life, but also hunted by them. As the power of the" +
                    " demons grew stronger, Asiaq enhanced the power of the wildness. Her cold gaze always stares at the horizon",
            "", heroAsiaq,
            false, false, false, false, false, 99, 20);
    private final Skin skinAsiaq2 = new Skin(97L, "Fox Spirit Asiaq",
            "https://drive.google.com/uc?id=1MtS2nU_N4GBcio8Y0yKUicU141Sxxuah",
            "",
            "",
            "Every now and then, Asiaq calls upon the spirits and spends her time with them. Her closest friend, Fox Spirit, taught her to" +
                    " hunt and to be cautious",
            "", heroAsiaq,
            false, false, false, false, false, 99, 20);
    private final Skin skinAsiaq3 = new Skin(98L, "Pristine Winter Asiaq",
            "https://drive.google.com/uc?id=1D-yIdwdirq6yZoaRa1CdmlUHLqHJJmzc",
            "",
            "",
            "2022 Winter Festival Skin",
            "Winter Festival", heroAsiaq,
            false, true, true, false, false, 119, 24);
    private final Skin skinAsiaq4 = new Skin(99L, "Sorcerer Asiaq",
            "https://drive.google.com/uc?id=1S24GR_Umf1PGQ4_GltHZCaMhJu89S5VT",
            "",
            "",
            "skindescription",
            "East Martial Tournament", heroAsiaq,
            false, true, true, true, false, 139, 28);
    private final Skin skinAsiaq5 = new Skin(100L, "Asiaq the Queen of Dead Spirits",
            "https://drive.google.com/uc?id=19EtQdY9dwpWr-0FoAbejp-vNF84pM3Zh",
            "https://drive.google.com/uc?id=13Pv156iRm5fVTzbX4efQ_jf823Svs048",
            "https://drive.google.com/uc?id=1H9lJkegU8lDTBIcKma-x5yMAOoYDFJWn",
            "Until the next twilight falls, may your soul stay in one piece.",
            "King God Pass", heroAsiaq,
            true, true, true, true, true, -1, -1);
    private final Skin skinBehemus1 = new Skin(101L, "Behemus the Holy Shield",
            "https://drive.google.com/uc?id=1O6eV45q5vO-90zfbfIobSQoDBoi0YWbB",
            "",
            "",
            "Instead of a black iron shields symbolizing the power and honor of the empire, Behemus jumps toward the battlefield with" +
                    " a pure white shields that protect others and confronts evil",
            "Divine Festival", heroBehemus,
            false, false, false, false, false, 99, 20);
    private final Skin skinBehemus2 = new Skin(102L, "Blackhorn Behemus",
            "https://drive.google.com/uc?id=1tulE7312x9UwTrvo4qbpPwh3H4AgZCR8",
            "",
            "",
            "Behemus hunted the monsters that even made him afraid, taking their shapes. Adorned with black horns, Behemus now charges" +
                    " ferociously like a wild beast",
            "", heroBehemus,
            false, true, false, false, false, 119, 24);
    private final Skin skinBehemus3 = new Skin(103L, "Chief Gatekeeper Behemus",
            "https://drive.google.com/uc?id=16_QN4rIgnVpDIPETHBB8p7uka5uOn4_y",
            "",
            "",
            "2022 East Martial Tournament Skin",
            "East Martial Tournament", heroBehemus,
            false, true, false, false, false, 119, 24);
    private final Skin skinBehemus4 = new Skin(104L, "Snowball Fight Behemus",
            "https://drive.google.com/uc?id=1B6HvZOJrSz_ncHIoIXRMBUAASrHvw4NP",
            "",
            "",
            "2022 Holiday Skin",
            "Holiday Event", heroBehemus,
            false, true, false, false, false, 119, 24);
    private final Skin skinBehemus5 = new Skin(105L, "Demon Bull King Behemus",
            "https://drive.google.com/uc?id=17kzV5sV7xbCsbz1E8ZpmIm75NlTGgsnP",
            "https://drive.google.com/uc?id=1oeWcqPWgJM2brlSxZM-lst0w2Aygq22b",
            "https://drive.google.com/uc?id=1S9uC0YaQGjA2HCKdBPA1grXp7xIDtL78",
            "Spells, weapons and every other tool out there... They're all useless. Only power and destruction will bring us victory! " +
                    "Witness my overwhelming power!",
            "King God Pass", heroBehemus,
            true, true, true, true, true, -1, -1);
    private final Skin skinYeon1 = new Skin(106L, "Wavelet Yeon",
            "https://drive.google.com/uc?id=1Qwx4kk30Ufv95jA2kieEXQaXJ1XSqxlL",
            "",
            "",
            "The coast at the end of the Eastern continent, where the Yeon grew up, was far from the world. But everything has changed since" +
                    " her village was attacked by the demons",
            "", heroYeon,
            false, false, false, false, false, 99, 20);
    private final Skin skinYeon2 = new Skin(107L, "Pirate King Yeon",
            "https://drive.google.com/uc?id=1gC37GcDC0fEIUlOzx0IQE-IRUiOQCUVo",
            "https://drive.google.com/uc?id=1DNP1aQJKDehO3pt9yY1K2YU0FWCtoNU5",
            "https://drive.google.com/uc?id=129xeY-ckNct2LLEsIKBpGb_fXdWIWFDy",
            "There are no laws at sea. You find only my waves and your life",
            "King God Pass", heroYeon,
            true, true, true, true, true, -1, -1);
    private final Skin skinYeon3 = new Skin(108L, "Nine-tailed Fox Yeon",
            "https://drive.google.com/uc?id=1rnMXYMNlcr8D_tWPzozBwkWTS2N5pne5",
            "",
            "",
            "2021 Halloween Event Limited Skin",
            "Halloween Festival", heroYeon,
            false, true, false, false, true, -1, -1);
    private final Skin skinYeon4 = new Skin(109L, "Black Tiger Yeon",
            "https://drive.google.com/uc?id=1H79oRZwS2eoagCt_M0APFnYghiMACNL8",
            "",
            "",
            "2022 Year of the Black Tiger Skin",
            "Event", heroYeon,
            false, false, false, false, true, -1, -1);
    private final Skin skinYeon5 = new Skin(110L, "Snowball Fight Yeon",
            "https://drive.google.com/uc?id=1X2n2nNkWaNqeAEfQTk0bLkYh6C-PE-Fn",
            "",
            "",
            "2022 Holiday Skin",
            "Holiday Event", heroYeon,
            false, true, false, true, false, 139, 28);
    private final Skin skinMel1 = new Skin(111L, "Mel, The Chosen One",
            "https://drive.google.com/uc?id=1nJ_rLHOi6Fg00JnTmD2tF2M2iDmQ89WR",
            "",
            "",
            "Mel has solved many requests and destroyed many evil beings. Everyone praises her name, but she still wields a sword for someone somewhere",
            "", heroMel,
            false, true, true, false, false, 119, 24);
    private final Skin skinMel2 = new Skin(112L, "Dark Lord Mel",
            "https://drive.google.com/uc?id=1qq5_EQ-4H0XkY3bF5ZJScIwyKUdFNGsz",
            "https://drive.google.com/uc?id=1Lyqr281-T3LxygQCUj8RMGEUsZZzXapN",
            "https://drive.google.com/uc?id=1v4wCYoqCxFXgFHicg-JOfsDKmYKudPyn",
            "At the end of destiny, a bright blue hope was turned into a pitch black despair. The catastrophic sword shall bring on the apocalypse",
            "King God Pass", heroMel,
            true, true, true, true, true, -1, -1);
    private final Skin skinMel3 = new Skin(113L, "Mel the Warrior of the Empire",
            "https://drive.google.com/uc?id=1X5LTkRjULWtNEKmuvdC7erq0B-9BZSit",
            "",
            "",
            "Foundation Day Festival",
            "2022 Foundation Day Festival Skin", heroMel,
            false, true, true, false, false, 119, 24);
    private final Skin skinMel4 = new Skin(114L, "Mel the Admiral of Loyal Valor",
            "https://drive.google.com/uc?id=1Mrm9Et8u18lPvzt8u4ZNYlj3pSN9rxTk",
            "",
            "",
            "There is a tale of a mythical war hero in the East. Wearing the armor of the undefeated legendary general, Mel could enhance her sword" +
                    " with the power of the waves like the avatar of the mighty ocean",
            "East Martial Tournament", heroMel,
            false, true, true, true, true, -1, -1);
    private final Skin skinMel5 = new Skin(115L, "Mel the Hero of Eldor",
            "https://drive.google.com/uc?id=1IbndbfPDJvEHnQkNHilLIASJ61C5Zt94",
            "https://drive.google.com/uc?id=1Uk6t6ZQKCjCdJR3JMFQO_sm0ZfCJ4aL5",
            "https://drive.google.com/uc?id=1uJMtyCeYqfgdBIHBzYPMB02Gu8KgVLIa",
            "Nice to meet you, recruit. I am Mel, a royal guardian knight",
            "source", heroMel,
            true, true, true, false, true, -1, -1);
    private final Skin skinMel6 = new Skin(116L, "Mel the Light Goddess",
            "https://drive.google.com/uc?id=1_BW_XVJAyCd192RPZGF-Ixu-_StbmWC5",
            "https://drive.google.com/uc?id=1XiWk6MYuawbWSqb8vGOSHRcX1-SamGB_",
            "https://drive.google.com/uc?id=17FPJ0uZdKVyA8wJAPFglZnpP8bzyZiTv",
            "The girl that sundered darkness with her tender hands, shall now grasp a streak of light and ascend to heaven; the splendor of the" +
                    " goddess will illuminate all lands",
            "Advent of the Light", heroMel,
            true, true, true, true, true, -1, -1);
    private final Skin skinBombie1 = new Skin(117L, "Plague Doctor Bombie",
            "https://drive.google.com/uc?id=1DYCNppGzWKSgFwrohEN56TwTd7F6ms34",
            "",
            "",
            "Bombie, who has learned how to deal with poison following explosives, addicts the enemies with his special poison bomb made" +
                    " with his talents",
            "", heroBombie,
            false, true, false, false, false, 119, 24);
    private final Skin skinBombie2 = new Skin(118L, "Summer Beach Bombie",
            "https://drive.google.com/uc?id=1zEwhwe5e9bpH_znoHvohBNw3wGtECstU",
            "",
            "",
            "During the blazing hot summer, miners would go to the beach to relieve their fatigue from hard labor. Bombie brings local" +
                    " specialties to pay for his explosives. From time to time, some of his products are said to leave explosive tastes in your mouth",
            "Wave Festival", heroBombie,
            false, true, true, false, false, 119, 24);
    private final Skin skinBombie3 = new Skin(119L, "Snowball Fight Bombie",
            "https://drive.google.com/uc?id=1Kk4PouwaJbRyLGBu59ESmf5oG4hUPcf6",
            "",
            "",
            "2021 Holiday Skin",
            "Holiday Event", heroBombie,
            false, true, true, true, false, 119, 24);
    private final Skin skinBombie4 = new Skin(120L, "Technomancy Master Bombie",
            "https://drive.google.com/uc?id=1kj_QcLVe6NYOoPpA3K52uo66uYs7OPvx",
            "https://drive.google.com/uc?id=1jAJUEq_T5gJezyPIQ7MBOfBSt6qdD15b",
            "https://drive.google.com/uc?id=10-fdDFACIn_tqpnp0GcvEtAnCslW2zJP",
            "Peace is not achieved through power only. You also need technology, mana and a little bit of ingenuity",
            "King God Pass", heroBombie,
            true, true, true, true, true, -1, -1);
    private final Skin skinBombie5 = new Skin(121L, "Acolyte Bombie",
            "https://drive.google.com/uc?id=1uqGLQzvXBao39sfvdC_2gfTqZb9c4kdf",
            "",
            "",
            "2023 Divine Festival Skin",
            "Divine Festival", heroBombie,
            false, true, false, false, false, 119, 24);
    private final Skin skinLuniare1 = new Skin(122L, "Summer Beach Luniare",
            "https://drive.google.com/uc?id=1CHsyRbGC0fZwH4mOXT-i-Mu9yXi4yPTy",
            "",
            "",
            "Luniare's hometown is cold enough for the sea to be frozen, but every summer, the warm sunlight shines upon the beach. " +
                    "Her tribe holds a summer festival to celebrate precious days",
            "Wave Festival", heroLuniare,
            false, false, false, false, false, 99, 20);
    private final Skin skinLuniare2 = new Skin(123L, "Saint Luniare",
            "https://drive.google.com/uc?id=1aKvhYLWyhXIiQ2aHlsv1q_G9rsAflZ4j",
            "",
            "",
            "2022 Divine Festival Skin",
            "Divine Festival", heroLuniare,
            false, true, true, false, false, 119, 24);
    private final Skin skinLuniare3 = new Skin(124L, "Luniare the New Moon Queen",
            "https://drive.google.com/uc?id=1P64ckzrFbnCbvTYW8xLSkUrrI9fANpdU",
            "https://drive.google.com/uc?id=1-DupkgM07xOe5Bj2WZ7CsYyJ26nI_hFs",
            "https://drive.google.com/uc?id=1B5dznOghkneBSrr0AAA3GUqFnYbh_jUB",
            "The Crescent Moon may be the darkest of all Moons, but it will soon become bright. May the New Moon blesses you who will shine " +
                    "the night as the Full Moon",
            "King God Pass", heroLuniare,
            true, true, true, true, true, -1, -1);
    private final Skin skinLuniare4 = new Skin(125L, "Pristine Winter Luniare",
            "https://drive.google.com/uc?id=1gtOeRfobGprc0evl7E78oVRSj6_23aO6",
            "",
            "",
            "2022 Winter Festival Skin",
            "Winter Festival", heroLuniare,
            false, true, false, true, false, 139, 28);
    private final Skin skinRossette1 = new Skin(126L, "Fishing Festival Rossette",
            "https://drive.google.com/uc?id=1-ULvqsYvT-yzHzxQdsSUjyNlpCmP7M1a",
            "",
            "",
            "When it is the season of the sky becoming clear again, the southern village of the Empire holds a sea fishing festival. Rossette" +
                    " enters the contest with just a spear and not a fishing rod, but she wins a prize every year",
            "Wave Festival", heroRossette,
            false, true, true, false, false, 119, 24);
    private final Skin skinRossette2 = new Skin(127L, "Frozen Land Lumberjack Rossette",
            "https://drive.google.com/uc?id=12Ut4W9RW7rCuJXf1G5bqMiP-0g_3W24X",
            "",
            "",
            "While Rossette was cutting down trees in one of the northern mountains, all the bears mistook her big fur coat as another bear," +
                    " leading to a turf war. She now unintendedly became the most powerful bear in the north",
            "Holiday Event", heroRossette,
            false, true, false, false, false, 119, 24);
    private final Skin skinRossette3 = new Skin(128L, "Knight Rossette",
            "https://drive.google.com/uc?id=1EzHOWjt2Eb6sOA3u1Kruh-qcOU6C_mMZ",
            "",
            "",
            "2022 Foundation Day Festival Skin",
            "Foundation Day Festival", heroRossette,
            false, true, false, false, false, 119, 24);
    private final Skin skinRossette4 = new Skin(129L, "Canna Rossette",
            "https://drive.google.com/uc?id=1A1Rht3TFesPdq8WfrY-EbjR3uT9lRNAM",
            "",
            "",
            "2023 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroRossette,
            false, true, false, false, false, 119, 24);
    private final Skin skinRossette5 = new Skin(130L, "Execution Queen Rossette",
            "https://drive.google.com/uc?id=1IqI1zehglIEvIf88MoUFXlC1Tj9nHwnu",
            "https://drive.google.com/uc?id=1Xuc2DVYduOv4Kr4WvdC39qXdIBDtfoYv",
            "https://drive.google.com/uc?id=1MsDJTPBtdVsHOJm8NSFaKt4j26RBCs2j",
            "Next one taking the one-way ticket trip to hell! Come on up!",
            "King God Pass", heroRossette,
            true, true, true, true, true, -1, -1);
    private final Skin skinCain1 = new Skin(131L, "Bladeclaw Cain",
            "https://drive.google.com/uc?id=1eNzbq7Ljs8fkWy_Ce6herNE9M8TtX-oE",
            "",
            "",
            "Cain became a ruthless slayer after acquiring bladeclaws from an evil beast of the northeastern desert. " +
                    "To his deathly circus is now addes a tenacity of a predator",
            "", heroCain,
            false, true, false, false, false, 139, 28);
    private final Skin skinCain2 = new Skin(132L, "Bridal Mask Cain",
            "https://drive.google.com/uc?id=1lnIqGD0klbKekZXOGAZju_4ziJQ_xfnm",
            "",
            "",
            "2022 East Martial Tournament Skin",
            "East Martial Tournament Skin", heroCain,
            false, true, false, false, false, 119, 24);
    private final Skin skinCain3 = new Skin(133L, "Vampire Cain",
            "https://drive.google.com/uc?id=1tpyOOv-VNdf-TqQRgQrkSD-XzXwfjt0L",
            "",
            "",
            "Cain, who drinks the blood of his enemies amidst the dark silence, might as well be the vampire of vampires. The difference from " +
                    "his human form is that only barren death remains on his path, without a single drop of blood to be found",
            "Northern Monster Festival", heroCain,
            false, true, true, true, false, 139, 28);
    private final Skin skinCain4 = new Skin(134L, "Cain the Shadow of the Empire",
            "https://drive.google.com/uc?id=1U3arhrXNMEW9ad6loz6We3aEzt5_BFbi",
            "",
            "",
            "2023 Foundation Day Festival Skin",
            "Foundation Day Festival", heroCain,
            false, false, false, false, false, 119, 24);
    private final Skin skinCain5 = new Skin(135L, "Shadow King Cain",
            "https://drive.google.com/uc?id=1fdrtagGTf_wkR691uGHKdx75hSm5Gztf",
            "https://drive.google.com/uc?id=1a1JSuIvBpS5Pvoo9Ls9GGpFeKyN1yoZa",
            "https://drive.google.com/uc?id=1uWXPx_ioFfe2sKc5cufmdUlCyo_M3BOQ",
            "I see the fading of your light. Behold! My shadow shall engulf you",
            "King God Pass", heroCain,
            true, true, true, true, true, -1, -1);
    private final Skin skinChungAh1 = new Skin(136L, "Knight Chung Ah",
            "https://drive.google.com/uc?id=1e_AoPz5JprgV3aLjaHR--ywLIfr_QhfI",
            "",
            "",
            "As a great advocate of warfare and battles, Chung Ah felt a sense of belonging among the powerful knights. She met numerous" +
                    " masters, pupils and peers here",
            "Foundation Day Destival", heroChungAh,
            false, true, false, false, false, 119, 24);
    private final Skin skinChungAh2 = new Skin(137L, "Dragon Queen Chung Ah",
            "https://drive.google.com/uc?id=1R3vhLTsrFfTa14svXbi5NW9Nlk-wxw_m",
            "https://drive.google.com/uc?id=1JOIJqytXG3VHUyDy2DSYOmtK9PmPLkfs",
            "https://drive.google.com/uc?id=1Q9lTcJgqDFxtX92xuoOOAdniBa-14EU3",
            "Three slashes were all it took. Are there no one who can challenge me?",
            "King God Pass", heroChungAh,
            true, true, true, true, true, -1, -1);
    private final Skin skinChungAh3 = new Skin(138L, "Void Punisher Chung Ah",
            "https://drive.google.com/uc?id=1fwnKQHzD9D-TRm3_SqFkQYaebibnKgyU",
            "",
            "",
            "When you fight monsters, you should be careful not to become one. Yet Chung Ah wilingly damnes herself to fight the mighty void. " +
                    "Now, the Azure Dragon tainted by the void energy sweeps the battlefield",
            "Northern Monster Festival", heroChungAh,
            false, true, true, true, false, 139, 28);
    private final Skin skinChungAh4 = new Skin(139L, "Grim Reaper Chung Ah",
            "https://drive.google.com/uc?id=1idzpzsaVL00Yu5lIH8F2-hoivRzUAeOi",
            "",
            "",
            "2022 Halloween Event Skin",
            "Halloween Festival", heroChungAh,
            false, true, true, false, false, 119, 24);
    private final Skin skinRahawk1 = new Skin(140L, "Eye of the Desert Rahawk",
            "https://drive.google.com/uc?id=1FQ21JQoyIguaO-fL-FqT_osd4yerBTfn",
            "",
            "",
            "Rahawk's experience in the perilous wilds of the Northern Plains gave him the wisdom and means to overcome any terrains. His" +
                    " eyes quietly focus on his prey even in the barren desert",
            "source", heroRahawk,
            false, true, true, false, false, 119, 24);
    private final Skin skinRahawk2 = new Skin(141L, "Frost Tribe Rahawk",
            "https://drive.google.com/uc?id=18WXra95SgIGUZc1loD34n8bWKisTk5KM",
            "",
            "",
            "Rahawk's hunting skills were just as great as those of the tribes people in the most barren land on the continent. After hunting" +
                    " a giant mammoth together, the members of the Frost Tribe accepted him as their 'brother'",
            "Northern Monster Festival", heroRahawk,
            false, true, false, false, false, 119, 24);
    private final Skin skinRahawk3 = new Skin(142L, "Fishing Festival Rahawk",
            "https://drive.google.com/uc?id=17SMIMRj_SSoaBibG_8n4DXi3O0XkkxlY",
            "",
            "",
            "Rahawk's expert hunting skills are also applicable to creatures of the sea. He participates in the fishing festival every year and" +
                    " brings along a peculiar creature that likes fish instead of a thunder hawk with an aversion to water",
            "source", heroRahawk,
            false, true, true, true, false, 119, 24);
    private final Skin skinRahawk4 = new Skin(143L, "Knight Rahawk",
            "https://drive.google.com/uc?id=1SerRit0uBGaojG80sTSzexyAUsIxUoki",
            "",
            "",
            "2023 Foundation Day Festival Skin",
            "Foundation Day Festival", heroRahawk,
            false, true, false, false, false, 119, 24);
    private final Skin skinRen1 = new Skin(144L, "Black Sanguine Sword Ren",
            "https://drive.google.com/uc?id=1pt0_p1_q3vL1SDdASCDDhfQOcBqLVVH0",
            "",
            "",
            "Ren has infiltrated the Eastern Kingdom and eliminated countless enemies like a silent shadow. The enemies of the Southern" +
                    " Empire who witnessed Ren's swordcraft called her the 'Sword that Draws Death'",
            "source", heroRen,
            false, true, false, false, false, 139, 28);
    private final Skin skinRen2 = new Skin(145L, "Pristine Winter Ren",
            "https://drive.google.com/uc?id=1yC83nXquoYEPZ1vuWFLTWbN0gDKLvRx1",
            "",
            "",
            "2022 Winter Festival Skin",
            "WInter Festival", heroRen,
            false, true, false, false, false, 119, 24);
    private final Skin skinRen3 = new Skin(146L, "Greatest Sword of the East Ren",
            "https://drive.google.com/uc?id=1832JqzqdykKbKHVa25aeWDzOPG4h38sA",
            "",
            "",
            "2022 East Martial Tournament Skin",
            "East Martial Tournament", heroRen,
            false, true, false, false, false, 119, 24);
    private final Skin skinRen4 = new Skin(147L, "Nightmare Queen Ren",
            "https://drive.google.com/uc?id=11adVpIX9gX7mg7lnd9n0mr8_V7CFLx6n",
            "https://drive.google.com/uc?id=1nzlgfnOWfplNlrtFgpbM9HlX4RTHZrlP",
            "https://drive.google.com/uc?id=1-RlJv-0XggTtnKzm0ELTzzYcYbEVCm-6",
            "When all six points of the blade-written hexagrams merge as one to slunder the skies, the blossoms of the bloody flower bed shall" +
                    " bloom across the earth, and I shall become the nightmare of my enemies",
            "King God Pass", heroRen,
            true, true, true, true, true, -1, -1);
    private final Skin skinRen5 = new Skin(148L, "Angel Ren",
            "https://drive.google.com/uc?id=1eQ5T48BxmMzx9mFRA93kikYSz2WmjsM5",
            "",
            "",
            "2023 Divine Festival Skin",
            "Divine Festival", heroRen,
            false, true, false, false, false, 119, 24);
    private final Skin skinAgathe1 = new Skin(149L, "Heretic Punisher Captain Agathe",
            "https://drive.google.com/uc?id=1CXTALtcxY9qN0p79FOTnOmRLSdSBT2de",
            "",
            "",
            "Agathe is called the Unsetting Sun of the Holy Church, but she is cold to those who do not follow the church's creed. With armor" +
                    " and mind of steel, she swings the mace of punishment to the heretics",
            "Divine Festival", heroAgathe,
            false, true, false, false, false, 119, 24);
    private final Skin skinAgathe2 = new Skin(150L, "Undead Agathe",
            "https://drive.google.com/uc?id=1T79CRJ12hfmVRElZyt8kxSq2mMn02XZ2",
            "",
            "",
            "Agathe fought with all of her might against the undead army that ambushed the members of the Holy Church. She managed to evacuate" +
                    " everyone but ultimately fell in the battle. And then... A new durahan rose from the battleground after a while",
            "Northern Monster Festival", heroAgathe,
            false, true, false, false, false, 119, 24);
    private final Skin skinAgathe3 = new Skin(151L, "Pristine Winter Agathe",
            "https://drive.google.com/uc?id=1zx24Op1OKa63CIwlGWQqqYRp7j2uQkCx",
            "",
            "",
            "2022 Winter Festival Skin",
            "Winter Festival", heroAgathe,
            false, true, false, true, false, 139, 28);
    private final Skin skinAgathe4 = new Skin(152L, "Knight Agathe",
            "https://drive.google.com/uc?id=1yyjQjS9YMnUl8Q8KaXgjiKXA_dEpX2f_",
            "",
            "",
            "2023 Foundation Day Festival Skin",
            "Foundation Day Festival", heroAgathe,
            false, true, false, false, false, 119, 24);
    private final Skin skinHela1 = new Skin(153L, "Lunar New Year Greetings Hela",
            "https://drive.google.com/uc?id=1AYHhGw3xV_8aLbIXRFTrTuyBo9svbaID",
            "",
            "",
            "2022 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroHela,
            false, false, true, false, false, 119, 24);
    private final Skin skinHela2 = new Skin(154L, "Violet Hela",
            "https://drive.google.com/uc?id=13ElVRugJAIn2n71kVlR9WasFyM96bagq",
            "",
            "",
            "2022 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroHela,
            false, true, false, false, false, 119, 24);
    private final Skin skinHela3 = new Skin(155L, "Hela the Queen of Fate",
            "https://drive.google.com/uc?id=1cnER8l0MHePE0TyL6pTMs3fEiJSKv4BW",
            "https://drive.google.com/uc?id=1PmnYhX93XKoo6U652dsCGQsKkUk5CXsf",
            "https://drive.google.com/uc?id=1wrFM8Es8GE2SK6kiPSMrOINiKuzfhhxw",
            "Every so often, small pests seem to think they can win against a monster with their numbers alone... Failing to realize what" +
                    " a single snap of my fingers is capable of",
            "King God Pass", heroHela,
            true, true, true, true, true, -1, -1);
    private final Skin skinHela4 = new Skin(156L, "Snowball Fight Hela",
            "https://drive.google.com/uc?id=1tO5fJS7v-fB8cwaIxQKNhYK7C6F4YqjA",
            "",
            "",
            "2022 Holiday Skin",
            "Holiday Event", heroHela,
            false, true, true, true, false, 139, 28);
    private final Skin skinHela5 = new Skin(157L, "Summer Beach Hela",
            "https://drive.google.com/uc?id=1r6ooBszv3yjpwLKD805N3hLNI55CPXSL",
            "",
            "",
            "2023 Wave Festival Limited Skin",
            "Wave Festival", heroHela,
            false, true, false, true, false, 139, 28);
    private final Skin skinZuoBai1 = new Skin(158L, "Devil Zuo Bai",
            "https://drive.google.com/uc?id=1kLzKIqrELwUn92LA87OepYEdwvt-qp8y",
            "",
            "",
            "2022 Divine Festival Skin",
            "Divine Festival", heroZuoBai,
            false, true, true, false, false, 119, 24);
    private final Skin skinZuoBai2 = new Skin(159L, "Blue Wave Zuo Bai",
            "https://drive.google.com/uc?id=1ruYi5FdONja0C4Yg8523B-NtDnGBipML",
            "",
            "",
            "Zuo Bai usually imbues his sword with red flames to become swift, but sometimes he uses the power of fierce blue waves instead." +
                    " Many wonder what enhances his sword more",
            "source", heroZuoBai,
            false, true, true, true, true, -1, -1);
    private final Skin skinZuoBai3 = new Skin(160L, "Eastern Swordsman Zuo Bai",
            "https://drive.google.com/uc?id=1U3NjttNanwQjoY2HDXLxi4K0-fkMvCBO",
            "https://drive.google.com/uc?id=12evBsoe6UCgMroScq4x4_r4-_M7kJuOi",
            "https://drive.google.com/uc?id=1N4Hc6GgwkwrBCqjyAxD_nAdfDqIWFZMm",
            "I fight with my sword. You won't be able to use magic from now on, so you better be prepared. Well then, let's see if you can" +
                    " put up against the energy of my sword",
            "Eldor Festival", heroZuoBai,
            true, true, true, false, true, -1, -1);
    private final Skin skinZuoBai4 = new Skin(161L, "Flame Spirit King Zuo Bai",
            "https://drive.google.com/uc?id=1hpKbJb4B9UICkxjVEofyWygidP7h4aBL",
            "https://drive.google.com/uc?id=1XdXav41_oJX-6_ANkzW6_B_Sv4ZSre7A",
            "https://drive.google.com/uc?id=16L5p8WPfLeoLZkQeCquKKpKyKU8ejzz0",
            "...Then the silhouette rose, wielding the shadow sword of fire to render the split second asunder. And all that remained" +
                    " was ashes, a glimpse of what once was a battlefield",
            "King God Pass", heroZuoBai,
            true, true, true, true, true, -1, -1);
    private final Skin skinZuoBai5 = new Skin(162L, "Cherry Blossom Zuo Bai",
            "https://drive.google.com/uc?id=1nnH6BHItOArMhBhwTdmR14yAB4GMoZg_",
            "",
            "",
            "2023 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroZuoBai,
            false, true, true, false, false, 139, 28);
    private final Skin skinTia1 = new Skin(163L, "Knight Tia",
            "https://drive.google.com/uc?id=1-rOZChpPz1W2g7zyUDH1VZduvfdY_qNG",
            "",
            "",
            "Tia has fulfilled many duties for the Elven royal family for a long time. She once took on the role of a scout to hide among humans" +
                    " and observe the northern monsters by the order of the Elven Prince",
            "", heroTia,
            false, true, false, false, true, -1, -1);
    private final Skin skinTia2 = new Skin(164L, "Forest Watcher Tia",
            "https://drive.google.com/uc?id=1TUQ-6ER3qQz4dN2RzCwIuOIxFvM12h8a",
            "https://drive.google.com/uc?id=1LPM6Eo_I4ISMwmG1A-V_aKDOPOlOfcfH",
            "https://drive.google.com/uc?id=1SQPfyiyrIvNwl1Ki7p5LnuetHiG6VWLZ",
            "Huh? You can't be here! Humans shall not set foot in the Elven Kingdom! What? A test to become the Warrior? There are still " +
                    "people attempting that?",
            "Eldor Festival", heroTia,
            true, true, false, true, true, -1, -1);
    private final Skin skinTia3 = new Skin(165L, "Red Riding Hood Tia",
            "https://drive.google.com/uc?id=1_M-vuidG9PQ4Pa_3zXvPcfArqwcwdYQc",
            "",
            "",
            "2022 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroTia,
            false, true, false, false, false, 119, 24);
    private final Skin skinTia4 = new Skin(166L, "Pristine Winter Tia",
            "https://drive.google.com/uc?id=14jKUDYpZSsMcGUfnrKUDNyu0ryDcGCeR",
            "",
            "",
            "2022 Winter Festival Skin",
            "Winter Festival", heroTia,
            false, true, false, true, false, 139, 28);
    private final Skin skinTia5 = new Skin(167L, "Zephyr Queen Tia",
            "https://drive.google.com/uc?id=1OYwU1s9paBHBa5fiK3PXrP_HW8muq6bz",
            "https://drive.google.com/uc?id=1RNciNbq-gUcPu0NYipsBK4W5Kmvmmavu",
            "https://drive.google.com/uc?id=1vEcHq7-uX9kVo2-e03fIANrbvmQ6Zc8h",
            "The harshest of storms start from gentle zephyrs and so do the steps of purification that embark from the eternity of the west",
            "King God Pass", heroTia,
            true, true, true, true, true, -1, -1);
    private final Skin skinMirsyl1 = new Skin(168L, "Mirsyl the Sage of the Forest",
            "https://drive.google.com/uc?id=1Oiw_HE5Qw71yon0pl-ESpZmRYshj8XRD",
            "",
            "",
            "Wood elves are deeply bound to the earth, forest and source of life. During spring when greeneries and living things flourish, " +
                    "Mirsyl's power becomes stronger",
            "", heroMirsyl,
            false, true, false, false, true, -1, -1);
    private final Skin skinMirsyl2 = new Skin(169L, "Summer Beach Mirsyl",
            "https://drive.google.com/uc?id=1j85PSTknl6OA1D-V1H6IQakfVaXWzpGV",
            "",
            "",
            "Mirsyl can borrow the power of nature in all forms as long as the place she is in is teeming with vitality. When summer comes, she" +
                    " creates a lush beach in the middle of the deep forest for her forest friends",
            "Wave Festival", heroMirsyl,
            false, true, false, true, false, 119, 24);
    private final Skin skinMirsyl3 = new Skin(170L, "Slime's Friend Mirsyl",
            "https://drive.google.com/uc?id=1QI4otTJ8azAjUJ4tcUPciaic-oHkujDh",
            "",
            "",
            "At times, slimes that get lost would find themselves in the depths of Mirsyl's forest. Although she strongly rejects any outsiders," +
                    " she became good friends with the slimes who had no ill intent. Now she protects the forest with tme",
            "Northern Monster Festival", heroMirsyl,
            false, true, false, false, false, 119, 24);
    private final Skin skinMirsyl4 = new Skin(171L, "Pristine Winter Mirsyl",
            "https://drive.google.com/uc?id=1XplAFDM-l-YSlMtoznQUCqzHVL-y7iuQ",
            "",
            "",
            "2022 Winter Festival Skin",
            "Winter Festival", heroMirsyl,
            false, true, false, false, false, 119, 24);
    private final Skin skinTaesan1 = new Skin(172L, "Taegeuk Taesan",
            "https://drive.google.com/uc?id=1cp7fcoYTxuh3f1QYROHCupue-KILrWt-",
            "",
            "",
            "Mount White Tiger is a shrine for White Tiger, one of the Four Divine Creatures. It is also known as the birthplace and sacred" +
                    " location of numerous martial arts. Born in such a place. Taesan has practiced all martial arts that exist on the continent " +
                    "including those from the Elves of the west and those of the East.",
            "source", heroTaesan,
            false, true, false, false, true, -1, -1);
    private final Skin skinTaesan2 = new Skin(173L, "Franken Taesan",
            "https://drive.google.com/uc?id=1aJ_FrkOA5KNaaycFDOTbe_NYMvPtplg8",
            "",
            "",
            "2022 Halloween Event Skin",
            "Halloween Festival", heroTaesan,
            false, true, true, true, false, 119, 24);
    private final Skin skinTaesan3 = new Skin(174L, "Fishing Festival Taesan",
            "https://drive.google.com/uc?id=1pJ-6f-6RnsgzSwkFDJMqrl9d5-pKWxg8",
            "",
            "",
            "2023 Wave Festival Limited Skin",
            "Wave Festival", heroTaesan,
            false, true, true, true, false, 139, 28);
    private final Skin skinNeria1 = new Skin(175L, "Neria the Imperial Chaser",
            "https://drive.google.com/uc?id=1xp20ds86eZAtBhp1b3QcRMg64jU2rNLs",
            "",
            "",
            "To find the cursed ones hiding in all quarters of the continent, Neria maintains a close relationship with the empire that rules" +
                    " over the largest teritory. In return for her help, she acquires information on her enemies such as their hideouts and weaknesses",
            "", heroNeria,
            false, true, false, false, true, -1, -1);
    private final Skin skinNeria2 = new Skin(176L, "Lilium Neria",
            "https://drive.google.com/uc?id=1ndWr6NB7x0WUfouDXC6iezW4JJH_H9zi",
            "",
            "",
            "2023 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroNeria,
            false, true, false, true, false, 139, 28);
    private final Skin skinNeria3 = new Skin(177L, "Elf Neria",
            "https://drive.google.com/uc?id=1wrruzTWwPZiWsViQV1hYLrP5EuNA5M0i",
            "",
            "",
            "2023 Northern Monster Festival Skin",
            "Northern Monster Festival", heroNeria,
            false, true, false, false, false, 119, 24);
    private final Skin skinHaerang1 = new Skin(178L, "Shrouded Haerang",
            "https://drive.google.com/uc?id=1J38vBsEvk54RHAESqLxwarWYo31lJIY5",
            "",
            "",
            "Although Haerang seems to show no interest in the secular world, at times, he would be gone like the winds and reappear at all" +
                    " corners of the continent to find out the state of affairs. Thus, he is well-informed about the matters of the continent while" +
                    " living in the far east",
            "", heroHaerang,
            false, true, false, false, true, -1, -1);
    private final Skin skinHaerang2 = new Skin(179L, "Jack O'Lantern Haerang",
            "https://drive.google.com/uc?id=1e2BwZ9FxWykxQx8PL8TVUTh0YhTh8Gg8",
            "",
            "",
            "2022 Halloween Event Skin",
            "Halloween Festival", heroHaerang,
            false, true, true, true, false, 139, 28);
    private final Skin skinHaerang3 = new Skin(180L, "Adviser Haerang",
            "https://drive.google.com/uc?id=1wQW0q5ffYJ_aIsRe4N7rxkef88qdCDoe",
            "",
            "",
            "2023 East Martial Tournament Skin",
            "East Martial Tournament", heroHaerang,
            false, true, false, false, false, 119, 24);
    private final Skin skinHaerang4 = new Skin(181L, "Seadragon King Haerang",
            "https://drive.google.com/uc?id=16uftYU2wIUmoeYh8mmPru__iPEUJA8tG",
            "https://drive.google.com/uc?id=1vRpe7zBSm40HgN3TUCBbAzYuGnnQRuUf",
            "https://drive.google.com/uc?id=1WLKTW1TG_MmPrR1Vd66BnNiG1qaBOtmX",
            "The first wave shall be mellow, the second, fierce and the third, merciless",
            "King God Pass", heroHaerang,
            true, true, true, true, true, -1, -1);
    private final Skin skinGidnil1 = new Skin(182L, "Gidnil the Gauntlet of the Empire",
            "https://drive.google.com/uc?id=1WLKTW1TG_MmPrR1Vd66BnNiG1qaBOtmX",
            "",
            "",
            "As a knight of the Holy Church and a citizen of the Empire, there are times when Gidnil would fight in wars as part of the Imperial" +
                    " Army. The black imperial armor he wears instead of the white armor of the Holy Church clearly shows what he is: a natural-born soldier",
            "", heroGidnil,
            false, true, false, false, false, -1, -1);
    private final Skin skinGidnil2 = new Skin(183L, "Snowball Fight Gidnil",
            "https://drive.google.com/uc?id=1e4uQjH1Gqk93YVA2D0rc50OtD9s9lIn0",
            "",
            "",
            "2022 Holiday Skin",
            "Holiday Event", heroGidnil,
            false, true, false, false, false, 119, 24);
    private final Skin skinGidnil3 = new Skin(184L, "Iron Fist Gidnil",
            "https://drive.google.com/uc?id=1c2JteqT0zWa-pRvI_Cf1M1ucIOxLqyb6",
            "",
            "",
            "2023 Northern Monster Festival Skin",
            "Northern Monster Festival", heroGidnil,
            false, true, false, false, false, 119, 24);
    private final Skin skinKanak1 = new Skin(185L, "Frost Tribe Kanak",
            "https://drive.google.com/uc?id=1HRatkmNGVPvBN5xyEP3qimn7wZQlPAML",
            "",
            "",
            "The dark side of Kanak will never let go of its rage and sorrow even in the bone-chilling cold. With the Forest Tribe's piercing fury," +
                    " Kanak rips into his target's soul",
            "source", heroKanak,
            false, true, false, false, true, -1, -1);
    private final Skin skinKanak2 = new Skin(186L, "Black Phantom Kanak",
            "https://drive.google.com/uc?id=1aRX7HrPvCztPnCeajDgsCiPFDKCLFsTp",
            "",
            "",
            "2023 East Martial Tournament Skin",
            "East Martial Tournament", heroKanak,
            false, true, false, false, false, 119, 24);
    private final Skin skinKanak3 = new Skin(187L, "Wraith Kanak",
            "https://drive.google.com/uc?id=10Vjr2KGyWbx7KEXuJMJXP3BjFjxwCe7X",
            "",
            "",
            "2023 Northern Monster Festival Skin",
            "Northern Monster Festival", heroKanak,
            false, true, true, true, false, 139, 28);
    private final Skin skinRie1 = new Skin(188L, "Princess Rie",
            "https://drive.google.com/uc?id=1af4rQehwMptgOEsEqNQFhxxTAakNq_oD",
            "",
            "",
            "Ren may as well be the coldest person to others, but she shows the warmest love for her little sister. She always dresses Rie in" +
                    " bright clothes and wishes her to be in the light instead of the darkness",
            "", heroRie,
            false, true, false, false, true, -1, -1);
    private final Skin skinRie2 = new Skin(189L, "Lunar New Year Greetings Rie",
            "https://drive.google.com/uc?id=1NBEzQK5CZZpVBDc7-MQHIc46tUuR0So9",
            "",
            "",
            "2023 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroRie,
            false, true, true, true, false, 139, 28);
    private final Skin skinRie3 = new Skin(190L, "Devil Rie",
            "https://drive.google.com/uc?id=1s5CskZEO7cJ-lWA7OXJgE9mEjPy4NNrS",
            "",
            "",
            "2023 Divine Festival Skin",
            "Divine Festival", heroRie,
            false, true, true, true, false, 139, 28);
    private final Skin skinRie4 = new Skin(191L, "Rie the Queen of Hearts",
            "https://drive.google.com/uc?id=13K7bE1GRBZvwDnKCfb2gTKznpKYJLLGB",
            "",
            "",
            "2023 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroRie,
            false, true, true, false, false, 139, 28);
    private final Skin skinMachina1 = new Skin(192L, "Princess Machina",
            "https://drive.google.com/uc?id=1k2oZAcHQnnM7tPVNbBCqPcZxPjKELB4c",
            "",
            "",
            "Ren may as well be the coldest person to others, but she shows the warmest love for her little sister. She always dresses Rie in" +
                    " bright clothes and wishes her to be in the light instead of the darkness",
            "", heroMachina,
            false, true, false, false, true, -1, -1);
    private final Skin skinMachina2 = new Skin(193L, "Lunar New Year Greetings Machina",
            "https://drive.google.com/uc?id=1rUj9SDMd3kd0Gl5ciSThlTZr_u94Y3Te",
            "",
            "",
            "2023 Lunar New Year Greetings Skin",
            "Lunar New Year Greetings Festival", heroMachina,
            false, true, true, true, false, 139, 28);
    private final Skin skinMachina3 = new Skin(194L, "Devil Machina",
            "https://drive.google.com/uc?id=1vTbVrvNPo1INPob1hDuF7lH4uBcj3RsH",
            "",
            "",
            "2023 Divine Festival Skin",
            "Divine Festival", heroMachina,
            false, true, true, true, false, 139, 28);
    private final Skin skinMachina4 = new Skin(195L, "Machina the Queen of Hearts",
            "https://drive.google.com/uc?id=1RE0SJXHB7yILPR9QYvsiw2Lb0IY1PqSi",
            "",
            "",
            "2023 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroMachina,
            false, true, true, false, false, 139, 28);
    private final Skin skinNibella1 = new Skin(196L, "Desert Usurper Nibella",
            "https://drive.google.com/uc?id=1k52J-sndPp6pOak-44uUSlpcyzUSkDZj",
            "",
            "",
            "Worshipped by the outlaws, Nibella finally became the Empress of the Wasteland. Merchants who passed by her lands would always" +
                    " stay vigilant, watching out for sandstorms over the horizon that might come their way",
            "", heroNibella,
            false, true, false, false, true, -1, -1);
    private final Skin skinNibella2 = new Skin(197L, "Lavender Nibella",
            "https://drive.google.com/uc?id=1gbeZlo0_pq6f-HXrlXF0uOgYcSKXCtgJ",
            "",
            "",
            "2023 Flower Blooming Festival Skin",
            "Flower Blooming Festival", heroNibella,
            false, true, false, false, false, 119, 24);
    private final Skin skinNibella3 = new Skin(198L, "Fishing Festival Nibella",
            "https://drive.google.com/uc?id=1TtnZ7bURV7jfO1men7TgfjaTMy02qiLk",
            "",
            "",
            "2023 Wave Festival Limited Skin",
            "Wave Festival", heroNibella,
            false, true, false, true, false, 139, 28);
    private final Skin skinTaebaek1 = new Skin(199L, "Taegeuk Taebaek",
            "https://drive.google.com/uc?id=1iN_In3XPSrtMwS1ZhM--NtnYpecMKSQh",
            "",
            "",
            "Taebaek is indeed aggressive and in love with fights, but his expertise is no other than the art of patient retaliation. Though" +
                    " he might look a bit careless, he has trained his body for years to withstand the attacks of his enemies until he can land his" +
                    " 'final comeback'",
            "", heroTaebaek,
            false, true, false, false, true, -1, -1);
    private final Skin skinTaebaek2 = new Skin(200L, "Fallen Angel Taebaek",
            "https://drive.google.com/uc?id=1aHFpX2hpdR9yxxl46qW6WO8rlKZ3TOQQ",
            "",
            "",
            "2023 Divine Festival Skin",
            "Divine Festival", heroTaebaek,
            false, true, true, true, false, 139, 28);
    private final Skin skinCathy1 = new Skin(201L, "Knight Cathy",
            "https://drive.google.com/uc?id=1tjP4jkwPtpjgwmDhqTzYFRBisPfKh35N",
            "",
            "",
            "Cathy has no regard for events that don't bring her huge profits, but for reasons unknown, she would sometimes show up to major" +
                    " incidents all over the continent. Not only that, she would sometimes become a mercenary and join a side to fight in wars",
            "", heroCathy,
            false, true, false, false, true, -1, -1);
    private final Skin skinCathy2 = new Skin(202L, "Blue Wave Cathy",
            "https://drive.google.com/uc?id=1zC-eD7p3StqEt4usevKTdC7qe21Yn2bM",
            "",
            "",
            "2023 Wave Festival Limited Skin",
            "Wave Festival", heroCathy,
            false, true, false, true, true, -1, -1);
    private final Skin skinEsthea1 = new Skin(203L, "Knight Esthea",
            "https://drive.google.com/uc?id=1-N9oMNzRa2MgbM-d8ny5fG_s6NP1Vkmk",
            "",
            "",
            "Esthea might be reserved, but her tenderness earns her welcome wherever she goes. When she is occasionally dispatched to heal the wounded " +
                    "soldiers, there would be several times more visitors",
            "", heroEsthea,
            false, true, false, false, true, -1, -1);
    private final Skin skinEsthea2 = new Skin(204L, "Saint Esthea",
            "https://drive.google.com/uc?id=1VxCga3DJ0mkmD3v6mpBA6JkJBKIKt5Ki",
            "",
            "",
            "2023 Divine Festival Skin",
            "Divine Festival", heroEsthea,
            false, false, false, false, false, 119, 24);
    private final Skin skinEsthea3 = new Skin(205L, "Poison Apple Esthea",
            "https://drive.google.com/uc?id=1lAPepkNmdRFGWD6pQBHSBsH2M8esevmw",
            "",
            "",
            "2023 Fairy Tale Festival Skin",
            "Fairy Tale Festival", heroEsthea,
            false, true, false, true, false, 139, 28);
    private final Skin skinBaldir1 = new Skin(206L, "Frost Tribe Baldir",
            "https://drive.google.com/uc?id=1RhAS6z03FtnPjhOhkTmQpIDEwgU8tdJo",
            "",
            "",
            "The time of Baldir's birth and the nature of his existence is a sheer mystery. He used to appear in a new form in blistering winters," +
                    " preventing the spark of life from fading away by reviving the dying plants",
            "", heroBaldir,
            false, true, true, false, true, -1, -1);
    private final Skin skinNatureFury1 = new Skin(207L, "Frost Tribe Nature Fury",
            "https://drive.google.com/uc?id=1ERrEogVq1hkfeIq-a6PC-cP3Cy9pFs8q",
            "",
            "",
            "The time of Baldir's birth and the nature of his existence is a sheer mystery. He used to appear in a new form in blistering winters," +
                    " preventing the spark of life from fading away by reviving the dying plants",
            "", heroBaldirNatureFuryForm,
            false, true, true, false, true, -1, -1);
    private final Skin skinEternalWisdom1 = new Skin(208L, "Frost Tribe Eternal Wisdom",
            "https://drive.google.com/uc?id=1wkeoKSAY-gwr1DIbMs5QYmwkpfESM169",
            "",
            "",
            "The time of Baldir's birth and the nature of his existence is a sheer mystery. He used to appear in a new form in blistering winters," +
                    " preventing the spark of life from fading away by reviving the dying plants",
            "", heroBaldirEternalWisdomForm,
            false, true, true, false, true, -1, -1);
    private final Skin skinIan1 = new Skin(209L, "Knight Ian",
            "https://drive.google.com/uc?id=1L40TBXMfpqtohD-_fxWanqlIwjBJT-5D",
            "",
            "",
            "Ian's village, located at the southern borders, was invaded by monsters that appeared out of nowhere in the year Mel became" +
                    " an adult. After losing everything he had, including his family, Ian took his steps to the knights of the central region to protect" +
                    " the continent, holding onto his grandfather's legacy",
            "", heroIan,
            false, true, false, false, true, -1, -1);
    private final Skin skinOphelia1 = new Skin(210L, "Noble Ophelia",
            "https://drive.google.com/uc?id=1Es1Qdm9Eehmi5dXRJumd1S4E4SQvjxwg",
            "",
            "",
            "Known for her stunning beauty, Ophelia was originally a lady from a noble family who moves to the north from the central region." +
                    " She disappeared one day and according to rumors, her carriage had been suddenly attacked",
            "", heroOphelia,
            false, true, false, false, true, -1, -1);
    private final Skin skinOphelia2 = new Skin(211L, "Void Destroyer Ophelia",
            "https://drive.google.com/uc?id=1Ga7W1ZvppDSh-QVFn78JUN89V4J-xFY3",
            "",
            "",
            "2023 Northern Monster Festival Skin",
            "Northern Monster Festival", heroOphelia,
            false, true, false, true, false, 139, 28);
    private final Skin skinKirdan1 = new Skin(212L, "Golden Blade Kirdan",
            "https://drive.google.com/uc?id=1zM-nPcA6_qG3tCHHJqVOmOQag_C1RFWL",
            "",
            "",
            "The ancient remains of the north may now lie in ruins, but there once stood a glorious kingdom of gold. Old scripts tell the tale" +
                    " of how the kingdom rules the north with warriors armed in gold. However, it's legacy is nowhere to be found except the swordsmanship" +
                    " of those warriors",
            "", heroKirdan,
            false, true, false, false, true, -1, -1);


//    private final Skin skin = new Skin(0L, "skin",
//            "https://drive.google.com/uc?id=",
//            "https://drive.google.com/uc?id=",
//            "https://drive.google.com/uc?id=",
//            "skindescription",
//            "source", hero,
//            false, false, false, false, false, 0, 0);




    @EventListener(ApplicationReadyEvent.class)
    public void init(){

        // Skills
        heroSkillRepository.saveAll(Arrays.asList(skillShelda, skillDaniel, skillLeonhardt, skillEvan, skillPriya, skillAramis,
                skillAlberon, skillZuoYun, skillDraco, skillBardrey, skillMara, skillHansi, skillLyca, skillLycanthrope, skillJol, skillLily,
                skillLilyAndShaSha, skillZupitere, skillAsiaq, skillBehemus, skillYeon, skillMel, skillBombie, skillLuniare, skillRossette,
                skillCain, skillChungAh, skillRahawk, skillRen, skillAgathe, skillHela, skillZuoBai, skillTia, skillMirsyl, skillTaesan, skillNeria,
                skillHaerang, skillGidnil, skillKanak, skillRie, skillMachina, skillNibella, skillTaebaek, skillCathy, skillEsthea, skillBaldir,
                skillNaturesFury, skillEternalWisdom, skillIan, skillOphelia, skillKirdan));

        // Regions
        regionRepository.saveAll(Arrays.asList(regionCentral, regionEast, regionNorth, regionSouth, regionWest));

        // Hero Classes
        heroClassRepository.saveAll(Arrays.asList(heroClassTenacity, heroClassCourage, heroClassElement, heroClassSwiftness, heroClassMystique, heroClassShadow));

        // Heroes
        heroRepository.saveAll(Arrays.asList(heroShelda, heroDaniel, heroLeonhardt, heroEvan, heroPriya, heroAramis, heroAlberon, heroZuoYun, heroDraco,
                heroBardrey, heroMara, heroHansi, heroLyca, heroLycanthrope, heroJol, heroLily, heroLilyAndShaSha, heroZupitere, heroAsiaq, heroBehemus,
                heroYeon, heroMel, heroBombie, heroLuniare, heroRossette, heroCain, heroChungAh, heroRahawk, heroRen, heroAgathe, heroHela, heroZuoBai,
                heroTia, heroMirsyl, heroTaesan, heroNeria, heroHaerang, heroGidnil, heroKanak, heroRie, heroMachina, heroNibella, heroTaebaek,
                heroCathy, heroEsthea, heroBaldir, heroBaldirNatureFuryForm, heroBaldirEternalWisdomForm, heroIan, heroOphelia, heroKirdan));

        // Awakenings
        awakeningRepository.saveAll(Arrays.asList(awakeningShelda1, awakeningShelda2, awakeningDaniel1, awakeningDaniel2, awakeningLeonhardt1,
                awakeningLeonhardt2, awakeningEvan1, awakeningEvan2, awakeningPriya1, awakeningPriya2, awakeningAramis1, awakeningAramis2, awakeningAlberon1,
                awakeningAlberon2, awakeningZuoYun1, awakeningZuoYun2, awakeningDraco1, awakeningDraco2, awakeningBardrey1, awakeningBardrey2,
                awakeningMara1, awakeningMara2, awakeningHansi1, awakeningHansi2, awakeningLyca1, awakeningLyca2, awakeningJol1, awakeningJol2,
                awakeningLily1, awakeningLily2, awakeningZupitere1, awakeningZupitere2, awakeningAsiaq1, awakeningAsiaq2, awakeningBehemus1,
                awakeningBehemus2, awakeningYeon1, awakeningYeon2, awakeningMel1, awakeningMel2, awakeningBombie1, awakeningBombie2,
                awakeningLuniare1, awakeningLuniare2, awakeningRossette1, awakeningRossette2, awakeningCain1, awakeningCain2, awakeningChungAh1,
                awakeningChungAh2, awakeningRahawk1, awakeningRahawk2, awakeningRen1, awakeningRen2, awakeningAgathe1, awakeningAgathe2, awakeningHela1,
                awakeningHela2, awakeningZuoBai1, awakeningZuoBai2, awakeningTia1, awakeningTia2, awakeningMirsyl1, awakeningMirsyl2, awakeningTaesan1,
                awakeningTaesan2, awakeningNeria1, awakeningNeria2, awakeningHaerang1, awakeningHaerang2, awakeningGidnil1, awakeningGidnil2,
                awakeningKanak1, awakeningKanak2, awakeningRie1, awakeningRie2, awakeningNibella1, awakeningNibella2, awakeningTaebaek1, awakeningTaebaek2,
                awakeningCathy1, awakeningCathy2, awakeningEsthea1, awakeningEsthea2, awakeningBaldir1, awakeningBaldir2, awakeningIan1, awakeningOphelia1,
                awakeningKirdan1, awakeningIan2));

        // Skins
        skinRepository.saveAll(Arrays.asList(skinShelda1, skinShelda2, skinShelda3, skinShelda4, skinShelda5, skinDaniel1, skinDaniel2, skinDaniel3, skinDaniel4,
                skinDaniel5, skinLeonhardt1, skinLeonhardt2, skinLeonhardt3, skinLeonhardt4, skinLeonhardt5, skinEvan1, skinEvan2, skinEvan3, skinEvan4,
                skinEvan5, skinPriya1, skinPriya2, skinPriya3, skinPriya4, skinPriya5, skinPriya6, skinPriya7, skinAramis1, skinAramis2, skinAramis3,
                skinAramis4, skinAramis5, skinAramis6, skinAlberon1, skinAlberon2, skinAlberon3, skinAlberon4, skinAlberon5, skinZuoYun1, skinZuoYun2,
                skinZuoYun3, skinZuoYun4, skinZuoYun5, skinDraco1, skinDraco2, skinDraco3, skinDraco4, skinBardrey1, skinBardrey2, skinBardrey3, skinBardrey4,
                skinMara1, skinMara2, skinMara3, skinMara4, skinMara5, skinMara6, skinHansi1, skinHansi2, skinHansi3, skinHansi4, skinHansi5, skinHansi6,
                skinLyca1, skinLyca2, skinLyca3, skinLyca4, skinLyca5, skinLycanthrope1, skinLycanthrope2, skinLycanthrope3, skinLycanthrope4, skinLycanthrope5,
                skinJol1, skinJol2, skinJol3, skinJol4, skinJol5, skinJol6, skinLily1, skinLily2, skinLily3, skinLily4, skinLily5, skinLily6, skinShasha1,
                skinShasha2, skinShasha3, skinShasha4, skinShasha5, skinShasha6, skinZupitere1, skinZupitere2, skinZupitere3, skinZupitere4, skinAsiaq1,
                skinAsiaq2, skinAsiaq3, skinAsiaq4, skinAsiaq5, skinBehemus1, skinBehemus2, skinBehemus3, skinBehemus4, skinBehemus5, skinYeon1, skinYeon2,
                skinYeon3, skinYeon4, skinYeon5, skinMel1, skinMel2, skinMel3, skinMel4, skinMel5, skinMel6, skinBombie1, skinBombie2, skinBombie3, skinBombie4,
                skinBombie5, skinLuniare1, skinLuniare2, skinLuniare3, skinLuniare4, skinRossette1, skinRossette2, skinRossette3, skinRossette4, skinRossette5,
                skinCain1, skinCain2, skinCain3, skinCain4, skinCain5, skinChungAh1, skinChungAh2, skinChungAh3, skinChungAh4, skinRahawk1, skinRahawk2,
                skinRahawk3, skinRahawk4, skinRen1, skinRen2, skinRen3, skinRen4, skinRen5, skinAgathe1, skinAgathe2, skinAgathe3, skinAgathe4, skinHela1,
                skinHela2, skinHela3, skinHela4, skinHela5, skinZuoBai1, skinZuoBai2, skinZuoBai3, skinZuoBai4, skinZuoBai5, skinTia1, skinTia2, skinTia3,
                skinTia4, skinTia5, skinMirsyl1, skinMirsyl2, skinMirsyl3, skinMirsyl4, skinTaesan1, skinTaesan2, skinTaesan3, skinNeria1, skinNeria2, skinNeria3,
                skinHaerang1, skinHaerang2, skinHaerang3, skinHaerang4, skinGidnil1, skinGidnil2, skinGidnil3, skinKanak1, skinKanak2, skinKanak3, skinRie1,
                skinRie2, skinRie3, skinRie4, skinMachina1, skinMachina2, skinMachina3, skinMachina4, skinNibella1, skinNibella2, skinNibella3, skinTaebaek1,
                skinTaebaek2, skinCathy1, skinCathy2, skinEsthea1, skinEsthea2, skinEsthea3, skinBaldir1, skinNatureFury1, skinEternalWisdom1, skinIan1,
                skinOphelia1, skinOphelia2, skinKirdan1));



    }

}
