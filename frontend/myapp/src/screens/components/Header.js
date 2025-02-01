import React from 'react';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <header>
      <nav className="navbar horizontal-navbar">
        <h1 className="navbar-brand">User Portal</h1>
        <ul className="navbar-links">
          <li><Link to="/user/FillingComplaint">Filling Complaint</Link></li>
          <li><Link to="/user/ApplicationStatus">Application Status</Link></li>
          <li><Link to="/user/MyVoterId">My Voter ID</Link></li>
          <li><Link to="/user/DeliveryStatus">Delivery Status</Link></li>
        </ul>
      </nav>
    </header>
  );
}

export default Header;
