package com.ontrack.TASKSUBMISSIONSERVICE.service;

import com.ontrack.TASKSUBMISSIONSERVICE.modal.Submission;
import com.ontrack.TASKSUBMISSIONSERVICE.modal.TaskDTO;
import com.ontrack.TASKSUBMISSIONSERVICE.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService{

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDTO task = taskService.getTaskById(jwt,taskId);
        if (task != null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);
        }
        throw new Exception("Could not create submission.Task not found with id : " + taskId);
    }

    @Override
    public Submission getTaskBySubmissionId(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(() -> new Exception("Could not find submission with id : " + submissionId));
    }

    @Override
    public List<Submission> getAllTaskSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskBySubmissionId(id);
        submission.setStatus(status);
        if (status.equals("ACCEPT")){
            taskService.completeTask(submission.getTaskId());
        }

        return submissionRepository.save(submission);
    }
}
