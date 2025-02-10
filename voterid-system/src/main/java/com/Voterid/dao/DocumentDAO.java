package com.Voterid.dao;

import com.Voterid.pojo.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;



public interface DocumentDAO extends JpaRepository<Document, Long> {

    Optional<Document> findByUser_UserId(Long userId); 
    
    List<Document> findAllByUser_UserId(Long userId); 
    }
