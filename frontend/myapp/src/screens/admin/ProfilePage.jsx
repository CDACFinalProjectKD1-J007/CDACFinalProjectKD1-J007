import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';  
import '../css/ProfilePage.css';

function Profilepage() {
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
        <h1 className="navbar-brand">Admin Portal</h1>
        <div className="navbar-links">
          <button onClick={() => handleNavigation('/admin/Searchvoter')}>Search Voter</button>
          <button onClick={() => handleNavigation('/admin/ComplaintsManagement')}>Complaints</button>
          <button onClick={() => handleNavigation('/admin/Applications')}>Applications</button>
          <button onClick={() => handleNavigation('/admin/Deliverys')}>Deliverys</button>
        </div>
      </nav>

      <div className="sidebar">
        <h2>Profile</h2>
        <ul>
          <li><button onClick={() => handleNavigation('/admin/profilepage')}>Overview</button></li>
          <li><button onClick={() => handleNavigation('/admin/profile/settings')}>Settings</button></li>
          <li><button onClick={() => navigate('/admin/change-password')}>Change Password</button></li>
          <li><button onClick={() => handleLogout('/logout')}>Logout</button></li>
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

export default Profilepage;
