import React from "react";

const UserTypeTable = ({ userTypes, onEdit, onDelete }) => {
return (
    <table className="table table-bordered table-hover mt-2">
    <thead className="table-info">
    <tr>
    <th>UserType Name</th>
    <th>Actions</th>
    </tr>
    </thead>
    <tbody>
 {userTypes.length ? (
          userTypes.map((userType) => (
            <tr key={userType.id}>
              <td>{userType.name}</td>
              <td>
                <button 
                className="btn btn-warning btn-sm me-2" 
                onClick={() => onEdit(userType)}>
                Edit
                </button>
                <button 
                className="btn btn-danger btn-sm" 
                onClick={() => onDelete(userType.id)}>
                Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="2" className="text-center">
              No userType types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default UserTypeTable;