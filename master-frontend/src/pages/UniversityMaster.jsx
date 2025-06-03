import React, { useEffect, useState } from 'react';
import UniversityForm from '../components/university/UniversityForm';
import UniversityTable from '../components/university/UniversityTable';
import Toast from '../components/common/Toast';

import {
  getUniversity,
  getUniversityById,
  createUniversity,
  updateUniversity,
  deleteUniversity,
} from '../api/universityApi';

const UniversityMaster = () => {
  const [universities, setUniversities] = useState([]);
  const [editData, setEditData] = useState(null);
  const [toast, setToast] = useState(null);

  const loadUniversities = () => {
    getUniversity()
      .then((res) => setUniversities(res.data))
      .catch(() => showToast('Failed to load universities', 'danger'));
  };

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  const handleSave = (data) => {
    const apiCall = editData
      ? updateUniversity(editData.id, data)
      : createUniversity(data);

    apiCall
      .then(() => {
        showToast(`University ${editData ? 'updated' : 'created'} successfully`);
        setEditData(null);
        loadUniversities();
      })
      .catch((err) => {
        if (err.response?.status === 409) showToast(err.response.data, 'danger');
        else showToast('Error occurred', 'danger');
      });
  };

  const handleEdit = (id) => {
    getUniversityById(id)
      .then((res) => setEditData(res.data))
      .catch(() => showToast('Failed to load university data', 'danger'));
  };

  const handleDelete = (id) => {
    if (window.confirm('Are you sure?')) {
      deleteUniversity(id)
        .then(() => {
          showToast('University deleted');
          loadUniversities();
        })
        .catch(() => showToast('Delete failed', 'danger'));
    }
  };

  useEffect(() => {
    loadUniversities();
  }, []);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow">
      <h2 className="text-info mb-4">Add / Edit University Type</h2>
      <UniversityForm onSave={handleSave} editData={editData} />
      <hr className="my-4" />
      <h4 className="text-secondary mb-3">All University Types</h4>
      <UniversityTable
        universities={universities}
        onEdit={(uni) => handleEdit(uni.id)}
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

export default UniversityMaster;
