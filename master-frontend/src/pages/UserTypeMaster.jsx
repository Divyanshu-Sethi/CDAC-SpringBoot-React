import React, { useEffect, useState } from "react";
import UserTypeForm from "../components/usertype/UserTypeForm";
import UserTypeTable from "../components/usertype/UserTypeTable";
import Toast from "../components/common/Toast";

import {
  getUserTypes,
  getUserTypeById,
  createUserType,
  updateUserType,
  deleteUserType,
} from '../api/userTypeApi';

const UserTypeMaster = () => {
  const [userTypes, setUserTypes] = useState([]);
  const [editData, setEditData] = useState(null);
  const [toast, setToast] = useState(null);

  const loadUserTypes = () => {
    getUserTypes()
      .then((res) => setUserTypes(res.data))
      .catch(() => showToast('Failed to load User Types', 'danger'));
  };

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  const handleSave = (data) => {
    const apiCall = editData
      ? updateUserType(editData.id, data)
      : createUserType(data);

    apiCall
      .then(() => {
        showToast(`User Type ${editData ? 'updated' : 'created'} successfully`);
        setEditData(null);
        loadUserTypes();
      })
      .catch((err) => {
        if (err.response?.status === 409) showToast(err.response.data, 'danger');
        else showToast('Error occurred', 'danger');
      });
  };

  const handleEdit = (data) => {
    setEditData(data);
  };

  const handleDelete = (id) => {
    if (window.confirm('Are you sure?')) {
      deleteUserType(id)
        .then(() => {
          showToast('User Type deleted');
          loadUserTypes();
        })
        .catch(() => showToast('Delete failed', 'danger'));
    }
  };

  useEffect(() => {
    loadUserTypes();
  }, []);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow">
      <h2 className="text-info mb-4">Add / Edit User Type</h2>
      <UserTypeForm onSave={handleSave} editData={editData} />
      <hr className="my-4" />
      <h4 className="text-secondary mb-3">All User Types</h4>
      <UserTypeTable
        userTypes={userTypes}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
      <div className="toast-container position-fixed top-0 end-0 p-3">
        {toast && (
          <Toast
            message={toast.message}
            type={toast.type}
            onClose={() => setToast(null)}
          />
        )}
      </div>
    </div>
  );
};

export default UserTypeMaster;
