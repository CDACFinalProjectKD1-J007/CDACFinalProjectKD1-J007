import { ToastContainer } from "react-toastify";
import UserLogin from "./screens/Login";
import SignUp from "./screens/signup";
import { useState } from "react";
import "./screens/css/homepage.css"

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
            Login
          </button>
        
          <button
            className={`tab ${activeTab === 'signup' ? 'active' : ''}`}
            onClick={() => handleTabChange('signup')}
          >
            Sign Up
          </button>
        </div>
  
        {activeTab === 'user' && <UserLogin />}
        {activeTab === 'signup' && <SignUp />}
        <ToastContainer position="top-center" autoClose={3000} />
    </div>

  );
};

export default Homepage;