import React from "react";

const DesignationTable = ({ designations, onEdit, onDelete }) => {
  return (
    <table className="table table-bordered table-hover mt-2">
      <thead className="table-info">
        <tr>
          <th>Designation Name</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {designations.length ? (
          designations.map((designation) => (
            <tr key={designation.id}>
              <td>{designation.name}</td>
              <td>
                <button
                  className="btn btn-warning btn-sm me-2"
                  onClick={() => onEdit(designation)}>
                  Edit
                </button>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => onDelete(designation.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="2" className="text-center">
              No designation types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default DesignationTable;