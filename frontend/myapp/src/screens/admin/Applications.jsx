import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/ApplicationStatus.css';

function Applications() {
  const [applications, setApplications] = useState([]);
  const [filteredApplications, setFilteredApplications] = useState([]);
  const [loading, setLoading] = useState(false);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [selectedStatus, setSelectedStatus] = useState('');
  const [updatedStatus, setUpdatedStatus] = useState('');

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/api/applications');
      setApplications(response.data);
      setFilteredApplications(response.data);
      toast.success('Applications fetched successfully!');
    } catch (error) {
      console.error('Error fetching applications:', error);
      toast.error('Failed to fetch applications.');
    } finally {
      setLoading(false);
    }
  };

  const handleFilterByDate = () => {
    if (!startDate || !endDate) {
      toast.error('Please select both start and end dates.');
      return;
    }

    const filtered = applications.filter((app) => {
      const appDate = new Date(app.createdAt);
      return appDate >= new Date(startDate) && appDate <= new Date(endDate);
    });

    setFilteredApplications(filtered);
  };

  const handleFilterByStatus = async () => {
    if (!selectedStatus) {
      toast.error('Please select a status.');
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/applications/status/${selectedStatus}`);
      setFilteredApplications(response.data);
      toast.success(`Applications with status "${selectedStatus}" fetched!`);
    } catch (error) {
      console.error('Error fetching applications by status:', error);
      toast.error('Failed to fetch applications.');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateStatus = async (id) => {
    if (!updatedStatus) {
      toast.error('Please select a new status.');
      return;
    }

    setLoading(true);
    try {
      await axios.put(`http://localhost:8080/api/applications/${id}/status`, {
        status: updatedStatus,
      });

      toast.success(`Status updated to "${updatedStatus}"`);
      fetchApplications();
    } catch (error) {
      console.error('Error updating application status:', error);
      toast.error('Failed to update status.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="application-container">
      <h1>All Applications</h1>

   
      <div className="filters">
        <div className="date-filter">
          <label>Start Date:</label>
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />

          <label>End Date:</label>
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />

          <button onClick={handleFilterByDate}>Filter by Date</button>
        </div>

       
        <div className="status-filter">
          <label>Filter by Status:</label>
          <select value={selectedStatus} onChange={(e) => setSelectedStatus(e.target.value)}>
            <option value="">Select Status</option>
            <option value="Pending">Pending</option>
            <option value="Approved">Approved</option>
            <option value="Rejected">Rejected</option>
          </select>
          <button onClick={handleFilterByStatus}>Filter</button>
        </div>
      </div>

      {loading ? (
        <p>Loading applications...</p>
      ) : (
        <table className="applications-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>User ID</th>
              <th>Status</th>
              <th>Created At</th>
              <th>Updated At</th>
              <th>Update Status</th>
            </tr>
          </thead>
          <tbody>
            {filteredApplications.length > 0 ? (
              filteredApplications.map((app) => (
                <tr key={app.applicationId}>
                  <td>{app.applicationId}</td>
                  <td>{app.userId}</td>
                  <td>{app.status}</td>
                  <td>{new Date(app.createdAt).toLocaleString()}</td>
                  <td>{new Date(app.updatedAt).toLocaleString()}</td>
                  <td>
                    <select value={updatedStatus} onChange={(e) => setUpdatedStatus(e.target.value)}>
                      <option value="">Select Status</option>
                      <option value="Pending">Pending</option>
                      <option value="Approved">Approved</option>
                      <option value="Rejected">Rejected</option>
                    </select>
                    <button onClick={() => handleUpdateStatus(app.applicationId)}>Update</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6">No applications found.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
      <ToastContainer />
    </div>
  );
}

export default Applications;
