package com.training.fullstack.training.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.mentor.controller.MentorRepresentation;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.training.model.Training;
import com.training.fullstack.training.model.TrainingSchedule;
import com.training.fullstack.training.model.TrainingStatus;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingRepresentation {
    @JsonProperty  Long id;
    @JsonProperty  Long mentorId;
    @JsonProperty  Long userId ;
    @JsonProperty  Long skillId;
    @JsonProperty  String mentorName;
    @JsonProperty  String userName ;
    @JsonProperty  String skillTitle;
    @JsonProperty  String rejectComments;
    @JsonProperty  TrainingStatus status;
    @JsonProperty  Integer progress;
    @JsonProperty  Integer payedPercentage;
    @JsonProperty  Integer rating;
    @JsonProperty  List<TrainingScheduleRepresentation> schedules;





    static class TrainingScheduleRepresentation{
        @JsonProperty  Long id;
        @JsonProperty  private LocalTime startTime;
        @JsonProperty private LocalTime endTime;
        @JsonProperty private LocalDate trainingDate;


        public TrainingSchedule toTrainingSchedule(){
            TrainingSchedule ts = new TrainingSchedule();
            BeanUtils.copyProperties(this, ts);
            return ts ;
        }

        public static TrainingScheduleRepresentation fromTrainingSchedule(TrainingSchedule ts){
            TrainingScheduleRepresentation tsr = new TrainingScheduleRepresentation();
            BeanUtils.copyProperties(ts, tsr);
            return tsr;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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


    public Training toTraining(){
        Training training = new Training();
        BeanUtils.copyProperties(this, training);
        this.schedules.stream().map(TrainingScheduleRepresentation::toTrainingSchedule).forEach((ts)-> training.addSchedule(ts));
        return training ;
    }

    public static TrainingRepresentation fromTraining(Training training){
        TrainingRepresentation trainingRepresentation = new TrainingRepresentation();
        BeanUtils.copyProperties(training, trainingRepresentation);
        trainingRepresentation.schedules = training.getSchedules().stream().map(TrainingScheduleRepresentation::fromTrainingSchedule).collect(Collectors.toList());
        return trainingRepresentation;
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

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getRejectComments() {
        return rejectComments;
    }

    public void setRejectComments(String rejectComments) {
        this.rejectComments = rejectComments;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getPayedPercentage() {
        return payedPercentage;
    }

    public void setPayedPercentage(Integer payedPercentage) {
        this.payedPercentage = payedPercentage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<TrainingScheduleRepresentation> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TrainingScheduleRepresentation> schedules) {
        this.schedules = schedules;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }
}

