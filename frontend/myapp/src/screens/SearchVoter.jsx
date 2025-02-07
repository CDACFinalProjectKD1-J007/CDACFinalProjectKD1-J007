import React, { useState } from 'react';
import './css/UserLogin.css';

function SearchVoter() {
  const [formData, setFormData] = useState({
    voterId: '',
    name: '',
    dob: '',
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form submitted:', formData);
  };

  return (
    <div className="login-container">
      <h1>Search Voter</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="voterId">Voter ID</label>
        <input
          type="text"
          id="voterId"
          value={formData.voterId}
          onChange={handleChange}
          required
        />

        <label htmlFor="name">Name</label>
        <input
          type="text"
          id="name"
          value={formData.name}
          onChange={handleChange}
        />

        <label htmlFor="dob">Date of Birth</label>
        <input
          type="date"
          id="dob"
          value={formData.dob}
          onChange={handleChange}
        />

        <button type="submit">Search</button>
      </form>
    </div>
  );
}

export default SearchVoter;
