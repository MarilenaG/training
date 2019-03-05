package com.training.fullstack.mentor.model;

import com.training.fullstack.admin.model.Skill;

import javax.persistence.*;



@Entity
@Table(name = "mentor_skills")
public class MentorSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Column(name = "skill_rating")
    private Integer skillRating;
    @Column(name = "years_experience")
    private Integer yearsExperience;
    @Column(name = "trainings_delivered")
    private Integer trainingsDelivered;

    @Column (name="fee")
    private Double fee;

    public MentorSkill( Skill skill, Integer skillRating, Integer yearsExperience, Integer trainingsDelivered, Double fee) {
        this.skill = skill;
        this.skillRating = skillRating;
        this.yearsExperience = yearsExperience;
        this.trainingsDelivered = trainingsDelivered;
        this.fee = fee;
    }

    public MentorSkill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(Integer skillRating) {
        this.skillRating = skillRating;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public Integer getTrainingsDelivered() {
        return trainingsDelivered;
    }

    public void setTrainingsDelivered(Integer trainingsDelivered) {
        this.trainingsDelivered = trainingsDelivered;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}