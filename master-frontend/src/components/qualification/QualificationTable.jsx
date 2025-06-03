import React from "react";

const QualificationTable = ({ qualifications, onEdit, onDelete }) => {
return (
    <table className="table table-bordered table-hover mt-2">
      <thead className="table-info">
        <tr>
          <th>ID</th>
          <th>Qualification Name</th>
          <th>Created By</th>
          <th>Created At</th>
          <th>Updated By</th>
          <th>Updated At</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {qualifications.length ? (
          qualifications.map((qualification) => (
            <tr key={qualification.id}>
              <td>{qualification.id}</td>
              <td>{qualification.name}</td>
              <td>{qualification.createdBy}</td>
              <td>{qualification.createdAt ? new Date(qualification.createdAt).toLocaleString() : ''}</td>
              <td>{qualification.updatedBy}</td>
              <td>{qualification.updatedAt ? new Date(qualification.updatedAt).toLocaleString() : ''}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2" onClick={() => onEdit(qualification)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => onDelete(qualification.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="7" className="text-center">
              No qualification types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default QualificationTable;
