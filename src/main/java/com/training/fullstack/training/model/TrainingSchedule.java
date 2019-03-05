package com.training.fullstack.training.model;

import com.training.fullstack.mentor.model.Mentor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "training_schedule")
public class TrainingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Column(name = "start_Time" )
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;


    @Column(name = "training_date")
    private LocalDate trainingDate;

    public TrainingSchedule(LocalTime startTime, LocalTime endTime, LocalDate trainingDate) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.trainingDate = trainingDate;
    }

    public TrainingSchedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }
}
