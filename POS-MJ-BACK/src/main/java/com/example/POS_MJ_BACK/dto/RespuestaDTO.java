package com.example.POS_MJ_BACK.dto;

public class RespuestaDTO<T> {
    private int status;
    private String mensaje;
    private T data; // Campo genérico para datos opcionales

    // Constructor para respuestas sin datos
    public RespuestaDTO(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    // Constructor para respuestas con datos
    public RespuestaDTO(int status, String mensaje, T data) {
        this.status = status;
        this.mensaje = mensaje;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
