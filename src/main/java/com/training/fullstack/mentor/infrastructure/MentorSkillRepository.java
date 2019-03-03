package com.training.fullstack.mentor.infrastructure;

import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.mentor.model.MentorSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorSkillRepository extends JpaRepository<MentorSkill, Long>  {
    @Query("SELECT ms FROM MentorSkill ms WHERE ms.skill.id = :mentorId and ms.skill.id = :skillId")
    Optional<MentorSkill> getByMentorIdAndSkillId(@Param("mentorId") Long mentorId, @Param("skillId") Long skillId);
}
