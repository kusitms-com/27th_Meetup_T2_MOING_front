package com.example.moing.Response;

import java.util.List;

public class BoardNoReadNoticeResponse {
    private String message;
    private int statusCode;
    private List<NoticeData> data;

    public BoardNoReadNoticeResponse(String message, int statusCode, List<NoticeData> data) {
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
        private String title;
        private String content;

        public NoticeData(String title, String content) {
            this.title = title;
            this.content = content;

        }

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }
    }
}
