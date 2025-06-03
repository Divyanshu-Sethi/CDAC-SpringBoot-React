import React, { useEffect, useState } from 'react';
import LeaveForm from '../components/leave/LeaveForm';
import LeaveTable from '../components/leave/LeaveTable';
import Toast from '../components/common/Toast';
import {
  getLeaves,
  getLeaveById,
  createLeave,
  updateLeave,
  deleteLeave,
} from '../api/leaveApi';

const LeaveMaster = () => {
  const [leaves, setLeaves] = useState([]);
  const [toast, setToast] = useState(null);
  const [editData, setEditData] = useState(null);

  const loadLeaves = () => {
    getLeaves()
      .then((res) => setLeaves(res.data))
      .catch(() => showToast('Failed to load leaves', 'danger'));
  };

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  const handleSave = (data) => {
    const apiCall = editData ? updateLeave(editData.id, data) : createLeave(data);
    apiCall
      .then(() => {
        showToast(`Leave ${editData ? 'updated' : 'created'} successfully`);
        setEditData(null);
        loadLeaves();
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
      deleteLeave(id)
        .then(() => {
          showToast('Leave deleted');
          loadLeaves();
        })
        .catch(() => showToast('Delete failed', 'danger'));
    }
  };

  useEffect(() => {
    loadLeaves();
  }, []);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow">
      <h2 className="text-info mb-4">Add / Edit Leave Type</h2>
      <LeaveForm onSave={handleSave} editData={editData} />
      <hr className="my-4" />
      <h4 className="text-secondary mb-3">All Leave Types</h4>
      <LeaveTable leaves={leaves} onEdit={handleEdit} onDelete={handleDelete} />
      <div className="toast-container position-fixed top-0 end-0 p-3">
        {toast && <Toast message={toast.message} type={toast.type} onClose={() => setToast(null)} />}
      </div>
    </div>
  );
};

export default LeaveMaster;
