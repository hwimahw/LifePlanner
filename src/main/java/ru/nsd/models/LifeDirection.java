package ru.nsd.models;

import java.util.Objects;

public class LifeDirection {

    private long id;

    private long userId;

    private int level;

    private String name;

    private Integer number;

    private Integer parentNumber;

    public LifeDirection(long id, long userId, int level,
                         String name, Integer number, Integer parentNumber) {
        this.id = id;
        this.userId = userId;
        this.level = level;
        this.name = name;
        this.number = number;
        this.parentNumber = parentNumber;
    }

    public LifeDirection(long userId, int level,
                         String name, Integer number, Integer parentNumber) {
        this.userId = userId;
        this.level = level;
        this.name = name;
        this.number = number;
        this.parentNumber = parentNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(Integer parentNumber) {
        this.parentNumber = parentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifeDirection that = (LifeDirection) o;
        return id == that.id &&
                userId == that.userId &&
                level == that.level &&
                Objects.equals(name, that.name) &&
                Objects.equals(number, that.number) &&
                Objects.equals(parentNumber, that.parentNumber);
    }
}
