import React from "react";

const UniversityTable = ({ universities, onEdit, onDelete }) => {
return(
<table className="table table-bordered table-hover mt-2">
<thead className="table-info">
    <tr>
        <th>ID</th>
        <th>University Name</th>
        <th>Created By</th>
        <th>Created At</th>
        <th>Updated By</th>
        <th>Updated At</th>
        <th>Actions</th>
    </tr>
</thead>
<tbody>
{universities.length ? (
          universities.map((university) => (
            <tr key={university.id}>
              <td>{university.id}</td>
              <td>{university.name}</td>
              <td>{university.createdBy}</td>
              <td>{university.createdAt ? new Date(university.createdAt).toLocaleString() : ''}</td>
              <td>{university.updatedBy}</td>
              <td>{university.updatedAt ? new Date(university.updatedAt).toLocaleString() : ''}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2" onClick={() => onEdit(university)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => onDelete(university.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="7" className="text-center">
              No university types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default UniversityTable;
