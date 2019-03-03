package com.training.fullstack.admin.infrastructure;

import com.training.fullstack.admin.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository  extends JpaRepository<Skill, Long>{

    Optional<Skill> findByTitle(String aTitle);
}
