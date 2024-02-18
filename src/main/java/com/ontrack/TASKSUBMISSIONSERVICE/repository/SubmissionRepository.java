package com.ontrack.TASKSUBMISSIONSERVICE.repository;

import com.ontrack.TASKSUBMISSIONSERVICE.modal.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> findByTaskId(Long taskId);
}
