import React, { useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./css/UserLogin.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function UserLogin() {
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!loginData.email || !loginData.password) {
      toast.error("Please fill in all fields!");
      return;
    }

    if (!emailRegex.test(loginData.email)) {
      toast.error("Please enter a valid email address!");
      return;
    }

    setLoading(true); 
    try {
      const response = await axios.post("http://localhost:8080/api/users/login", loginData);

      if (response.data.userId && response.data.role) {
        toast.success("Login Successful!");

        localStorage.setItem("userId", response.data.userId);
        localStorage.setItem("userRole", response.data.role);
        localStorage.setItem("authToken", response.data.token); 

        const userRole = response.data.role.toUpperCase(); 

        if (userRole === "ADMIN") {
          navigate("/admin/profilepage");
        } else if (userRole === "VOTER") {
          navigate("/voter/profilepage");
        } else {
          toast.error("Unknown role! Please contact support.");
        }
      } else {
        toast.error("Login failed. Please try again.");
      }
    } catch (err) {
      console.error("Login Error:", err);
      toast.error(err.response?.data?.message || "An unexpected error occurred.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <h1>Login</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          placeholder="Email Address"
          value={loginData.email}
          onChange={handleChange}
          required
          autoComplete="email"
        />
        
        <label htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          name="password"
          placeholder="Password"
          value={loginData.password}
          onChange={handleChange}
          required
          autoComplete="current-password"
        />
        
        <button type="submit" className="submit-btn" disabled={loading}>
          {loading ? "Logging in..." : "User Login"}
        </button>
      </form>
      <br />
      <Link to="/forgetpassword">Forgot password?</Link>
      <ToastContainer />
    </div>
  );
}

export default UserLogin;
