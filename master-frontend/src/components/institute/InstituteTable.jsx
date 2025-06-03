import React from "react";

const InstituteTable = ({ institutes, onEdit, onDelete }) => {
return (
<table className="table table-bordered table-hover mt-2">
<thead className="table-info">
    <tr>
        <th>ID</th>
        <th>Institute Name</th>
        <th>Created By</th>
        <th>Created At</th>
        <th>Updated By</th>
        <th>Updated At</th>
        <th>Actions</th>
        </tr>
      </thead>
      <tbody>
       {institutes.length ? (
          institutes.map((institute) => (
            <tr key={institute.id}>
              <td>{institute.id}</td>
              <td>{institute.name}</td>
              <td>{institute.createdBy}</td>
              <td>{institute.createdAt ? new Date(institute.createdAt).toLocaleString() : ''}</td>
              <td>{institute.updatedBy}</td>
              <td>{institute.updatedAt ? new Date(institute.updatedAt).toLocaleString() : ''}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2" onClick={() => onEdit(leave)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => onDelete(leave.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="7" className="text-center">
              No leave types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default InstituteTable;
