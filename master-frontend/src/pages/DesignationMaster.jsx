import React, { useEffect, useState } from "react";
import DesignationForm from "../components/designation/DesignationForm";
import DesignationTable from "../components/designation/DesignationTable";
import Toast from "../components/common/Toast";
import {
  getDesignation,
  createDesignation,
  updateDesignation,
  deleteDesignation,
} from '../api/designationApi';

const DesignationMaster = () => {
  const [designations, setDesignations] = useState([]);
  const [toast, setToast] = useState(null);
  const [editData, setEditData] = useState(null);

  const loadDesignation = () => {
    getDesignation()
      .then((res) => setDesignations(res.data))
      .catch(() => showToast('Failed to load designations', 'danger'));
  };

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  const handleSave = (data) => {
    const apiCall = editData
     ? updateDesignation(editData.id, data)
     : createDesignation(data);
    apiCall
      .then(() => {
        showToast(`Designation ${editData ? 'updated' : 'created'} successfully`);
        setEditData(null);
        loadDesignation();
      })
      .catch((err) => {
        if (err.response?.status === 409)  showToast(err.response.data, 'danger');
         else   showToast('Error occurred', 'danger');
      });
  };

  const handleEdit = (data) => {
    setEditData(data);
  };

  const handleDelete = (id) => {
    if (window.confirm('Are you sure?')) {
      deleteDesignation(id)
        .then(() => {
          showToast('Designation deleted');
          loadDesignation();
        })
        .catch(() => showToast('Delete Failed', 'danger'));
    }
  };

  useEffect(() => {
    loadDesignation();
  }, []);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow">
      <h2 className="text-info mb-4">Designation Type Master</h2>
      <DesignationForm onSave={handleSave} editData={editData} />
      <hr className="my-4" />
      <h4 className="text-secondary mb-3">All Designation Types</h4>
      <DesignationTable
        designations={designations}
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

export default DesignationMaster;