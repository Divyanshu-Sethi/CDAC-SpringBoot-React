import axios from './axiosConfig';  // your Axios instance

export const getLeaves = () => axios.get('/leave');
export const getLeaveById = (id) => axios.get(`/leave/${id}`);
export const createLeave = (data) => axios.post('/leave', data);
export const updateLeave = (id, data) => axios.put(`/leave/${id}`, data);
export const deleteLeave = (id) => axios.delete(`/leave/${id}`);
