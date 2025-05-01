
import axios from "axios";
const BASE_URL = "http://localhost:8085/jobs";

export const getAllJobs = () => axios.get(`${BASE_URL}/all`);
