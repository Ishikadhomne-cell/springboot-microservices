package com.embarkx.jobms.job.impl;
import com.embarkx.jobms.exception.CompanyServiceException;
import com.embarkx.jobms.job.DTO.JobDTO;
import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    // private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository; //Using JPA

    @Autowired
    public RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public JobDTO convertToDto(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJob(job);

        // Check if companyId is not null before making the call
        if (job.getCompanyId() != null) {
            try {
                Long id = job.getCompanyId();
                String url = ("http://companyms:8081/companies/" + id).toString();
                System.out.println(url);
                Company company = restTemplate.getForObject(
                        url,
                        Company.class);
                System.out.println("Company Information" + company);

                jobDTO.setCompany(company);

                ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                        "http://reviewms:8083/reviews?companyId=" + job.getCompanyId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Review>>() {
                        });
                List<Review> reviews = reviewResponse.getBody();

                jobDTO.setReviews(reviews);
            } catch (Exception e) {

                throw new CompanyServiceException("Failed to fetch company/review data from other services", e);
            }
        } else {
            jobDTO.setCompany(null);
        }

        return jobDTO;
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }


    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    @Transactional
    public boolean updateJobById(Long id, Job updateJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updateJob.getTitle());
            job.setDescription(updateJob.getDescription());
            job.setMinSalary(updateJob.getMinSalary());
            job.setMaxSalary(updateJob.getMaxSalary());
            job.setLocation(updateJob.getLocation());

            jobRepository.save(job); // Ensure changes are saved
            return true;
        }
        return false;
    }

}
