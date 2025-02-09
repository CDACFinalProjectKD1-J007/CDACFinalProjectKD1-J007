import React, { useState } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/DeliveryStatus.css';

function DeliveryStatus() {
  const [deliveryId, setDeliveryId] = useState('');
  const [deliveryDetails, setDeliveryDetails] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!deliveryId) {
      toast.error('Please enter a valid Delivery ID!');
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/delivery-status/${deliveryId}`);
      setDeliveryDetails(response.data);
      toast.success('Delivery status fetched successfully!');
    } catch (error) {
      console.error('Error fetching delivery status:', error);
      toast.error('Failed to fetch delivery status. Please check the ID.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="delivery-container">
      <h1>Check Delivery Status</h1>
      <form className="delivery-form" onSubmit={handleSubmit}>
        <label htmlFor="deliveryId">Delivery ID</label>
        <input
          type="text"
          id="deliveryId"
          value={deliveryId}
          onChange={(e) => setDeliveryId(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Checking...' : 'Check Status'}
        </button>
      </form>

      {deliveryDetails && (
        <div className="delivery-details">
          <h2>Delivery Details</h2>
          <p><strong>ID:</strong> {deliveryDetails.deliveryId}</p>
          <p><strong>Application ID:</strong> {deliveryDetails.applicationId}</p>
          <p><strong>Status:</strong> {deliveryDetails.status}</p>
          <p><strong>Created At:</strong> {new Date(deliveryDetails.createdAt).toLocaleString()}</p>
          <p><strong>Updated At:</strong> {new Date(deliveryDetails.updatedAt).toLocaleString()}</p>
        </div>
      )}

      <ToastContainer />
    </div>
  );
}

export default DeliveryStatus;
