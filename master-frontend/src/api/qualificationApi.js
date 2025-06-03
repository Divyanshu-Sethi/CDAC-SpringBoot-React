import axios from './axiosConfig';

export const getQualifications = () => axios.get('/qualification');
export const getQualificationById = (id) => axios.get(`/qualification/${id}`);
export const createQualification = (data) => axios.post('/qualification', data);
export const updateQualification = (id, data) => axios.put(`/qualification/${id}`, data);
export const deleteQualification = (id) => axios.delete(`/qualification/${id}`);
