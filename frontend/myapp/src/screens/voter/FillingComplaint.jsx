import React, { useState, useEffect, useCallback } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/UserLogin.css';

function FillingComplaint() {
  const userId = localStorage.getItem('userId'); 

  const [formData, setFormData] = useState({
    complaintType: '',
    description: '',
  });

  const [userComplaints, setUserComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterDate, setFilterDate] = useState('');

  
  const fetchUserComplaints = useCallback(async () => {
    if (!userId) {
      toast.error("User ID not found. Please log in.");
      return;
    }

    try {
      let url = `http://localhost:8080/api/complaints/user/${userId}`;
      if (filterDate) {
        url += `?date=${filterDate}`; 
      }

      const response = await fetch(url);
      if (!response.ok) throw new Error('Failed to fetch complaints');
      const data = await response.json();
      setUserComplaints(data);
    } catch (error) {
      console.error('Error fetching complaints:', error);
      toast.error('Error loading complaints.');
    } finally {
      setLoading(false);
    }
  }, [userId, filterDate]);

  useEffect(() => {
    fetchUserComplaints();
  }, [fetchUserComplaints]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!userId) {
      toast.error("User ID not found. Please log in.");
      return;
    }

    const { complaintType, description } = formData;

    if (!complaintType || !description) {
      toast.error('Please fill in all fields!');
      return;
    }

    const requestBody = {
      complaintType,
      description,
      status: "FORWARDED",
      userId: parseInt(userId),
    };

    try {
      const response = await fetch('http://localhost:8080/api/complaints', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody),
      });

      const result = await response.json();

      if (response.ok) {
        toast.success('Complaint submitted successfully!');
        setFormData({ complaintType: '', description: '' });
        fetchUserComplaints();
      } else {
        toast.error(result.message || 'Failed to submit complaint.');
      }
    } catch (error) {
      console.error("Error submitting complaint:", error);
      toast.error('An unexpected error occurred.');
    }
  };

  const handleDelete = async (complaintId) => {
    if (!complaintId || isNaN(Number(complaintId))) {
      console.error("Invalid complaint ID received:", complaintId);
      toast.error("Invalid complaint ID.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/complaints/${complaintId}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        toast.success("Complaint deleted successfully!");
        fetchUserComplaints();
      } else {
        const errorMessage = await response.text();
        toast.error(errorMessage || "Failed to delete complaint.");
      }
    } catch (error) {
      console.error("Error deleting complaint:", error);
      toast.error("An unexpected error occurred.");
    }
  };

  return (
    <div className="login-container">
      <h1>File a Complaint</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="complaintType">Complaint Type</label>
        <select id="complaintType" name="complaintType" value={formData.complaintType} onChange={handleChange} required>
          <option value="">Select Type</option>
          <option value="registration">Registration Issue</option>
          <option value="update">Update Information</option>
          <option value="other">Other</option>
        </select>

        <label htmlFor="description">Description</label>
        <textarea id="description" name="description" rows="5" value={formData.description} onChange={handleChange} required />

        <button type="submit" className="submit-btn">Submit</button>
      </form>

      <h2>Previous Complaints</h2>

      <label>Filter by Date:</label>
      <input type="date" value={filterDate} onChange={(e) => setFilterDate(e.target.value)} />

      {loading ? (
        <p>Loading complaints...</p>
      ) : userComplaints.length > 0 ? (
        <ul className="complaint-list">
          {userComplaints.map((complaint) => (
            <li key={complaint.complaintId} className="complaint-item">
              <strong>Type:</strong> {complaint.complaintType} <br />
              <strong>Description:</strong> {complaint.description} <br />
              <strong>Status:</strong> {complaint.status} <br />
              <button onClick={() => handleDelete(complaint.complaintId)}>Delete</button>
            </li>
          ))}
        </ul>
      ) : (
        <p>No complaints found.</p>
      )}

      <ToastContainer />
    </div>
  );
}

export default FillingComplaint;
