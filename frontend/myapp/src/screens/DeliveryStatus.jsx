import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/UserLogin.css';

function DeliveryStatus() {
  const [applicationId, setApplicationId] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (applicationId) {
      toast.success('Delivery status fetched successfully!');
    } else {
      toast.error('Please enter a valid Application ID!');
    }
  };

  const handleChange = (e) => {
    setApplicationId(e.target.value);
  };

  return (
    <div className="login-container">
      <h1>Delivery Status</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="applicationId">Delivery ID</label>
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

export default DeliveryStatus;
