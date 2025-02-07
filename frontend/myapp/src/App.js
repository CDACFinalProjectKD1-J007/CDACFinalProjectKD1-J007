import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import UserLogin from './screens/Login';
import AdminLogin from './screens/admin';
import SignUp from './screens/signup';
import ResetPassword from './screens/Forgetpassword';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Homepage from './homepage';
import Footer from './screens/components/Footer';
import UserProfilepage from './screens/UserProfilePage';
import UserProfileSettings from './screens/UserProfileSettings';
import Profilepage from './screens/ProfilePage';
import ProfileSettings from './screens/ProfileSettings';
import Logout from './screens/logout';
import Header from './screens/components/Header';
import SearchVoter from './screens/SearchVoter';
import ApplicationStatus from './screens/ApplicationStatus';
import ComplaintsManagement from './screens/ComplaintsManagement';
import FillingComplaint from './screens/FillingComplaint';

import DeliveryStatus from './screens/DeliveryStatus';

import VoterIDPage from './screens/MyVoterId';


function App() {
  return (
    <Router>
      <div className="app-container">
      
        <ToastContainer position="top-center" autoClose={3000} />

        <Header/>
        <Routes>
          <Route path="/" element={<Homepage />} /> 
          <Route path="/Signup" element={<SignUp />} /> 
          <Route path="/userlogin" element={<UserLogin />} />
          <Route path="/adminlogin" element={<AdminLogin />} />
          <Route path="/forgetpassword" element={<ResetPassword />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/user/profilepage" element={<UserProfilepage />} />
          <Route path="/user/profile/settings" element={<UserProfileSettings />} />
          <Route path="/user/profile/overview" element={<UserProfilepage/>} />
          <Route path="/admin/profilepage" element={<Profilepage />} />
          <Route path="/admin/profile/overview" element={<Profilepage/>} />
          <Route path="/admin/profile/settings" element={<ProfileSettings />} />

<Route path="/admin/searchvoter" element={<SearchVoter/>} />
<Route path="/user/ApplicationStatus" element={<ApplicationStatus/>} />
<Route path="/admin/ApplicationStatus" element={<ApplicationStatus/>} />
<Route path="/admin/ComplaintsManagement" element={<ComplaintsManagement/>} />
<Route path="/user/FillingComplaint" element={<FillingComplaint/>} />


<Route path="/admin/DeliveryStatus" element={<DeliveryStatus />} />
<Route path="/user/DeliveryStatus" element={<DeliveryStatus />} />
<Route path="/user/MyVoterID" element={<VoterIDPage />} />


        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
