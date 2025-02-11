import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../css/ProfileSettings.css';

function UserProfileSettings() {
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');

  const [profile, setProfile] = useState({
    name: '',
    email: '',
    district: '',
    phoneNumber: '',
    address: '',
    state: '',
    status: '',
    profileImage: '',
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!userId) {
      toast.error('User not found. Please log in again.');
      navigate('/login');
      return;
    }

    axios.get(`http://localhost:8080/api/users/${userId}`)
      .then((response) => {
        setProfile(response.data);
      })
      .catch((error) => {
        console.error('Error fetching user data:', error);
        toast.error('Failed to load profile.');
      });
  }, [userId, navigate]);

  const handleProfileUpdate = async () => {
    if (!profile.name || !profile.phoneNumber || !profile.district || !profile.address || !profile.state || !profile.status) {
      toast.error('All fields are required!');
      return;
    }

    setLoading(true);
    try {
      await axios.put(`http://localhost:8080/api/users/${userId}`, profile);
      toast.success('Profile updated successfully!');
    } catch (error) {
      console.error('Error updating profile:', error);
      toast.error(error.response?.data?.message || 'Failed to update profile.');
    } finally {
      setLoading(false);
    }
  };

  const handleNavigation = (path) => {
    navigate(path);
  };

  const handleLogout = () => {
    localStorage.clear();
    toast.success("Logged out successfully!");
    navigate('/login');
  };

  return (
    <div className="profile-page">
      <nav className="navbar horizontal-navbar">
        <h1 className="navbar-brand">User Portal</h1>
        <div className="navbar-links">
          <button onClick={() => handleNavigation('/voter/FillingComplaint')}>
            Complaints
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
            <button onClick={() => handleNavigation('/voter/change-password')}>
              Change password
            </button>
          </li>
          <li>
            <button onClick={handleLogout}>Logout</button>
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
            value={profile.name}
            onChange={(e) => setProfile({ ...profile, name: e.target.value })}
          />
          <input
            type="email"
            placeholder="Email"
            value={profile.email}
            readOnly 
          />
          <input
            type="text"
            placeholder="Phone Number"
            value={profile.phoneNumber}
            onChange={(e) => setProfile({ ...profile, phoneNumber: e.target.value })}
          />
          <input
            type="text"
            placeholder="District"
            value={profile.district}
            onChange={(e) => setProfile({ ...profile, district: e.target.value })}
          />
          <input
            type="text"
            placeholder="State"
            value={profile.state}
            onChange={(e) => setProfile({ ...profile, state: e.target.value })}
          />
          <input
            type="text"
            placeholder="Address"
            value={profile.address}
            onChange={(e) => setProfile({ ...profile, address: e.target.value })}
          />
          
          <select
            name="status"
            value={profile.status}
       onChange={(e) => setProfile({ ...profile, status:e.target.value })}
            
          >
            <option value="" disabled>Status</option>
            <option value="ACTIVE">ACTIVE</option>
            <option value="INACTIVE">INACTIVE</option>
                  </select>

          <button onClick={handleProfileUpdate} disabled={loading}>
            {loading ? "Updating..." : "Update Profile"}
          </button>
        </div>
        <ToastContainer />
      </div>
    </div>
  );
}

export default UserProfileSettings;




