package com.example.POS_MJ_BACK.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO<T> {
    private int status;
    private String mensaje;
    private T data;
}
