package com.Voterid.services;

import com.Voterid.pojo.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    Document getDocumentById(Long documentId);

    List<Document> getDocumentsByUserId(Long userId);

    Document createDocument(Document document);

    Document uploadDocument(Long userId, String documentType, MultipartFile file) throws IOException;

    void deleteDocument(Long documentId);
}
