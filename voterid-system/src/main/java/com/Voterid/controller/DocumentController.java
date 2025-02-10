package com.Voterid.controller;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.Voterid.dao.DocumentDAO;
import com.Voterid.dao.UserDAO;
import com.Voterid.pojo.Document;
import com.Voterid.pojo.User;


import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private DocumentDAO documentDAO;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocuments(
        @RequestParam("userId") Long userId,
        @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCard,
        @RequestParam(value = "panCard", required = false) MultipartFile panCard,
        @RequestParam(value = "birthCertificate", required = false) MultipartFile birthCertificate,
        @RequestParam(value = "a4Photo", required = false) MultipartFile a4Photo) {

        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        User user = userOptional.get();
        Document document = documentDAO.findByUser_UserId(userId).orElse(new Document());

        document.setUser(user);

        if (aadharCard != null && !aadharCard.isEmpty()) {
            document.setAadharCardUrl(saveFile(aadharCard));
        }
        if (panCard != null && !panCard.isEmpty()) {
            document.setPanCardUrl(saveFile(panCard));
        }
        if (birthCertificate != null && !birthCertificate.isEmpty()) {
            document.setBirthCertificateUrl(saveFile(birthCertificate));
        }
        if (a4Photo != null && !a4Photo.isEmpty()) {
            document.setA4PhotoUrl(saveFile(a4Photo));
        }

        document.setUpdatedAt(LocalDateTime.now());

        documentDAO.save(document);
        return ResponseEntity.ok("Documents uploaded/updated successfully!");
    }

    private String saveFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
   



    @GetMapping("/{userId}")
    public ResponseEntity<List<Document>> getDocumentsByUserId(@PathVariable Long userId) {
        List<Document> documents = documentDAO.findAllByUser_UserId(userId);
        if (documents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(documents);
    }
}
