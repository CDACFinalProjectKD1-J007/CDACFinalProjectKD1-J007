package com.Voterid.services.impl;


import com.Voterid.pojo.User;
import com.Voterid.services.OTPService;
import com.Voterid.services.UserService;
import com.Voterid.dao.UserDAO;
import com.Voterid.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OTPService otpService;

    
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
  


    
    public User getUserById(Long id) {
        return userDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public User createUser(User user) {
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return userDAO.save(user);
    }
 
    public User findUserById(Long userId) {
        Optional<User> user = userDAO.findById(userId);
        return user.orElse(null); 
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setDistrict(userDetails.getDistrict());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAddress(userDetails.getAddress());
        user.setState(userDetails.getState());
        user.setStatus(userDetails.getStatus());
        return userDAO.save(user);
    }
    
    public void deleteUser(Long id) {
        if (!userDAO.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userDAO.deleteById(id);
    }
    public boolean changePassword(Long id, String currentPassword, String newPassword) {
        User user = userDAO.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

      
        if (!user.getPassword().equals(currentPassword)) {
            return false;
        }

       
        user.setPassword(newPassword);
        userDAO.save(user);

        return true;
    }

    @Transactional
    public boolean resetPassword(String email, String otp, String newPassword) {
        System.out.println("Resetting password for: " + email);

        boolean isValidOTP = otpService.verifyOTP(email, otp);
        System.out.println("Is OTP valid? " + isValidOTP);

        if (!isValidOTP) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userDAO.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

       
        user.setPassword(newPassword);  

        userDAO.save(user);
        userDAO.flush();

        System.out.println("Password reset successful!");
        return true;
    }
    public boolean authenticate(String email, String rawPassword) {
        User user = userDAO.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found!"));

        rawPassword = rawPassword.trim();

       
        return rawPassword.equals(user.getPassword());
    }


}