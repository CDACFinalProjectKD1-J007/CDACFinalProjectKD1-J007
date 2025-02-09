import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/ComplaintsManagement.css';

function ComplaintsManagement() {
  const [complaints, setComplaints] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/complaints_management')
      .then(response => setComplaints(response.data))
      .catch(error => console.error("Error fetching complaints:", error));
  }, []);

  const updateStatus = (id, newStatus) => {
    axios.put(`http://localhost:8080/api/complaints_management/${id}`, { status: newStatus })
      .then(response => {
        setComplaints((prevComplaints) =>
          prevComplaints.map((complaint) =>
            complaint.id === id ? { ...complaint, status: newStatus } : complaint 
          )
        );
        toast.success('Complaint status updated successfully!');
      })
      .catch(error => {
        console.error("Error updating status:", error);
        toast.error('Failed to update complaint status.');
      });
  };

  return (
    <div className="complaints-management-container">
      <h1>Complaints Management</h1>
      <p>Manage and review complaints here.</p>

      <div className="complaints-list">
        {complaints.length > 0 ? (
          complaints.map((complaint) => (
            <div key={complaint.id} className="complaint-item">
              <h3>{complaint.complaintType}</h3>
              <p>{complaint.description}</p>
              <span className={`priority-${complaint.priority?.toLowerCase() || 'low'}`}>
                {complaint.priority || 'Low'}
              </span>
              <select
                value={complaint.status || "FORWARDED"} 
                onChange={(e) => updateStatus(complaint.id, e.target.value)}
              >
                <option value="FORWARDED">Forwarded</option>
                <option value="COMPLETED">Completed</option>
              </select>
              {complaints.length === 0 && <div className="loading-spinner"></div>}
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
