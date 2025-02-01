import React from 'react';
import { useNavigate } from 'react-router-dom';
import './css/ProfilePage.css';
import './css/Header.css'
import AdminHeader from './components/Header';

function Profilepage() {
  const navigate = useNavigate();

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <div className="profile-page">
          <AdminHeader/>
      <div className="sidebar">
        <h2>Profile</h2>
        <ul>
          <li>
            <button onClick={() => handleNavigation('/admin/profile/overview')}>Overview</button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/admin/profile/settings')}>Settings</button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/logout')}>Logout</button>
          </li>
        </ul>
      </div>

      <div className="main-content">
        <div className="profile-summary">
          <h3>Profile Overview</h3>
          <p><strong>Name:</strong> admin1</p>
          <p><strong>Email:</strong> admin@gmail.com</p>
          <p><strong>Contact:</strong> 90XXXXXXXX</p>
        </div>
      </div>
    </div>
  );
}

export default Profilepage;
