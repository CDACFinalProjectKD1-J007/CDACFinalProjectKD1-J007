import React from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function VoterCardApplication() {
  const userId = localStorage.getItem("userId");
  console.log("User ID from localStorage:", userId);

  const handleApply = async () => {
    try {
      if (!userId) {
        toast.error("User ID not found. Please log in again.");
        return;
      }

      const requestBody = {
        user: { userId: parseInt(userId) },
        status: "Pending",
      };

      const response = await axios.post("http://localhost:8080/api/applications", requestBody);

      if (response.data.applicationId) {
        toast.success("Application ID created successfully: " + response.data.applicationId);
      } else {
        toast.error("Error: Application ID was not created.");
      }
    } catch (error) {
      console.error("Error creating application ID:", error);
      toast.error("Only one application ID can be created or an error occurred.");
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
       
        const applicationResponse = await axios.get(`http://localhost:8080/api/delivery-status/user/${userId}`);
        applicationId = applicationResponse.data.applicationId; 
  
        if (!applicationId) {
          toast.error("No application found. Please apply first.");
          return;
        }
      } catch (error) {
        console.error("Error fetching application:", error);
        toast.error("application not found. Please apply first.");
        return;
      }
  
      
      const response = await fetch("http://localhost:8080/api/delivery-status/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ applicationId }),
      });
  
      const data = await response.text();
      if (response.ok) {
        toast.success("Delivery created successfully!");
      } else {
        toast.error("Error: " + data);
      }
    } catch (error) {
      console.error("Error:", error);
      toast.error("Failed to create delivery.");
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
