package com.Voterid.services;

import com.Voterid.pojo.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User findUserById(Long userId);

    User updateUser(Long id, User userDetails);

    void deleteUser(Long id);

    boolean changePassword(Long id, String currentPassword, String newPassword);

    boolean resetPassword(String email, String otp, String newPassword);

    boolean authenticate(String email, String rawPassword);
}
