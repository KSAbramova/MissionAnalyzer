package mephi.MissionAnalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sorcerers")
public class SorcererEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    public enum Rank {
        GRADE_2, GRADE_1, SEMI_GRADE_1, SPECIAL_GRADE
    }

    public SorcererEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Rank getRank() { return rank; }
    public void setRank(Rank rank) { this.rank = rank; }
}