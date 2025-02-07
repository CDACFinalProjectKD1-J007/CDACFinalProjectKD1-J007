import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/UserLogin.css';

function ApplicationStatus() {
  const [applicationId, setApplicationId] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (applicationId) {
      toast.success('Application status fetched successfully!');
    } else {
      toast.error('Please enter a valid Application ID!');
    }
  };

  const handleChange = (e) => {
    setApplicationId(e.target.value);
  };

  return (
    <div className="login-container">
      <h1>Application Status</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="applicationId">Application ID</label>
        <input
          type="text"
          id="applicationId"
          value={applicationId}
          onChange={handleChange}
          required
        />

        <button type="submit">Check Status</button>
      </form>
      <ToastContainer />
    </div>
  );
}

export default ApplicationStatus;
