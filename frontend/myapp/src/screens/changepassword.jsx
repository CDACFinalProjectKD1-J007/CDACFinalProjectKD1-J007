import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/ProfileSettings.css';

function ChangePassword() {
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');

  const [password, setPassword] = useState({
    currentPassword: '',
    newPassword: '',
    confirmNewPassword: '',
  });

  const handlePasswordChange = async () => {
    const { currentPassword, newPassword, confirmNewPassword } = password;

    if (!currentPassword || !newPassword || !confirmNewPassword) {
      toast.error('All fields are required!');
      return;
    }

    if (newPassword !== confirmNewPassword) {
      toast.error('New passwords do not match!');
      return;
    }

    try {
      const response = await axios.put(`http://localhost:8080/api/users/${userId}/password`, {
        currentPassword,
        newPassword,
      });

      if (response.status === 200) {
        toast.success('Password changed successfully!');
        setTimeout(() => navigate('/admin/profilepage'), 2000);
      }
    } catch (error) {
      console.error('Error changing password:', error);
      toast.error(error.response?.data || 'Failed to change password.');
    }
  };

  return (
    <div className="profile-page">
      <nav className="navbar horizontal-navbar">
        <h1 className="navbar-brand">Change Password Portal</h1>
      </nav>

      <div className="sidebar">
        <h2>Profile</h2>
        <ul>
          <li><button onClick={() => navigate('/admin/profilepage')}>Overview</button></li>
          <li><button onClick={() => navigate('/admin/profile/settings')}>Settings</button></li>
          <li><button onClick={() => navigate('/admin/change-password')}>Change Password</button></li>
          <li><button onClick={() => { localStorage.clear(); navigate('/logout'); }}>Logout</button></li>
        </ul>
      </div>

      <div className="profile-settings">
        <h2>Change Password</h2>
        <div className="password-section">
          <input type="password" placeholder="Current Password" value={password.currentPassword} 
            onChange={(e) => setPassword({ ...password, currentPassword: e.target.value })} />
          <input type="password" placeholder="New Password" value={password.newPassword} 
            onChange={(e) => setPassword({ ...password, newPassword: e.target.value })} />
          <input type="password" placeholder="Confirm Password" value={password.confirmNewPassword} 
            onChange={(e) => setPassword({ ...password, confirmNewPassword: e.target.value })} />
          <button onClick={handlePasswordChange}>Change Password</button>
        </div>
        <ToastContainer />
      </div>
    </div>
  );
}

export default ChangePassword;
