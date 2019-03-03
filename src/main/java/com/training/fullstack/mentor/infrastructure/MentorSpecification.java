package com.training.fullstack.mentor.infrastructure;

import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.admin.model.Skill;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.JoinColumn;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class MentorSpecification  {

    public static Specification<Mentor> isAvailableOn(LocalTime desiredTime, LocalDate desiredDate) {
        return (root, query, cb) ->{
            SetJoin<Mentor, Calendar> mentorCalendar = root.joinSet("calendars", JoinType.INNER);
            Predicate startsBeforeTime = cb.lessThanOrEqualTo(mentorCalendar.get("startTime"),  desiredTime);
            Predicate endsAfterTime = cb.greaterThanOrEqualTo(mentorCalendar.get("endTime"),  desiredTime);
            Predicate startsBeforeToday = cb.lessThanOrEqualTo(mentorCalendar.get("startDate"),  desiredDate);
            Predicate endsAfterToday = cb.greaterThanOrEqualTo(mentorCalendar.get("endDate"),  desiredDate);
            return cb.and(startsBeforeTime,endsAfterTime, startsBeforeToday, endsAfterToday);
        };
    }



    public static Specification<Mentor> hasSkillNamed(String skillName) {
        return (root, query, cb) -> {
            SetJoin<Mentor, MentorSkill> mentorSkills = root.joinSet("mentorSkills", JoinType.INNER);
            Join<MentorSkill, Skill> skills = mentorSkills.join("skill", JoinType.INNER);
            return cb.like(
                    cb.lower(skills.get("title")),
                    skillName
            );
        };
    }

    private static Specification<Mentor> alwaystrue ( ){
        return (root, query, cb) -> {
            return cb.and();
        };
    }

    public static Specification<Mentor> hasSkillNamedAndIsAvailableOn(String skillName, LocalTime desiredTime, LocalDate desiredDate) {
        return hasSkillNamed(skillName).and(isAvailableOn(desiredTime, desiredDate));
    }

}