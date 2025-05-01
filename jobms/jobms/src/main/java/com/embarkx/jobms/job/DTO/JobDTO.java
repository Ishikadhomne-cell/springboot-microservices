package com.embarkx.jobms.job.DTO;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;

import java.util.List;

public class JobDTO {
    private Job job;
    private Company company;
    private List<Review> reviews;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
