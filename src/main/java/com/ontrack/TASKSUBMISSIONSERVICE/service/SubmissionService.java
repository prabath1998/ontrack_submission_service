package com.ontrack.TASKSUBMISSIONSERVICE.service;

import com.ontrack.TASKSUBMISSIONSERVICE.modal.Submission;

import java.util.List;

public interface SubmissionService {
    Submission submitTask(Long taskId,String githubLink,Long userId) throws Exception;
    Submission getTaskBySubmissionId(Long submissionId) throws Exception;
    List<Submission> getAllTaskSubmissions();
    List<Submission> getTaskSubmissionsByTaskId(Long taskId);
    Submission acceptDeclineSubmission(Long id,String status) throws Exception;
}