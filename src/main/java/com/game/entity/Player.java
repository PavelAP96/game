package com.game.entity;

import com.game.exception_handling.exceptions.NotValidRequestException;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private Race race;

    @Column
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column
    private Date birthday;

    @Column
    private Boolean banned = false;

    @Column
    private Integer experience;

    @Column
    private Integer level;

    @Column
    private Integer untilNextLevel;

    public Player() {
    }

    public Player(String name, String title, Race race, Profession profession, Date birthday, boolean banned, int experience, int level, int untilNextLevel) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
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
        if (name == null)
            throw new NotValidRequestException();
        this.name = name;
    }

    public String getTittle() {
        return title;
    }

    public void setTittle(String title) {
        if (title == null)
            throw new NotValidRequestException();
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        if (race == null)
            throw new NotValidRequestException();
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        if (profession == null)
            throw new NotValidRequestException();
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        if (birthday == null)
            throw new NotValidRequestException();

        this.birthday = birthday;
    }

    public Boolean isBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        if (experience == null)
            throw new NotValidRequestException();

        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }
}
