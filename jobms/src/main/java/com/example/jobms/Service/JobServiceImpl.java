package com.example.jobms.Service;

import com.example.jobms.Entity.Job;
import com.example.jobms.Repository.JobRepo;
import com.example.jobms.dto.JobWithCompanyDTO;
import com.example.jobms.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    JobRepo jobRepo;

    @Autowired
    public JobServiceImpl(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {

        List<Job> jobs = jobRepo.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job){


            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);
            RestTemplate restTemplate = new RestTemplate();
            Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
            jobWithCompanyDTO.setCompany(company);

            return jobWithCompanyDTO;

    }

    @Override
    public void createJob(Job job) {
        jobRepo.save(job);

    }

    @Override
    public Job getJobById(Long id) {
        return jobRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepo.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean updateJobById(Long id, Job updateJob) {
        Optional<Job> jobOptional = jobRepo.findById(id);
        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updateJob.getTitle());
            job.setDescription(updateJob.getDescription());
            job.setMinSalary(updateJob.getMinSalary());
            job.setMaxSalary(updateJob.getMaxSalary());
            job.setLocation(updateJob.getLocation());
            jobRepo.save(job);
            return true;
        }
        return false;
    }
}
