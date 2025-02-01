import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/ProfileSettings.css';
import AdminHeader from './components/Header';

function ProfileSettings() {
  const navigate = useNavigate();

  const [profile, setProfile] = useState({
    name: 'Aditya Aghaw',
    email: 'adityaaghaw@gmail.com',
    contact: '9011814510',
  });

  const [password, setPassword] = useState({
    currentPassword: '',
    newPassword: '',
    confirmNewPassword: '',
  });

  const handleNavigation = (path) => {
    navigate(path);
  };

  const handleProfileUpdate = () => {
    if (!profile.name || !profile.email || !profile.contact) {
      toast.error('All fields are required!');
      return;
    }

    setTimeout(() => {
      toast.success('Profile updated successfully!');
    }, 1000);
  };

  const handlePasswordChange = () => {
    const { currentPassword, newPassword, confirmNewPassword } = password;

    if (!currentPassword || !newPassword || !confirmNewPassword) {
      toast.error('All fields are required for password change!');
      return;
    }

    if (newPassword !== confirmNewPassword) {
      toast.error('New passwords do not match!');
      return;
    }

    setTimeout(() => {
      toast.success('Password changed successfully!');
    }, 1000);
  };

  return (
    <div className="profile-page">
      <AdminHeader/>
      <div className="sidebar">
        <h2>Profile</h2>
        <ul>
          <li>
            <button onClick={() => handleNavigation('/admin/profile/overview')}>
              Overview
            </button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/admin/profile/settings')}>
              Settings
            </button>
          </li>
          <li>
            <button onClick={() => handleNavigation('/logout')}>Logout</button>
          </li>
        </ul>
      </div>

      <div className="profile-settings">
        <h2>Profile Settings</h2>
        <div className="profile-section">
          <h3>Profile Information</h3>
          <input
            type="text"
            placeholder="Name"
            
            onChange={(e) => setProfile({ ...profile, name: e.target.value })}
          />
          <input
            type="email"
            placeholder="Email"
           
            onChange={(e) => setProfile({ ...profile, email: e.target.value })}
          />
          <input
            type="text"
            placeholder="Contact"
            
            onChange={(e) => setProfile({ ...profile, contact: e.target.value })}
          />
          <button onClick={handleProfileUpdate}>Update Profile</button>
        </div>

        <div className="password-section">
          <h3>Change Password</h3>
          <input
            type="password"
            placeholder="Current Password"
            value={password.currentPassword}
            onChange={(e) =>
              setPassword({ ...password, currentPassword: e.target.value })
            }
          />
          <input
            type="password"
            placeholder="New Password"
            value={password.newPassword}
            onChange={(e) =>
              setPassword({ ...password, newPassword: e.target.value })
            }
          />
          <input
            type="password"
            placeholder="Confirm Password"
            value={password.confirmNewPassword}
            onChange={(e) =>
              setPassword({ ...password, confirmNewPassword: e.target.value })
            }
          />
          <button onClick={handlePasswordChange}>Change Password</button>
        </div>

        <ToastContainer />
      </div>
    </div>
  );
}

export default ProfileSettings;
