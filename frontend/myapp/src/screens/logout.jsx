import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './css/logout.css';

function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
    
    
    setTimeout(() => {
      navigate('/'); 
    }, 3000); 
  }, [navigate]);

  return (
    <div className="logout-container">
      <h1>You have been logged out</h1>
      <p>Redirecting to the homepage...</p>
    </div>
  );
}

export default Logout;
