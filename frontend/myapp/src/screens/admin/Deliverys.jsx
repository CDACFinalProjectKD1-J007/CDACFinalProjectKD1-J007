import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/ApplicationStatus.css';

function Deliverys() {
  const [deliveries, setDeliveries] = useState([]);
  const [filteredDeliveries, setFilteredDeliveries] = useState([]);
  const [loading, setLoading] = useState(false);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [selectedStatus, setSelectedStatus] = useState('');
  const [updatedStatus, setUpdatedStatus] = useState('');

  useEffect(() => {
    fetchDeliveries();
  }, []);

  const fetchDeliveries = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/api/delivery-status');
      setDeliveries(response.data);
      setFilteredDeliveries(response.data);
      toast.success('Deliveries fetched successfully!');
    } catch (error) {
      console.error('Error fetching deliveries:', error);
      toast.error('Failed to fetch deliveries.');
    } finally {
      setLoading(false);
    }
  };

  const handleFilterByDate = () => {
    if (!startDate || !endDate) {
      toast.error('Please select both start and end dates.');
      return;
    }

    const filtered = deliveries.filter((del) => {
      const delDate = new Date(del.updatedAt);
      return delDate >= new Date(startDate) && delDate <= new Date(endDate);
    });

    setFilteredDeliveries(filtered);
  };

  const handleFilterByStatus = async () => {
    if (!selectedStatus) {
      toast.error('Please select a status.');
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/delivery-status/status/${selectedStatus}`);
      setFilteredDeliveries(response.data);
      toast.success(`Deliveries with status "${selectedStatus}" fetched!`);
    } catch (error) {
      console.error('Error filtering by status:', error);
      toast.error('Failed to fetch deliveries.');
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
      await axios.put(`http://localhost:8080/api/delivery-status/${id}/status`, {
        status: updatedStatus, 
      });

      toast.success(`Status updated to "${updatedStatus}"`);
      fetchDeliveries(); 
    } catch (error) {
      console.error('Error updating status:', error);
      toast.error('Failed to update status.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="application-container">
      <h1>All Deliveries</h1>

     
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
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="DELIVERED">DELIVERED</option>
          </select>
          <button onClick={handleFilterByStatus}>Filter</button>
        </div>
      </div>

      {loading ? (
        <p>Loading deliveries...</p>
      ) : (
        <table className="applications-table">
          <thead>
            <tr>
              <th>Delivery ID</th>
              <th>Application ID</th>
              <th>Status</th>
              <th>Last Updated</th>
              <th>Update Status</th>
            </tr>
          </thead>
          <tbody>
            {filteredDeliveries.length > 0 ? (
              filteredDeliveries.map((del) => (
                <tr key={del.deliveryId}>
                  <td>{del.deliveryId}</td>
                  <td>{del.applicationId || 'N/A'}</td>
                  <td>{del.status}</td>
                  <td>{new Date(del.updatedAt).toLocaleString()}</td>
                  <td>
                    <select value={updatedStatus} onChange={(e) => setUpdatedStatus(e.target.value)}>
                      <option value="">Select Status</option>
                      <option value="IN_PROGRESS">IN_PROGRESS</option>
                      <option value="DELIVERED">DELIVERED</option>
                    </select>
                    <button onClick={() => handleUpdateStatus(del.deliveryId)}>Update</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5">No deliveries found.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
      <ToastContainer />
    </div>
  );
}

export default Deliverys;
