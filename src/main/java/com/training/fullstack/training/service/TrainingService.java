package com.training.fullstack.training.service;

import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.admin.service.SkillService;
import com.training.fullstack.mentor.infrastructure.MentorRepository;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.payment.PaymentService;
import com.training.fullstack.training.infrastructure.TrainingRepository;
import com.training.fullstack.training.model.Training;
import com.training.fullstack.training.model.TrainingSchedule;
import com.training.fullstack.training.model.TrainingStatus;
import com.training.fullstack.users.infrastructure.UserRepository;
import com.training.fullstack.users.model.User;
import com.training.fullstack.users.service.MailService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class TrainingService {
    private TrainingRepository trainingRepository;
    private UserRepository userRepository;
    private MentorRepository mentorRepository;
    private MailService mailService;
    private PaymentService paymentService;
    private SkillRepository skillRepository;

    // 10 percent of all payments perceived as commision by the website
    private static final int PERCENTAGE_COMMISION = 10;

    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository, MentorRepository mentorRepository, MailService mailService, PaymentService paymentService, SkillRepository skillRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
        this.mailService = mailService;
        this.paymentService = paymentService;
        this.skillRepository = skillRepository;
    }

    public Training proposeTraining(String userName, String mentorName, Long skillId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Mentor mentor = mentorRepository.findByUserName(mentorName).orElseThrow(()->new NoSuchElementException("No mentor with name " + mentorName));
        User user = userRepository.findByUserName(userName).orElseThrow(()->new NoSuchElementException("No user with name " + userName));

        Training training = new Training(user.getId(), mentor.getId(),skillId, TrainingStatus.PROPOSED,0);

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1))
        {
            if (date.getDayOfWeek()!= DayOfWeek.SATURDAY && date.getDayOfWeek()!= DayOfWeek.SUNDAY){
                training.addSchedule( new TrainingSchedule(startTime,endTime,date));
            }
        }
        trainingRepository.save(training);
        mailService.sendSimpleMessage(mentor.getUserName(), "New training proposed", "A new training with id "+ training.getId()+" was proposed by "+ userName+ ". Please login in the application and review/accept it.");
        return training;
    }

    public Training acceptTraining (Long trainingId){
        Training training = trainingRepository.findById(trainingId).orElseThrow(()-> new NoSuchElementException("No training with id "+ trainingId));
        User user = userRepository.findById(training.getUserId()).orElseThrow(()->new NoSuchElementException("No user with id " + training.getUserId()));
        training.setStatus(TrainingStatus.ACCEPTED);
        trainingRepository.save(training);
        mailService.sendSimpleMessage(user.getUserName(), "Training accepted", "Training with id "+ training.getId()+" was accepted by the mentor. Please login in the application and finalize the proposal .");
        return  training;
    }

    public Training finalizeTraining (Long trainingId){
        Training training = trainingRepository.findById(trainingId).orElseThrow(()-> new NoSuchElementException("No training with id "+ trainingId));
        User user = userRepository.findById(training.getUserId()).orElseThrow(()->new NoSuchElementException("No user with id " + training.getUserId()));
        training.setStatus(TrainingStatus.AWAITING_PAYMENT);
        trainingRepository.save(training);
        mailService.sendSimpleMessage(user.getUserName(), "Training finalized", "Training with id "+ training.getId()+" was finalized. Please login in the application and pay before you can attend classes.");
        return  training;
    }
    private Double calculateQuarterlyAmountForMentor( Double totalFee){
        return (totalFee * (1 - PERCENTAGE_COMMISION/100))/4;
    }

    public Training rejectTraining (Long trainingId, String rejectComments){
        Training training = trainingRepository.findById(trainingId).orElseThrow(()-> new NoSuchElementException("No training with id "+ trainingId));
        User user = userRepository.findById(training.getUserId()).orElseThrow(()->new NoSuchElementException("No user with id " + training.getUserId()));
        training.setStatus(TrainingStatus.DENIED);
        training.setRejectComments(rejectComments);
        trainingRepository.save(training);
        mailService.sendSimpleMessage(user.getUserName(), "Training denied", "Training with id "+ training.getId()+" was denied by the mentor. Please login in the application and see the comments.");
        return  training;
    }

    public List<Training> listTrainingsInProgress (){
        return trainingRepository.findAllByStatus(TrainingStatus.STARTED);
    }

    public List<Training> listTrainingsByMentor (String mentorName){
        Mentor mentor = mentorRepository.findByUserName(mentorName).orElseThrow(()->new NoSuchElementException("No mentor with name " + mentorName));
        return trainingRepository.findAllByMentorId(mentor.getId());
    }

    public Training rateTraining (Long trainingId, Integer rating){
        Training training = trainingRepository.findById(trainingId).orElseThrow(()-> new NoSuchElementException("No training with id "+ trainingId));
        training.setRating(rating);
        trainingRepository.save(training);
        return  training;
    }

    @Transactional
    public Training updateProgress (Long trainingId, Integer progress){
        Training training = trainingRepository.findById(trainingId).orElseThrow(()-> new NoSuchElementException("No training with id "+ trainingId));
        training.setProgress( progress);

        Mentor mentor = mentorRepository.findById(training.getMentorId()).orElseThrow(()-> new NoSuchElementException("No mentor with id "+ training.getMentorId()));
        Double fee = getFeeForTheTraining(training, mentor);

        if (progress>=25 & training.getPayedPercentage()< 25) {
            paymentService.payMentorAfterFirstQuarterTraining(mentor.getMentorIBAN(),calculateQuarterlyAmountForMentor(fee));
            training.setPayedPercentage(25);
        }
        if (progress>=50 & training.getPayedPercentage()< 50) {
            paymentService.payMentorAfterSecondQuarterTraining(mentor.getMentorIBAN(),calculateQuarterlyAmountForMentor(fee));
            training.setPayedPercentage(50);
        }
        if (progress>=75 & training.getPayedPercentage()<75) {
            paymentService.payMentorAfterThirdQuarterTraining(mentor.getMentorIBAN(),calculateQuarterlyAmountForMentor(fee));
            training.setPayedPercentage(75);
        }
        if (progress==100 & training.getPayedPercentage()<100) {
            paymentService.payMentorAfterEndTraining(mentor.getMentorIBAN(),calculateQuarterlyAmountForMentor(fee));
            training.setPayedPercentage(100);
        }
        trainingRepository.save(training);
        return training;
    }


    private Double getFeeForTheTraining (Training training, Mentor mentor){
        for (MentorSkill mentorSkill : mentor.getMentorSkills()   ) {
            if (mentorSkill.getSkill().getId()== training.getSkillId()){
                return mentorSkill.getFee();
            }
        }
        return Double.valueOf(0);
    }

}
