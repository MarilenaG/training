package com.training.fullstack.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentService {
    //TODO : payments should be saved in the database ( trainingId, from, to, amount, date) and presented to the admin as a list of payments
    public boolean payForTraining( String username,  Double fee , Long trainingId){
        //the users pays the entire amount at the beginning of training
        System.out.println("User  "+username+" pays the training fee :" + fee + " corresponding to training ID " + trainingId);
        return true;
    }

    public boolean payMentorAfterFirstQuarterTraining (String mentorIBAN, Double amount , Long trainingId){
        // pay mentor after 25% training is done
        System.out.println("Pay mentor "+mentorIBAN+" the 1st quarter of it's fee :" + amount+ " corresponding to training ID " + trainingId);
        return true;
    }

    public boolean payMentorAfterSecondQuarterTraining (String mentorIBAN, Double amount , Long trainingId){
        // pay mentor after 50% training is done
        System.out.println("Pay mentor "+mentorIBAN+" the 2nd quarter of it's fee :" + amount+ " corresponding to training ID " + trainingId);
        return true;
    }
    public boolean payMentorAfterThirdQuarterTraining (String mentorIBAN, Double amount , Long trainingId){
        // pay mentor after 50% training is done
        System.out.println("Pay mentor "+mentorIBAN+" the 3rd quarter of it's fee :" + amount+ " corresponding to training ID " + trainingId);
        return true;
    }
    public boolean payMentorAfterEndTraining (String mentorIBAN, Double amount , Long trainingId){
        // pay mentor after 100% training is done
        System.out.println("Pay mentor "+mentorIBAN+" the 4th quarter of it's fee :" + amount+ " corresponding to training ID " + trainingId);
        return true;
    }
}
