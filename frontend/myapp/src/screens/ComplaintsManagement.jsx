import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/ComplaintsManagement.css';

function ComplaintsManagement() {
  const [complaints, setComplaints] = useState([
    { id: 1, title: 'Voter ID Issue', description: 'Unable to update address', status: 'Open', priority: 'High' },
    { id: 2, title: 'Technical Problem', description: 'Website not accessible', status: 'In Progress', priority: 'Medium' },
    { id: 3, title: 'Voter ID Date Problem', description: 'Date of birth incorrect', status: 'In Progress', priority: 'Low' }
  ]);

  const updateStatus = (id, newStatus) => {
    setComplaints((prevComplaints) =>
      prevComplaints.map((complaint) =>
        complaint.id === id ? { ...complaint, status: newStatus } : complaint
      )
    );
    toast.success('Complaint status updated successfully!');
  };

  return (
    <div className="complaints-management-container">
      <h1>Complaints Management</h1>
      <p>Manage and review complaints here.</p>

      <div className="complaints-list">
        {complaints.length > 0 ? (
          complaints.map((complaint) => (
            <div key={complaint.id} className="complaint-item">
              <h3>{complaint.title}</h3>
              <p>{complaint.description}</p>
              <span className={`priority-${complaint.priority.toLowerCase()}`}>{complaint.priority}</span>
              <select
                value={complaint.status}
                onChange={(e) => updateStatus(complaint.id, e.target.value)}
              >
                <option value="Open">Open</option>
                <option value="In Progress">In Progress</option>
                <option value="Resolved">Resolved</option>
              </select>
            </div>
          ))
        ) : (
          <p>No complaints available.</p>
        )}
      </div>

      <ToastContainer />
    </div>
  );
}

export default ComplaintsManagement;
