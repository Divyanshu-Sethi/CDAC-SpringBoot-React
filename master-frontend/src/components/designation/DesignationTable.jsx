import React from "react";


const DesignationTable = ({ designations, onEdit, onDelete }) => {
return (
<table className="table table-bordered table-hover mt-2">
<thead className="table-info">
<tr>
<th>ID</th>
<th>Designation Name</th>
<th>Created By</th>
<th>Created At</th>
<th>Updated By</th>
<th>Updated At</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
 {designations.length ? (
          designations.map((designation) => (
            <tr key={designation.id}>
              <td>{designation.id}</td>
              <td>{designation.name}</td>
              <td>{designation.createdBy}</td>
              <td>{designation.createdAt ? new Date(designation.createdAt).toLocaleString() : ''}</td>
              <td>{designation.updatedBy}</td>
              <td>{designation.updatedAt ? new Date(designation.updatedAt).toLocaleString() : ''}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2" onClick={() => onEdit(designation)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => onDelete(designation.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="7" className="text-center">
              No designation types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default DesignationTable;