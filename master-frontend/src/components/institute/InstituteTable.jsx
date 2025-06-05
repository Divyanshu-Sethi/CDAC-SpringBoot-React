import React from "react";

const InstituteTable = ({ institutes, onEdit, onDelete }) => {
return (
<table className="table table-bordered table-hover mt-2">
<thead className="table-info">
    <tr>
        <th>Institute Name</th>
        <th>Actions</th>
    </tr>
      </thead>
      <tbody>
       {institutes.length ? (
          institutes.map((institute) => (
            <tr key={institute.id}>
            <td>{institute.name}</td>
            <td>
                <button 
                className="btn btn-warning btn-sm me-2" 
                onClick={() => onEdit(institute)}>
                Edit
                </button>
                <button className="btn btn-danger btn-sm" 
                onClick={() => onDelete(institute.id)}>
                Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="2" className="text-center">
              No institutes found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default InstituteTable;
