package com.example.moing.Response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("process_token")
    private String process;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getProcess() {
        return process;
    }

    public LoginResponse(String accessToken, String refreshToken, String process) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.process = process;
    }
}
