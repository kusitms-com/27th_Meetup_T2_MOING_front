package com.example.moing.Request;

public class ChangeJwt {
    private String refreshToken;
    private int userId;

    public ChangeJwt(String refreshToken, int userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
