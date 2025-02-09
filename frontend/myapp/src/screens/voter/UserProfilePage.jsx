import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/ProfilePage.css';
import axios from 'axios';

function UserProfilepage() {
  const navigate = useNavigate();
  const [userProfile, setUserProfile] = useState(null);

  useEffect(() => {
    
    const userId = localStorage.getItem("userId"); 

    if (userId) {
      axios.get(`http://localhost:8080/api/users/${userId}`)
        .then(response => setUserProfile(response.data))
        .catch(error => console.error("Error fetching user profile:", error));
    }
  }, []);

  const handleNavigation = (path) => {
    navigate(path);
  };

  const handleLogout = (path) => {
    localStorage.clear();
    navigate(path);
  };

  return (
    <div className="profile-page">
      <nav className="navbar horizontal-navbar">
        <h1 className="navbar-brand">User Portal</h1>
        <div className="navbar-links">
          <button onClick={() => handleNavigation('/voter/FillingComplaint')}>
            Complaints
          </button>
          <button onClick={() => handleNavigation('/voter/Documents')}>
           Add Documents
          </button>
          <button onClick={() => handleNavigation('/voter/ApplicationStatus')}>
            Application Status
          </button>
          <button onClick={() => handleNavigation('/voter/DeliveryStatus')}>
            Delivery Status
          </button>
          <button onClick={() => handleNavigation('/voter/MyVoterId')}>
            My Voter ID
          </button>
        </div>
      </nav>

      <div className="sidebar">
        <h2>Profile</h2>
        <ul>
          <li>
            <button onClick={() => handleNavigation('/voter/profilepage')}>
              Overview
            </button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/voter/profile/settings')}>
              Settings
            </button>
          </li>
          <li>
            <button onClick={() => navigate('/voter/change-password')}>Change Password</button>
          </li>
          <li>
            <button onClick={() => navigate('/voter/Apply')}>Apply for Votercard</button>
          </li>
          
          <li>
          <button onClick={() => handleLogout('/logout')}>Logout</button>
          </li>

        </ul>
      </div>

     
      <div className="main-content">
        {userProfile ? (
          <div className="profile-summary">
            <h3>Profile Overview</h3>
            <p><strong>Name:</strong> {userProfile.name}</p>
            <p><strong>Email:</strong> {userProfile.email}</p>
            <p><strong>District:</strong> {userProfile.district}</p>
            <p><strong>Phone Number:</strong> {userProfile.phoneNumber}</p>
            <p><strong>Address:</strong> {userProfile.address}</p>
            <p><strong>State:</strong> {userProfile.state}</p>
            <p><strong>Status:</strong> {userProfile.status}</p>
          </div>
        ) : (
          <p>Loading profile...</p>
        )}
      </div>
    </div>
  );
}

export default UserProfilepage;
