package com.example.moing.Response;

public class BoardFireResponse {
    private int statusCode;
    private String message;
    private Long data;

    public BoardFireResponse(int statusCode, String message, Long data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Long getData() {
        return data;
    }
}
