import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import UserLogin from './screens/Login';
import SignUp from './screens/signup';
import ResetPassword from './screens/Forgetpassword';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Homepage from './homepage';
import Profilepage from './screens/admin/ProfilePage';
import ProfileSettings from './screens/admin/ProfileSettings';
import SearchVoter from './screens/admin/SearchVoter';
import ApplicationStatus from './screens/voter/ApplicationStatus';
import ComplaintsManagement from './screens/admin/ComplaintsManagement';
import FillingComplaint from './screens/voter/FillingComplaint';
import Logout from './screens/logout';
import DeliveryStatus from './screens/voter/DeliveryStatus';
import UserProfilepage from './screens/voter/UserProfilePage';
import UserProfileSettings from './screens/voter/UserProfileSettings';
import VoterIDPage from './screens/voter/MyVoterId';
import NotFound from './screens/NotFound';
import ProtectedRoute from "./screens/ProtectedRoute";
import Applications from './screens/admin/Applications';
import Deliverys from './screens/admin/Deliverys';
import ChangePassword from './screens/changepassword';
import VoterCardApplication from './screens/voter/Apply';
import DocumentUpload from './screens/voter/DocumentUpload';



function App() {
  return (
    <Router>
      <div className="app-container">
        <ToastContainer position="top-center" autoClose={3000} />

        <Routes>
  <Route path="/" element={<Homepage />} />
  <Route path="/signup" element={<SignUp />} />
  <Route path="/login" element={<UserLogin />} />
  <Route path="/forgetpassword" element={<ResetPassword />} />

  {/* Voter Protected Routes */}
  <Route element={<ProtectedRoute allowedRoles={["VOTER"]} />}>
    <Route path="/voter/profilepage" element={<UserProfilepage />} />
    <Route path="/voter/profile/settings" element={<UserProfileSettings />} />
    <Route path="/voter/ApplicationStatus" element={<ApplicationStatus />} />
    <Route path="/voter/FillingComplaint" element={<FillingComplaint />} />
    <Route path="/voter/DeliveryStatus" element={<DeliveryStatus />} />
    <Route path="/voter/MyVoterID" element={<VoterIDPage />} />
    <Route path="/voter/Change-Password" element={<ChangePassword/>}/>
    <Route path="/voter/Documents" element={<DocumentUpload/>}/> 
    <Route path="/voter/Apply" element={<VoterCardApplication />}/> 
  </Route>

  {/* Admin Protected Routes */}
  <Route element={<ProtectedRoute allowedRoles={["ADMIN"]} />}>
    <Route path="/admin/profilepage" element={<Profilepage />} />
    <Route path="/admin/profile/settings" element={<ProfileSettings />} />
    <Route path="/admin/searchvoter" element={<SearchVoter />} />
    <Route path="/admin/Applications" element={<Applications />} />
    <Route path="/admin/ComplaintsManagement" element={<ComplaintsManagement />} />
    <Route path="/admin/Deliverys" element={<Deliverys />} />
    <Route path="/admin/Change-Password" element={<ChangePassword/>}/>
  </Route>

  {/* Logout Route */}
  <Route path="/logout" element={<Logout />} />

  {/* Catch-all route for undefined paths */}
  <Route path="*" element={<NotFound />} />
</Routes>;


      </div>
    </Router>
  );
}

export default App;
