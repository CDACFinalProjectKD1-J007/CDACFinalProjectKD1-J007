import React, { useState, useEffect } from 'react'; 
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/DeliveryStatus.css';

function ApplicationStatus() {
  const userId = localStorage.getItem("userId"); 
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!userId) {
      toast.error('User ID not found. Please log in again.');
      return;
    }

    const fetchApplications = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`http://localhost:8080/api/applications/user/${userId}`);
        setApplications(response.data);
        toast.success('Applications fetched successfully!');
      } catch (error) {
        console.error('Error fetching applications:', error);
        toast.error('Failed to fetch applications.');
      } finally {
        setLoading(false);
      }
    };

    fetchApplications();
  }, [userId]);

  return (
    <div className="application-container">
      <h1>Your Applications</h1>
      <label>User ID</label>
      <input type="text" value={userId} readOnly /> 

      {loading ? (
        <p>Loading applications...</p>
      ) : applications.length > 0 ? (
        <div className="application-list">
          {applications.map((app) => (
            <div key={app.applicationId} className="application-details">
              <h2>Application ID: {app.applicationId}</h2>
              <p><strong>Status:</strong> {app.status}</p>
              <p><strong>Created At:</strong> {new Date(app.createdAt).toLocaleString()}</p>
              <p><strong>Updated At:</strong> {new Date(app.updatedAt).toLocaleString()}</p>
            </div>
          ))}
        </div>
      ) : (
        <p>No applications found.</p>
      )}

      <ToastContainer />
    </div>
  );
}

export default ApplicationStatus;
