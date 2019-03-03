package com.training.fullstack.mentor.infrastructure;

import com.training.fullstack.mentor.model.Calendar;
import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import com.training.fullstack.admin.model.Skill;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class MentorSpecification  {

    public static Specification<Mentor> isAvailableOn(LocalTime desiredTime) {
        return (root, query, cb) ->{
            ListJoin<Mentor, Calendar> mentorCalendar = root.joinList("calendars", JoinType.INNER);
            Predicate startsBeforeTime = cb.lessThanOrEqualTo(mentorCalendar.get("startTime"),  desiredTime);
            Predicate endsAfterTime = cb.greaterThanOrEqualTo(mentorCalendar.get("endTime"),  desiredTime);
            Predicate startsBeforeToday = cb.lessThanOrEqualTo(mentorCalendar.get("startDate"),  LocalDate.now());
            Predicate endsAfterToday = cb.greaterThanOrEqualTo(mentorCalendar.get("endDate"),  LocalDate.now());
            return cb.and(startsBeforeTime,endsAfterTime, startsBeforeToday, endsAfterToday);
        };
    }



    public static Specification<Mentor> hasSkillNamed(String skillName) {
        return (root, query, cb) -> {
            ListJoin<Mentor, MentorSkill> mentorSkills = root.joinList("mentorSkills", JoinType.INNER);
            ListJoin<MentorSkill, Skill> skills = mentorSkills.joinList("skills", JoinType.INNER);
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

    public static Specification<Mentor> hasSkillNamedAndIsAvailableOn(String skillName, LocalTime desiredTime) {
        return hasSkillNamed(skillName).and(isAvailableOn(desiredTime));
    }

}