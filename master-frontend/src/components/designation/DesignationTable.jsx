import React, { forwardRef } from "react";

const DesignationTable = forwardRef(({ data, onEdit, onDelete }, ref) => {
  return (
    <div ref={ref}>
      <table className="table table-bordered table-hover mt-2">
        <thead>
          <tr>
            <th>ID</th>
            <th>Designation</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {data.map((designation) => (
            <tr key={designation.id}>
              <td>{designation.id}</td>
              <td>{designation.name}</td>
              <td>
                <button
                  className="btn btn-warning btn-sm me-2"
                  onClick={() => onEdit(designation)}
                >
                  Edit
                </button>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => onDelete(designation.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
});

export default DesignationTable;