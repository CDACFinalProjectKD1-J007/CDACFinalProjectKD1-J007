import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './css/UserLogin.css';
import { Link, useNavigate } from 'react-router-dom';

function UserLogin() {
  const [loginData, setLoginData] = useState({
    email: '',
    password: '',
  });

  const navigate = useNavigate(); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!loginData.email || !loginData.password) {
      toast.error('Please fill in all fields!');
      return;
    }

    if (!emailRegex.test(loginData.email)) {
      toast.error('Please enter a valid email address!');
      return;
    }

    try {
     
      const result = await Login(loginData); 

      if (result.status === 'success') {
        toast.success('User Login Successful!');
        navigate('/User/profilepage');
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
      if (email === 'adityaaghaw@hotmail.co.uk' && password === 'aditya123') {
        resolve({ status: 'success' });
      } else {
        resolve({ status: 'error', error: 'Invalid credentials!' });
      }
    }, 1000)
  );
};

  return (
    <div className="login-container">
      <h1>User Login</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="email">Email</label>
        <input
          type="email"
          name="email"
          placeholder="Email Address"
          value={loginData.email}
          onChange={handleChange}
          required
        />
        <label htmlFor="password">Password</label>
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={loginData.password}
          onChange={handleChange}
          required
        />
        <button type="submit" className="submit-btn">
          User Login
        </button>
      </form>
      <br /><br /><br />
      <Link to="/forgetpassword">Forget password</Link>
      <ToastContainer />
    </div>
  );
}

export default UserLogin;
