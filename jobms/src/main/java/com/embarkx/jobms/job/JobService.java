package com.embarkx.jobms.job;

import com.embarkx.jobms.job.DTO.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    Job createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJobById(Long id, Job updateJob);

}
