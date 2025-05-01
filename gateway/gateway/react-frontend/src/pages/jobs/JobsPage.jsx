
import React, { useEffect, useState } from "react";
import { getAllJobs } from "../../api/jobService";

function JobsPage() {
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    getAllJobs().then(res => {
      setJobs(res.data);
    }).catch(err => {
      console.error("Failed to fetch jobs", err);
    });
  }, []);

  return (
    <div className="container mt-4">
      <h2>Jobs</h2>
      <ul className="list-group">
        {jobs.map(job => (
          <li key={job.id} className="list-group-item">
            <strong>{job.title}</strong> - {job.location}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default JobsPage;
