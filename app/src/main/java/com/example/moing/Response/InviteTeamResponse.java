package com.example.moing.Response;

import com.google.gson.annotations.SerializedName;

public class InviteTeamResponse {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("teamId")
        private int teamId;

        public int getTeamId() {
            return teamId;
        }
    }
}
