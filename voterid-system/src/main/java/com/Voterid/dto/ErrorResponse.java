package com.Voterid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;

    
    public ErrorResponse(String message) {
        this.message = message;
        this.statusCode = 400; 
        this.timestamp = LocalDateTime.now();
    }
}
