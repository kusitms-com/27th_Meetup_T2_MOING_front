package com.example.moing.Response;

import com.google.gson.annotations.SerializedName;

public class RegisterNameResponse {
    private int statusCode;
    private String message;
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

    public class Data {
        private String result;

        public String getResult() {
            return result;
        }
    }
}

