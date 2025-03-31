package com.example.POS_MJ_BACK.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RespuestaError {
    private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public RespuestaError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
