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

function App() {
  return (
    <Router>
      <div className="app-container">
      
        <ToastContainer position="top-center" autoClose={3000} />

        
        <Routes>
          <Route path="/" element={<Homepage />} /> 
          <Route path="/Signup" element={<SignUp />} /> 
          <Route path="/userlogin" element={<UserLogin />} />
          <Route path="/adminlogin" element={<AdminLogin />} />
          <Route path="/forgetpassword" element={<ResetPassword />} />
   
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
