import React from 'react';


const LeaveTable = ({ leaves, onEdit, onDelete }) => {
  return (
    <table className="table table-bordered table-hover mt-2">
      <thead className="table-info">
        <tr>
          <th>ID</th>
          <th>Leave Name</th>
          <th>Created By</th>
          <th>Created At</th>
          <th>Updated By</th>
          <th>Updated At</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {leaves.length ? (
          leaves.map((leave) => (
            <tr key={leave.id}>
              <td>{leave.id}</td>
              <td>{leave.name}</td>
              <td>{leave.createdBy}</td>
              <td>{leave.createdAt ? new Date(leave.createdAt).toLocaleString() : ''}</td>
              <td>{leave.updatedBy}</td>
              <td>{leave.updatedAt ? new Date(leave.updatedAt).toLocaleString() : ''}</td>
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

export default LeaveTable;
