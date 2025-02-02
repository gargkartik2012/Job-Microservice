package com.example.jobms.Controller;


import com.example.jobms.Entity.Job;
import com.example.jobms.Service.JobService;
import com.example.jobms.dto.JobWithCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Job")
public class JobController {
     private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("jobs")
    public ResponseEntity<List<JobWithCompanyDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());

    }

    @PostMapping("/createJob")
    public ResponseEntity<String> createJob(Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if(job !=null){
            return new ResponseEntity<>(job,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        if(jobService.deleteJobById(id)){
            return new ResponseEntity<>("Job Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Job Not Found",HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, Job job){
        if(jobService.updateJobById(id,job)){
            return new ResponseEntity<>("Job Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Job Not Found",HttpStatus.NOT_FOUND);
    }




}

