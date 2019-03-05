package com.training.fullstack.mentor.controller;


import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.service.MentorService;
import com.training.fullstack.users.service.SignupService;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/mentor")
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @Autowired
    private SignupService signupService;


    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody MentorRepresentation mentorToSignup) {
        signupService.signup(mentorToSignup.toMentor(), true);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/confirmRegistration")
    public ResponseEntity<MentorRepresentation> confirmRegistration(@RequestParam @NotNull String mentorName , @RequestParam @NotNull  String registrationCode ) {
        if (! signupService.confirmApplicationMember(mentorName,registrationCode, true)) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "MentorName/registration code mismatch");
        }
        Mentor registeredMentor = mentorService.getMentor(mentorName);
        return new ResponseEntity(MentorRepresentation.fromMentor(registeredMentor), HttpStatus.OK);
    }

    @PutMapping("/updateMentorProfile")
    public ResponseEntity<MentorRepresentation> updateMentorProfile(@RequestParam String mentorUserName,@RequestParam Integer yearsExperience,@RequestParam String linkedinUrl,@RequestParam String mobileNumber) {
        Mentor updatedMentor = mentorService.updateMentorProfile(mentorUserName,yearsExperience,linkedinUrl,mobileNumber);
        return new ResponseEntity(MentorRepresentation.fromMentor(updatedMentor), HttpStatus.OK);
    }

    @PutMapping("/addMentorCalendar")
    public ResponseEntity<MentorRepresentation> addMentorCalendar(@RequestParam String mentorUserName , @RequestParam LocalTime startTime,@RequestParam LocalTime endTime, @RequestParam LocalDate startDate ,@RequestParam LocalDate endDate) {
        Mentor updatedMentor = mentorService.addMentorCalendar(mentorUserName,startTime,endTime,startDate,endDate);
        return new ResponseEntity(MentorRepresentation.fromMentor(updatedMentor), HttpStatus.OK);
    }

    @PutMapping("/addMentorSkill")
    public ResponseEntity<MentorRepresentation> addMentorSkill(@RequestParam String mentorUserName,@RequestParam String skillName,@RequestParam Integer skillRating,@RequestParam Integer yearsExperience,@RequestParam Integer trainingsDelivered, @RequestParam Double fee) {
        Mentor updatedMentor = mentorService.addMentorSkill(mentorUserName,skillName,skillRating,yearsExperience, trainingsDelivered,fee);
        return new ResponseEntity(MentorRepresentation.fromMentor(updatedMentor), HttpStatus.OK);
    }

    @GetMapping("/findByTimeAndTechnology")
    public ResponseEntity<List<MentorRepresentation>> searchByTimeAndTechnology (@RequestParam @NotNull String technology, @RequestParam @NotNull LocalTime desiredTime, @RequestParam @NotNull LocalDate desiredDate) {
        List<Mentor> foundMentor = mentorService.searchMentorByTimeAndTechnology(technology,desiredTime,desiredDate);

        List<MentorRepresentation> results = foundMentor.stream()
                .map(MentorRepresentation::fromMentor)
                .collect(toList());
        return ok(results);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<MentorRepresentation>> listAllMentors () {
        List<Mentor> allMentors = mentorService.listAllMentors();
        List<MentorRepresentation> results = allMentors.stream()
                .map(MentorRepresentation::fromMentor)
                .collect(toList());
        return ok(results);
    }



    }
