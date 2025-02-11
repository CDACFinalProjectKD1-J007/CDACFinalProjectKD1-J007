package com.Voterid.controller;

import com.Voterid.pojo.Document;
import com.Voterid.dto.ErrorResponse;
import com.Voterid.dto.LoginRequest;
import com.Voterid.dto.PasswordChangeRequest;
import com.Voterid.pojo.User;
import com.Voterid.enums.Role;
import com.Voterid.dao.DocumentDAO;
import com.Voterid.dao.UserDAO;
import com.Voterid.dto.UserDTO;
import com.Voterid.exception.UserNotFoundException;
import com.Voterid.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DocumentDAO documentDAO;
    
    @GetMapping("/{userId}/photo")
    public ResponseEntity<Resource> getUserPhoto(@PathVariable Long userId) {
        Optional<Document> document = documentDAO.findByUser_UserId(userId);

        if (document.isEmpty() || document.get().getA4PhotoUrl() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            String dbFilePath = document.get().getA4PhotoUrl(); 
            
            
            Path filePath = Paths.get(System.getProperty("user.dir"), dbFilePath).normalize();
            
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource resource = new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userDAO.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/voter/{voterNumber}")
    @Operation(summary = "Find voter by Voter Number")
    public ResponseEntity<User> getVoterByVoterNumber(@PathVariable String voterNumber) {
        Optional<User> voter = userDAO.findByVoterNumber(voterNumber);

        if (voter.isPresent()) {
            return ResponseEntity.ok(voter.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestBody PasswordChangeRequest request) {

        boolean success = userService.changePassword(id, request.getCurrentPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect current password");
        }
    }


    
    @PostMapping("/signup")
    @Operation(summary = "Create a new user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        
        if (userDAO.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error: Email is already taken!"));
        }

     
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isAuthenticated = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            if (!isAuthenticated) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid credentials"));
            }

            User user = userDAO.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));

            System.out.println("DEBUG: User role from DB -> " + user.getRole());

            if (user.getRole() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("User role is null! Please contact support."));
            }

           
            if (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.VOTER)) {
                return ResponseEntity.ok(new UserDTO(user.getUserId(), user.getName(), user.getEmail(), user.getRole()));
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Unknown role! Please contact support."));

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred"));
        }
    }
   

        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        }
    


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
