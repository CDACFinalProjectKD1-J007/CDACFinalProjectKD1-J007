import React, { useEffect, useState } from "react";
import "../css/VoterIDPage.css";
import axios from "axios";
function VoterIDPage() {
  const [userData, setUserData] = useState(null);
  const [photoUrl, setPhotoUrl] = useState(null);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/${userId}`);
        setUserData(response.data);
      } catch (error) {
        console.error("Error fetching user data", error);
      }
    };

    const fetchUserPhoto = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/${userId}/photo`, {
          responseType: "blob",
        });
    
        const imageUrl = URL.createObjectURL(response.data);
        setPhotoUrl(imageUrl);
      } catch (error) {
        console.error("Error fetching user photo", error);
      }
    };
    
    
    if (userId) {
      fetchUserData();
      fetchUserPhoto();
    }
  }, [userId]);

  if (!userData) {
    return <p>Loading Voter ID...</p>;
  }

  return (
    <div className="voter-id-card">
      <h2>Voter ID Card</h2>
      <div className="card-content">
      {photoUrl ? <img src={photoUrl} alt="User" /> : <p>Loading photo...</p>}



        <p><strong>Name:</strong> {userData.name}</p>
        <p><strong>DOB:</strong> {userData.dob}</p>
        <p><strong>Address:</strong> {userData.address}</p>
        <p><strong>Voter ID:</strong> {userData.voterNumber}</p>
      </div>
    </div>
  );
}


export default VoterIDPage;
