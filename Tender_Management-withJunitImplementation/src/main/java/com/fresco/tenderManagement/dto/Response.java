// Response Class
package com.fresco.tenderManagement.dto;

public class Response {
    private String token;
    private String status;

    // Constructor
    public Response(String token, String status) {
        this.token = token;
        this.status = status;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}