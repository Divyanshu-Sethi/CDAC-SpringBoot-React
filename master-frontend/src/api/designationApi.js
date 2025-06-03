import axios from './axiosConfig';

export const getDesignation = () => axios.get('/designation');
export const getDesignationById = (id) => axios.get(`/designation/${id}`);
export const createDesignation = (data) => axios.post('/designation', data);
export const updateDesignation = (id, data) => axios.put(`/designation/${id}`, data);
export const deleteDesignation = (id) => axios.delete(`/designation/${id}`);