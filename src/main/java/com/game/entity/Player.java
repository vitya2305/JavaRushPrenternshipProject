package com.game.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "player")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "banned")
    private Boolean banned;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "level")
    private Integer level;

    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;

    public Player() {
    }
    public Player(Long id, String name,
                  String title, Race race,
                  Profession profession, Date birthday,
                  Boolean banned, Integer experience,
                  Integer level, Integer untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        this.birthday = birthday;
        this.banned = banned;
    }

    public int calcCurrentLevel(){
        int level = (int)(Math.sqrt(2500 + 200 * experience) - 50) / 100;
        this.level = level;
        return level;
    }

    public int calcUntilNextLevel(){
        int untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        this.untilNextLevel = untilNextLevel;
        return untilNextLevel;
    }

    public void changePlayer(Player playerWithChanges){
        if (playerWithChanges.getName() != null){
            this.setName(playerWithChanges.getName());
        }
        if (playerWithChanges.getTitle() != null){
            this.setTitle(playerWithChanges.getTitle());
        }
        if (playerWithChanges.getRace() != null){
            this.setRace(playerWithChanges.getRace());
        }
        if (playerWithChanges.getProfession() != null){
            this.setProfession(playerWithChanges.getProfession());
        }
        if (playerWithChanges.getBirthday() != null){
            this.setBirthday(playerWithChanges.getBirthday());
        }
        if (playerWithChanges.getBanned() != null){
            this.setBanned(playerWithChanges.getBanned());
        }
        if (playerWithChanges.getExperience() != null){
            this.setExperience(playerWithChanges.getExperience());
        }
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}