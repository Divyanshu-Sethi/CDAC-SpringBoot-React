import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8081', // Spring Boot backend URL
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default instance;
