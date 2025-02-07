import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/UserLogin.css';

function FillingComplaint() {
  const [complaintType, setComplaintType] = useState('');
  const [description, setDescription] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (complaintType && description) {
      toast.success('Complaint submitted successfully!');
    } else {
      toast.error('Please fill in all fields!');
    }
  };

  const handleComplaintTypeChange = (e) => {
    setComplaintType(e.target.value);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  return (
    <div className="login-container">
      <h1>Filling Complaint</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="complaintType">Complaint Type</label>
        <select
          id="complaintType"
          value={complaintType}
          onChange={handleComplaintTypeChange}
          required
        >
          <option value="">Select Type</option>
          <option value="registration">Registration Issue</option>
          <option value="update">Update Information</option>
          <option value="other">Other</option>
        </select>

        <label htmlFor="description">Description</label>
        <textarea
          id="description"
          rows="5"
          value={description}
          onChange={handleDescriptionChange}
          required
        />

        <button type="submit">Submit</button>
      </form>
      <ToastContainer />
    </div>
  );
}

export default FillingComplaint;
