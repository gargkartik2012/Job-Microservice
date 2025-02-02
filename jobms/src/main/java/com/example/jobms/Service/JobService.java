package com.example.jobms.Service;

import com.example.jobms.Entity.Job;
import com.example.jobms.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> findAll();

    void createJob(Job job);

    Job getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJobById(Long id, Job job);



}
