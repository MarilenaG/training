package com.training.fullstack.training.controller;


import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.training.model.Training;
import com.training.fullstack.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/training")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;


    @PostMapping("/proposeTraining")
    public ResponseEntity<TrainingRepresentation> proposeTraining(@NotNull @RequestParam String userName,
                                                                  @NotNull @RequestParam String mentorName,
                                                                  @NotNull @RequestParam Long skillId,
                                                                  @NotNull @RequestParam LocalDate startDate,
                                                                  @NotNull @RequestParam LocalDate endDate,
                                                                  @NotNull @RequestParam LocalTime startTime,
                                                                  @NotNull @RequestParam LocalTime endTime) {
        Training proposedTraining = trainingService.proposeTraining(userName, mentorName, skillId, startDate, endDate, startTime, endTime);
        return new ResponseEntity(TrainingRepresentation.fromTraining(proposedTraining),HttpStatus.OK);
    }

    @PutMapping("/acceptTraining")
    public ResponseEntity<TrainingRepresentation> acceptTraining(@RequestParam @NotNull Long trainingId  ) {
        Training acceptedTraining = trainingService.acceptTraining(trainingId);
        return new ResponseEntity(TrainingRepresentation.fromTraining(acceptedTraining),HttpStatus.OK);
    }

    @PutMapping("/rejectTraining")
    public ResponseEntity<TrainingRepresentation> rejectTraining(@RequestParam @NotNull Long trainingId ,
                                                                 @RequestParam @NotNull String rejectComments ) {
        Training rejectedTraining = trainingService.rejectTraining(trainingId,rejectComments);
        return new ResponseEntity(TrainingRepresentation.fromTraining(rejectedTraining),HttpStatus.OK);
    }


    @PutMapping("/finalizeTraining")
    public ResponseEntity<TrainingRepresentation> finalizeTraining(@RequestParam @NotNull Long trainingId  ) {
        Training finalizedTraining = trainingService.acceptTraining(trainingId);
        return new ResponseEntity(TrainingRepresentation.fromTraining(finalizedTraining),HttpStatus.OK);
    }

    @PutMapping("/payTraining")
    public ResponseEntity<TrainingRepresentation> payTraining(@RequestParam @NotNull Long trainingId  ) {
        Training payedTraining = trainingService.payTraining(trainingId);
        return new ResponseEntity(TrainingRepresentation.fromTraining(payedTraining),HttpStatus.OK);
    }

    @PutMapping("/rateTraining")
    public ResponseEntity<TrainingRepresentation> rateTraining(@RequestParam @NotNull Long trainingId ,
                                                               @RequestParam @NotNull Integer rating ) {
        Training ratedTraining = trainingService.rateTraining(trainingId,rating);
        return new ResponseEntity(TrainingRepresentation.fromTraining(ratedTraining),HttpStatus.OK);
    }


    @PutMapping("/updateProgressTraining")
    public ResponseEntity<TrainingRepresentation> updateProgress(@RequestParam @NotNull Long trainingId ,
                                                               @RequestParam @NotNull Integer progress ) {
        Training updatedTraining = trainingService.updateProgress(trainingId,progress);
        return new ResponseEntity(TrainingRepresentation.fromTraining(updatedTraining),HttpStatus.OK);
    }


    //                        public List<Training> listTrainingsInProgress (){
//                            public List<Training> listTrainingsByMentor (String mentorName){


    @GetMapping("/findByMentor")
    public ResponseEntity<List<TrainingRepresentation>> listTrainingsByMentor (@RequestParam @NotNull String mentorName) {
        List<Training> trainingsByMentor = trainingService.listTrainingsByMentor(mentorName);

        List<TrainingRepresentation> results = trainingsByMentor.stream()
                .map(TrainingRepresentation::fromTraining)
                .collect(toList());
        return ok(results);
    }

    @GetMapping("/trainingsInProgress")
    public ResponseEntity<List<TrainingRepresentation>> listTrainingsInProgress () {
        List<Training> trainingsInProgress = trainingService.listTrainingsInProgress();

        List<TrainingRepresentation> results = trainingsInProgress.stream()
                .map(TrainingRepresentation::fromTraining)
                .collect(toList());
        return ok(results);
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingRepresentation>> listAllTrainings () {
        List<Training> trainingsInProgress = trainingService.listAllTrainings();

        List<TrainingRepresentation> results = trainingsInProgress.stream()
                .map(TrainingRepresentation::fromTraining)
                .collect(toList());
        return ok(results);
    }



    }
