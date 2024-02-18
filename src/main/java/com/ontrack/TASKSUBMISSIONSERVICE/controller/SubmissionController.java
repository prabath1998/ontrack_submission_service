package com.ontrack.TASKSUBMISSIONSERVICE.controller;

import com.ontrack.TASKSUBMISSIONSERVICE.modal.Submission;
import com.ontrack.TASKSUBMISSIONSERVICE.modal.UserDTO;
import com.ontrack.TASKSUBMISSIONSERVICE.service.SubmissionService;
import com.ontrack.TASKSUBMISSIONSERVICE.service.TaskService;
import com.ontrack.TASKSUBMISSIONSERVICE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Submission> submitTask(@RequestParam Long taskId, @RequestParam String githubLink, @RequestHeader("Authorization") String jwt)throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(taskId, githubLink,user.getId(),jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id, @RequestHeader("Authorization") String jwt)throws Exception{
        Submission submission = submissionService.getTaskBySubmissionId(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmissions(@RequestHeader("Authorization") String jwt)throws Exception{
        List<Submission> submission = submissionService.getAllTaskSubmissions();
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<Submission>> getAllSubmissionsByTaskId(@PathVariable Long taskId,@RequestHeader("Authorization") String jwt)throws Exception{
        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(@PathVariable Long id,@RequestParam("status") String status, @RequestHeader("Authorization") String jwt)throws Exception{
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id, status);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }
}
