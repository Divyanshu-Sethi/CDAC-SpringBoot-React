import axios from './axiosConfig';

export const getUserTypes = () => axios.get('/user-type');
export const getUserTypeById = (id) => axios.get(`/user-type/${id}`);
export const createUserType = (data) => axios.post('/user-type', data);
export const updateUserType = (id, data) => axios.put(`/user-type/${id}`, data);
export const deleteUserType = (id) => axios.delete(`/user-type/${id}`);