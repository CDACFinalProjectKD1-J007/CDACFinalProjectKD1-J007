import { ToastContainer } from "react-toastify";
import AdminLogin from "./screens/admin";
import UserLogin from "./screens/Login";
import SignUp from "./screens/signup";
import { useState } from "react";

function Homepage () {
    const [activeTab, setActiveTab] = useState('signup');
  
    const handleTabChange = (tab) => {
      setActiveTab(tab);
    };
  
    return (
      
      <div className="container">
        <div className="tabs">
          <button
            className={`tab ${activeTab === 'user' ? 'active' : ''}`}
            onClick={() => handleTabChange('user')}
          >
            User Login
          </button>
          <button
            className={`tab ${activeTab === 'admin' ? 'active' : ''}`}
            onClick={() => handleTabChange('admin')}
          >
            Admin Login
          </button>
          <button
            className={`tab ${activeTab === 'signup' ? 'active' : ''}`}
            onClick={() => handleTabChange('signup')}
          >
            Sign Up
          </button>
        </div>
  
        {activeTab === 'user' && <UserLogin />}
        {activeTab === 'admin' && <AdminLogin />}
        {activeTab === 'signup' && <SignUp />}
        <ToastContainer position="top-center" autoClose={3000} />
    </div>

  );
};

export default Homepage;