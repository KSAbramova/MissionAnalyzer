package model;

public class Sorcerer {

    public enum Rank {
        GRADE_2,
        GRADE_1,
        SEMI_GRADE_1,
        SPECIAL_GRADE
    }

    private String name;
    private Rank rank;

    public Sorcerer() {
    }
    
    public Sorcerer(String name, Rank rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
