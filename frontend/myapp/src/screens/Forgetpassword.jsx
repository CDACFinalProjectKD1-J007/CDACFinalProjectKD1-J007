import React, { useState } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';  
import './css/UserLogin.css';

function ResetPassword() {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [step, setStep] = useState(1); 

  const handleSendOTP = async (e) => {
    e.preventDefault();
    if (!email) {
      toast.error('Please enter your email!');
      return;
    }

    try {
      await axios.post(`http://localhost:8080/api/otp/send?email=${email}`);
      toast.success('OTP sent to your email!');
      setStep(2);
    } catch (error) {
      toast.error('Failed to send OTP. Please try again.');
      console.error('Error:', error);
    }
  };

  const handleVerifyOTP = async (e) => {
    e.preventDefault();
    if (!otp) {
      toast.error('Please enter OTP!');
      return;
    }

    try {
      await axios.post(`http://localhost:8080/api/otp/verify?email=${email}&otp=${otp}`);
      toast.success('OTP verified successfully!');
      setStep(3);
    } catch (error) {
      toast.error('Invalid OTP. Please try again.');
      console.error('Error:', error);
    }
  };

  const handleResetPassword = async (e) => {
    e.preventDefault();
    
    if (!newPassword || !confirmPassword) {
      toast.error('Please fill in all fields!');
      return;
    }

    if (newPassword !== confirmPassword) {
      toast.error('Passwords do not match!');
      return;
    }

    if (newPassword.length < 8) {
      toast.error('Password must be at least 8 characters long!');
      return;
    }

    try {
      await axios.post('http://localhost:8080/api/otp/reset-password', {
        email,
        otp,
        newPassword
      });
      toast.success('Password reset successfully!');
      setTimeout(() => {
        window.location.href = '/login';
      }, 2000);
    } catch (error) {
      toast.error('Failed to reset password. Please try again.');
      console.error('Error response:', error.response?.data); 
    }
    
  };

  return (
    <div className="login-container">
      <h1>Reset Password</h1>
      
      {step === 1 && (
        <form className="login-form" onSubmit={handleSendOTP}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <button type="submit">Send OTP</button>
        </form>
      )}

      {step === 2 && (
        <form className="login-form" onSubmit={handleVerifyOTP}>
          <p className="email-display">OTP sent to: {email}</p>
          <label htmlFor="otp">Enter OTP</label>
          <input
            type="text"
            id="otp"
            placeholder="Enter OTP"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            maxLength="6"
            required
          />
          <button type="submit">Verify OTP</button>
          <button 
            type="button" 
            className="resend-button"
            onClick={handleSendOTP}
          >
            Resend OTP
          </button>
        </form>
      )}

      {step === 3 && (
        <form className="login-form" onSubmit={handleResetPassword}>
          <label htmlFor="newPassword">New Password</label>
          <input
            type="password"
            id="newPassword"
            placeholder="Enter new password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
          <label htmlFor="confirmPassword">Confirm Password</label>
          <input
            type="password"
            id="confirmPassword"
            placeholder="Confirm new password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          <button type="submit">Reset Password</button>
        </form>
      )}
      
      <ToastContainer />
    </div>
  );
}

export default ResetPassword;