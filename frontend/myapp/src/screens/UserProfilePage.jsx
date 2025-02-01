import React from 'react';
import { useNavigate } from 'react-router-dom';
import './css/ProfilePage.css';
import Header from './components/Header';

function UserProfilepage() {
  const navigate = useNavigate();

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <div className="profile-page">
       <Header/>
   
      <div className="sidebar">
        <h2>Profile</h2>
        <ul>
          <li>
            <button onClick={() => handleNavigation('/user/profile/overview')}>Overview</button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/user/profile/settings')}>Settings</button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/logout')}>Logout</button>
          </li>
        </ul>
      </div>

      <div className="main-content">
        <div className="profile-summary">
          <h3>Profile Overview</h3>
          <p><strong>Name:</strong> Aditya Aghaw</p>
          <p><strong>Email:</strong> adityaaghaw@gmail.com</p>
          <p><strong>Contact:</strong> 9011814510</p>
        </div>
      </div>
    </div>
  );
}

export default UserProfilepage;
