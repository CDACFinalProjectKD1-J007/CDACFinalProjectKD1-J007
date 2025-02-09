import React, { useState, useEffect } from "react";
import axios from "axios";
import "../css/UserLogin.css";

function SearchVoter() {
  const [formData, setFormData] = useState({ voterId: "" });
  const [voterData, setVoterData] = useState(null);
  const [allVoters, setAllVoters] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/users")  
      .then((response) => setAllVoters(response.data))
      .catch((error) => console.error("Error fetching voters:", error));
  }, []);
  

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
  
    try {
      const response = await axios.get(
        `http://localhost:8080/api/users/voter/${formData.voterId}`
      );
      setVoterData(response.data);
    } catch (err) {
      setError("Voter not found or an error occurred.");
      setVoterData(null);
      console.error(err);
    }
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
        <button type="submit">Search</button>
      </form>

      {error && <p className="error">{error}</p>}

      {voterData && (
        <div className="voter-details">
          <h2>Voter Details</h2>
          <p><strong>Voter Number:</strong> {voterData.voterNumber}</p>
          <p><strong>Name:</strong> {voterData.name}</p>
          <p><strong>Date of Birth:</strong> {voterData.dob}</p>
          <p><strong>Address:</strong> {voterData.address}</p>
        </div>
      )}

      <hr />

      <h1>All Voters</h1>
      <table className="voter-table">
        <thead>
          <tr>
            <th>Voter Number</th>
            <th>Name</th>
            <th>Date of Birth</th>
            <th>Address</th>
          </tr>
        </thead>
        <tbody>
          {allVoters.length > 0 ? (
            allVoters.map((voter) => (
              <tr key={voter.voterNumber}>
                <td>{voter.voterNumber}</td>
                <td>{voter.name}</td>
                <td>{voter.dob}</td>
                <td>{voter.address}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4">No voters found.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default SearchVoter;
