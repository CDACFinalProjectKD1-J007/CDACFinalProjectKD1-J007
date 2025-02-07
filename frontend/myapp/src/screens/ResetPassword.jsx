import React, { useState } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';  
import './css/UserLogin.css';

function ResetPassword() {
  const [email, setEmail] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!email) {
      toast.error('Please enter your email!');
      return;
    }
    toast.success('Password reset link sent to your email!');  
  };

  return (
    <div className="login-container">
      <h1>Reset Password</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="email">Email</label>
        <input
          type="email"
          id="email"
          placeholder="Enter your email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit">Reset</button>
      </form>
      <ToastContainer />
    </div>
  );
}

export default ResetPassword;
