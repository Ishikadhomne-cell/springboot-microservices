package com.embarkx.jobms;

import com.embarkx.jobms.job.DTO.JobDTO;
import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobController;
import com.embarkx.jobms.job.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    void testFindAllJobs() throws Exception {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJob(new Job(1L, "Java Dev", "Backend dev", "50000", "100000", "Delhi", 1L));
        List<JobDTO> jobs = Collections.singletonList(jobDTO);

        Mockito.when(jobService.findAll()).thenReturn(jobs);

        mockMvc.perform(get("/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(jobs.size()));
    }

    @Test
    void testCreateJob() throws Exception {
        Job job = new Job(1L, "Java Dev", "Backend dev", "50000", "100000", "Delhi", 1L);

        Mockito.when(jobService.createJob(any(Job.class))).thenReturn(job);

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(job)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Job added successfully"));
    }

    @Test
    void testGetJobById_Found() throws Exception {
        Long id = 1L;
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJob(new Job(id, "Java Dev", "Backend dev", "50000", "100000", "Delhi", 1L));

        Mockito.when(jobService.getJobById(id)).thenReturn(jobDTO);

        mockMvc.perform(get("/jobs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.job.id").value(id));
    }

    @Test
    void testGetJobById_NotFound() throws Exception {
        Mockito.when(jobService.getJobById(99L)).thenReturn(null);

        mockMvc.perform(get("/jobs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteJob_Success() throws Exception {
        Mockito.when(jobService.deleteJobById(1L)).thenReturn(true);

        mockMvc.perform(delete("/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Job deleted successfully"));
    }

    @Test
    void testDeleteJob_NotFound() throws Exception {
        Mockito.when(jobService.deleteJobById(99L)).thenReturn(false);

        mockMvc.perform(delete("/jobs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateJob_Success() throws Exception {
        Job updatedJob = new Job(null, "Updated Title", "Updated desc", "60000", "110000", "Mumbai", 2L);

        Mockito.when(jobService.updateJobById(eq(1L), any(Job.class))).thenReturn(true);

        mockMvc.perform(put("/jobs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedJob)))
                .andExpect(status().isOk())
                .andExpect(content().string("Job updated successfully"));
    }

    @Test
    void testUpdateJob_NotFound() throws Exception {
        Job updatedJob = new Job(null, "Title", "desc", "60000", "110000", "Mumbai", 2L);

        Mockito.when(jobService.updateJobById(eq(999L), any(Job.class))).thenReturn(false);

        mockMvc.perform(put("/jobs/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedJob)))
                .andExpect(status().isNotFound());
    }
}
