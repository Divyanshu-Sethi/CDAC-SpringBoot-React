import React, { useState, useEffect} from "react";

const QualificationForm = ({ onSave, editData }) => {
  const [name, setName] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
      if (editData) {
        setName(editData.name);
      } else {
        setName('');
      }
      setError('');
    }, [editData]);
  
    const handleSubmit = (e) => {
    e.preventDefault();
    if (!name.trim()) {
      setError('Qualification name is required');
      return;
    }
    onSave({ name });
    setName('');
  };

  return (
    <form onSubmit={handleSubmit} className="row g-3">
      <div className="col-md-8">
        <input
          type="text"
          className="form-control"
          placeholder="Enter Qualification name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        {error && <div className="text-danger">{error}</div>}
      </div>
      <div className="col-md-4">
        <button type="submit" className="btn btn-info w-100">
          {editData ? 'Update' : 'Save'}
        </button>
      </div>
    </form>
  );
};

export default QualificationForm;