import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function AdminLogin () {
  const [loginData, setLoginData] = useState({
    email: '',
    password: '',
  });
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    
    if (!loginData.email || !loginData.password) {
      toast.error('Please fill in all fields!');
      return;
    }

    
    try {
      const result = await Login(loginData);
      if (result.status === 'success') {
        toast.success('Admin Login Successful!');
        navigate('/admin/profilepage'); 
      } else {
        toast.error(result.error || 'Login failed. Please try again.');
      }
    } catch (err) {
      toast.error('An unexpected error occurred. Please try again.');
    }
  };

  
  const Login = async ({ email, password }) => {
    return new Promise((resolve) =>
      setTimeout(() => {
        if (email === 'admin@gmail.com' && password === 'password123') {
          resolve({ status: 'success' });
        } else {
          resolve({ status: 'error', error: 'Invalid credentials!' });
        }
      }, 1000)
    );
  };

  return (
    <div className="login-container">
      <h1>Admin Login</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <input
          type="email"
          name="email"
          placeholder="Email Address"
          value={loginData.email}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={loginData.password}
          onChange={handleChange}
          required
        />
        <button type="submit" className="submit-btn">
          Admin Login
        </button>
        <br />
        <br />
        <br />
        <Link to="/forgetpassword">Forget password</Link>
      </form>

      <ToastContainer />
    </div>
  );
};

export default AdminLogin;
