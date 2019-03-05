package com.training.fullstack.training.model;

import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.users.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "training")
public class Training implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="mentor_id")
    @NotNull
    private Long mentorId;
    @NotNull
    @Column(name ="user_id")
    private Long userId;
    @Column(name ="skill_id")
    @NotNull
    private Long skillId;

    @Size(max = 256)
    private String rejectComments;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TrainingStatus status;

    @OneToMany(mappedBy ="training", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrainingSchedule> schedules =  new HashSet<TrainingSchedule>();

    @Column(name = "percentage_progress")
    private Integer progress;
    @Column(name = "percentage_payed")
    private Integer payedPercentage;

    private Integer rating;

    public Training() {
    }

    public Training(Long mentorId, Long userId, Long skillId, TrainingStatus status, Integer progress) {
        this.mentorId = mentorId;
        this.userId = userId;
        this.status = status;
        this.skillId = skillId;
        this.progress = progress;
        this.payedPercentage = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }

    public Set<TrainingSchedule> getSchedules() {
        return schedules;
    }
    public void addSchedule(TrainingSchedule schedule) {
        schedule.setTraining(this);
        this.schedules.add(schedule);
    }


    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getRejectComments() {
        return rejectComments;
    }

    public void setRejectComments(String rejectComments) {
        this.rejectComments = rejectComments;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Integer getPayedPercentage() {
        return payedPercentage;
    }

    public void setPayedPercentage(Integer payedPercentage) {
        this.payedPercentage = payedPercentage;
    }
}
