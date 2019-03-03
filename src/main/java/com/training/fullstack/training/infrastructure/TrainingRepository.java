package com.training.fullstack.training.infrastructure;

import com.training.fullstack.mentor.model.Mentor;
import com.training.fullstack.training.model.Training;
import com.training.fullstack.training.model.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository  extends JpaRepository<Training, Long> {

    List<Training> findAllByStatus(TrainingStatus status);
    List<Training> findAllByMentorId(Long mentorId);

}
