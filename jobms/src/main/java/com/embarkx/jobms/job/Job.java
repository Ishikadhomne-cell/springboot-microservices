package com.embarkx.jobms.job;

import com.embarkx.jobms.job.external.Company;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    // Using BigDecimal for better monetary value handling
    @Column(name = "min_salary")
    private String minSalary;
    @Column(name = "max_salary")
    private String maxSalary;

    private String location;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    // Required no-arg constructor for JPA
    public Job() {
    }

    // Full constructor with proper parameter order
    public Job(Long id, String title, String description, String minSalary,
               String maxSalary, String location, Long companyId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
        this.companyId = companyId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // toString() for better logging/debugging
    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", location='" + location + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}