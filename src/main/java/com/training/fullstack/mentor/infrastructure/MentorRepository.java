package com.training.fullstack.mentor.infrastructure;

import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>, JpaSpecificationExecutor<Mentor> {

    Optional<Mentor> findByUserName(String userName);

    @Query(value = "SELECT m FROM Mentor m  JOIN FETCH m.mentorSkills s where m.id = :id  ")
    Optional<Mentor> loadSkillsByMentor(@Param("id") Long id);


}
