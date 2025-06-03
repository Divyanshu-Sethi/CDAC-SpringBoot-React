import React from "react";


const UserTypeTable = ({ userTypes, onEdit, onDelete }) => {
return (
    <table className="table table-bordered table-hover mt-2">
    <thead className="table-info">
    <tr>
    <th>ID</th>
    <th>UserType Name</th>
    <th>Created By</th>
    <th>Created At</th>
    <th>Updated By</th>
    <th>Updated At</th>
    <th>Actions</th>
    </tr>
    </thead>
    <tbody>
 {userTypes.length ? (
          userTypes.map((userType) => (
            <tr key={userType.id}>
              <td>{userType.id}</td>
              <td>{userType.name}</td>
              <td>{userType.createdBy}</td>
              <td>{userType.createdAt ? new Date(userType.createdAt).toLocaleString() : ''}</td>
              <td>{userType.updatedBy}</td>
              <td>{userType.updatedAt ? new Date(userType.updatedAt).toLocaleString() : ''}</td>
              <td>
                <button className="btn btn-warning btn-sm me-2" onClick={() => onEdit(userType)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => onDelete(userType.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="7" className="text-center">
              No userType types found.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default UserTypeTable;