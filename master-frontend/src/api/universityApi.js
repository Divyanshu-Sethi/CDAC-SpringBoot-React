import axios from './axiosConfig';

export const getUniversity = () => axios.get('/university');
export const getUniversityById = (id) => axios.get(`/university/${id}`);
export const createUniversity = (data) => axios.post('/university', data);
export const updateUniversity = (id, data) => axios.put(`/university/${id}`, data);
export const deleteUniversity = (id) => axios.delete(`/university/${id}`);
