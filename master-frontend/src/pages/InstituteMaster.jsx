import React, { useEffect, useState } from "react";
import InstituteForm from "../components/institute/InstituteForm";
import InstituteTable from "../components/institute/InstituteTable";
import Toast from "../components/common/Toast";
import {
  getInstitutes,
  getInstituteById,
  createInstitute,
  updateInstitute,
  deleteInstitute,
} from '../api/instituteApi';

const InstituteMaster = () => {
  const [institutes, setInstitutes] = useState([]);
  const [toast, setToast] = useState(null);
  const [editData, setEditData] = useState(null);

  const loadInstitute = () => {
    getInstitutes()
      .then((res) => setInstitutes(res.data))
      .catch(() => showToast('Failed to load institute', 'danger'));
  };

  const showToast = (message, type = 'success') => {
    setToast({ message, type });
    setTimeout(() => setToast(null), 4000);
  };

  const handleSave = (data) => {
    const apiCall = editData ? updateInstitute(editData.id, data) : createInstitute(data);
    apiCall
      .then(() => {
        showToast(`Institute ${editData ? 'updated' : 'created'} successfully`);
        setEditData(null);
        loadInstitute();
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
      deleteInstitute(id)
        .then(() => {
          showToast('Institute deleted');
          loadInstitute();
        })
        .catch(() => showToast('Delete Failed', 'danger'));
    }
  };

  useEffect(() => {
    loadInstitute();
  }, []);

  return (
    <div className="container mt-5 p-4 bg-white rounded shadow">
      <h2 className="text-info mb-4">Institute Type Master</h2>
      <InstituteForm onSave={handleSave} editData={editData} />
      <hr className="my-4" />
      <h4 className="text-secondary mb-3">All Institute Types</h4>
      <InstituteTable institutes={institutes} onEdit={handleEdit} onDelete={handleDelete} />
      <div className="toast-container position-fixed top-0 end-0 p-3">
        {toast && <Toast message={toast.message} type={toast.type} onClose={() => setToast(null)} />}
      </div>
    </div>
  );
};

export default InstituteMaster;
