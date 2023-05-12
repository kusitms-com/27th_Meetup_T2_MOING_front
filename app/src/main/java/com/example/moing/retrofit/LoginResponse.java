package com.example.moing.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("refreshToken")
    private String refreshToken;
    @SerializedName("process")
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
