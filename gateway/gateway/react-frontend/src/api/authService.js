
import axios from "axios";
const BASE_URL = "http://localhost:8085/auth";

export const loginUser = (credentials) => axios.post(`${BASE_URL}/login`, credentials);
