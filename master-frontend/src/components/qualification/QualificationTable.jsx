import React from "react";

const QualificationTable = ({ qualifications, onEdit, onDelete }) => {
return (
    <table className="table table-bordered table-hover mt-2">
      <thead className="table-info">
        <tr>
          <th>Qualification Name</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {qualifications.length ? (
          qualifications.map((qualification) => (
            <tr key={qualification.id}>
              <td>{qualification.name}</td>
              <td>
                <button 
                className="btn btn-warning btn-sm me-2" 
                onClick={() => onEdit(qualification)}>
                Edit
                </button>
                <button 
                className="btn btn-danger btn-sm" 
                onClick={() => onDelete(qualification.id)}>
                Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="2" className="text-center">
              No qualification types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default QualificationTable;
