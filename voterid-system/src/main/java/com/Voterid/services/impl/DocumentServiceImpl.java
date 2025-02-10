package com.Voterid.services.impl;

import com.Voterid.dao.DocumentDAO;
import com.Voterid.dao.UserDAO;
import com.Voterid.pojo.Document;
import com.Voterid.pojo.User;
import com.Voterid.services.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DocumentServiceImpl  implements DocumentService{
    
    @Autowired
    private DocumentDAO documentDAO;
    
    @Autowired
    private UserDAO userDAO;

    private final Path rootLocation = Paths.get("uploads/documents");

    public Document getDocumentById(Long documentId) {
        return documentDAO.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));
    }

    public List<Document> getDocumentsByUserId(Long userId) {
        return documentDAO.findAllByUser_UserId(userId);
    }

    public Document createDocument(Document document) {
        return documentDAO.save(document);
    }

    public Document uploadDocument(Long userId, String documentType, MultipartFile file) throws IOException {
        
        User user = userDAO.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        
        Files.createDirectories(rootLocation);

        
        String filename = userId + "_" + documentType + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), rootLocation.resolve(filename));
        String fileUrl = "/uploads/documents/" + filename;

        Document document = documentDAO.findByUser_UserId(userId).orElse(new Document());
        document.setUser(user);

        switch (documentType.toLowerCase()) {
            case "aadhar":
                document.setAadharCardUrl(fileUrl);
                break;
            case "pan":
                document.setPanCardUrl(fileUrl);
                break;
            case "birth":
                document.setBirthCertificateUrl(fileUrl);
                break;
            case "photo":
                document.setA4PhotoUrl(fileUrl);
                break;
            default:
                throw new RuntimeException("Invalid document type: " + documentType);
        }

        return documentDAO.save(document);
    }

    public void deleteDocument(Long documentId) {
        Document document = getDocumentById(documentId);

        
        deleteFileIfExists(document.getAadharCardUrl());
        deleteFileIfExists(document.getPanCardUrl());
        deleteFileIfExists(document.getBirthCertificateUrl());
        deleteFileIfExists(document.getA4PhotoUrl());

        documentDAO.deleteById(documentId);
    }

    private void deleteFileIfExists(String fileUrl) {
        if (fileUrl != null && !fileUrl.isEmpty()) {
            try {
                Path filePath = Paths.get("." + fileUrl);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Error deleting file: " + fileUrl);
            }
        }
    }
}
