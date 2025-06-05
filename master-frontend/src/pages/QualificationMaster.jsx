import React, { useEffect, useState } from 'react';
import QualificationForm from '../components/qualification/QualificationForm';
import QualificationTable from '../components/qualification/QualificationTable';
import Toast from '../components/common/Toast';

import {
  getQualifications,
  createQualification,
  updateQualification,
  deleteQualification,
} from '../api/qualificationApi';

const QualificationMaster = () => {
  const [qualifications, setQualifications] = useState([]);
  const [toast, setToast] = useState(null);
  const [editData, setEditData] = useState(null);

  const loadQualifications = () => {
    getQualifications()
      .then((res) => setQualifications(res.data))
      .catch(() => showToast('Failed to load qualifications', 'danger'));
  };

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  const handleSave = (data) => {
    const apiCall = editData ? updateQualification(editData.id, data) : createQualification(data);
    apiCall
      .then(() => {
        showToast(`Qualification ${editData ? 'updated' : 'created'} successfully`);
        setEditData(null);
        loadQualifications();
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
      deleteQualification(id)
        .then(() => {
          showToast('Qualification deleted');
          loadQualifications();
        })
        .catch(() => showToast('Delete failed', 'danger'));
    }
  };

  useEffect(() => {
    loadQualifications();
  }, []);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow">
      <h2 className="text-info mb-4">Qualification Type Master</h2>
      <QualificationForm onSave={handleSave} editData={editData} />
      <hr className="my-4" />
      <h4 className="text-secondary mb-3">All Qualification Types</h4>
      <QualificationTable
        qualifications={qualifications}
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

export default QualificationMaster;
