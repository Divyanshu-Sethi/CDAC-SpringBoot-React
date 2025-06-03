
import axios from './axiosConfig';  // your Axios instance

export const getInstitutes = () => axios.get('/institute');
export const getInstituteById = (id) => axios.get(`/institute/${id}`);
export const createInstitute = (data) => axios.post('/institute', data);
export const updateInstitute = (id, data) => axios.put(`/institute/${id}`, data);
export const deleteInstitute = (id) => axios.delete(`/institute/${id}`);