import React from "react";

const UniversityTable = ({ universities, onEdit, onDelete }) => {
return(
<table className="table table-bordered table-hover mt-2">
<thead className="table-info">
    <tr>
        <th>University Name</th>
        <th>Actions</th>
    </tr>
</thead>
<tbody>
{universities.length ? (
          universities.map((university) => (
            <tr key={university.id}>
              <td>{university.name}</td>
              <td>
                <button 
                className="btn btn-warning btn-sm me-2" 
                onClick={() => onEdit(university)}>
                Edit
                </button>
                <button 
                className="btn btn-danger btn-sm" 
                onClick={() => onDelete(university.id)}>
                Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="2" className="text-center">
              No university types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default UniversityTable;
