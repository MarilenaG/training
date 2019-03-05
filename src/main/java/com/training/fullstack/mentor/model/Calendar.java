package com.training.fullstack.mentor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "mentor_calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
    @JsonProperty
    @Column(name = "start_Time" )
    private LocalTime startTime;
    @JsonProperty
    @Column(name = "end_time")
    private LocalTime endTime;
    @JsonProperty
    @Column(name = "start_Date" )
    private LocalDate startDate;
    @JsonProperty
    @Column(name = "end_date")
    private LocalDate endDate;

    public Calendar() {
    }

    public Calendar(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
