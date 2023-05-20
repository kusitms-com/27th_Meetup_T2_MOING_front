package com.example.moing.Response;

import java.util.List;

public class BoardNoReadResponse {
    private String message;
    private int statusCode;
    private List<NoticeData> data;

    public BoardNoReadResponse(String message, int statusCode, List<NoticeData> data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<NoticeData> getData() {
        return data;
    }

    public static class NoticeData {
        private String content;
        private String title;

        public NoticeData(String content, String title) {
            this.content = content;
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }
    }
}
