package com.Voterid.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String storeFile(MultipartFile file) throws IOException;
}
