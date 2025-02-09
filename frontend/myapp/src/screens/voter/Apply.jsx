import React from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function VoterCardApplication() {
  const userId = localStorage.getItem("userId");

  const handleApply = async () => {
    try {
      if (!userId) {
        toast.error("User ID not found. Please log in again.");
        return;
      }

      const requestBody = {
        user: { userId: parseInt(userId) },
        status: "Pending"
      };

      const response = await axios.post("http://localhost:8080/api/applications", requestBody);
      toast.success("Application ID created successfully: " + response.data.applicationId);
    } catch (error) {
      console.error("Error creating application ID:", error);
      toast.error("only one application ID can be created");
    }
  };

  const handleApplyForDelivery = async () => {
    try {
      if (!userId) {
        toast.error("User ID not found. Please log in again.");
        return;
      }

      let applicationId;
      try {
        const applicationResponse = await axios.get(`http://localhost:8080/api/applications/user/${userId}`);
        if (!applicationResponse.data || !applicationResponse.data.applicationId) {
          toast.error("Delivery already placed");
          return;
        }
        applicationId = applicationResponse.data.applicationId;
      } catch (error) {
        toast.error("No application found. Please apply first.");
        return;
      }

      const response = await axios.post("http://localhost:8080/api/delivery-status/create", { applicationId });

      if (response.status === 200) {
        toast.success("Delivery created successfully!");
      } else {
        toast.error("Error: " + response.data);
      }
    } catch (error) {
      console.error("Error:", error);
      toast.error(" delivery already created.");
    }
  };

  const handleGetVoterId = async () => {
    try {
      if (!userId || isNaN(userId)) {
        toast.error("User ID not found. Please log in again.");
        return;
      }

      const response = await axios.get(`http://localhost:8080/api/delivery-status/voterid/${userId}`);
      const voterId = response.data;

      if (!voterId) {
        toast.error("No voter ID found. Please check your application status.");
        return;
      }

      toast.success(`Your Voter ID: ${voterId}`);

     
      await axios.put(`http://localhost:8080/api/applications/user/${userId}/voter-id`, { voterId });
    } catch (error) {
      console.error("Error retrieving voter ID:", error);
      toast.error("No voter ID found. Please check your application status.");
    }
  };

  return (
    <div>
      <h2>Voter Card Application</h2>
      <button onClick={handleApply}>Apply for Application ID</button>
      <button onClick={handleApplyForDelivery}>Apply for Delivery</button>
      <button onClick={handleGetVoterId}>Get Voter ID</button>
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
}

export default VoterCardApplication;
