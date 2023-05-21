package com.example.moing.Response;

import java.util.List;

public class BoardNoReadVoteResponse {
    private int statusCode;
    private String message;
    private List<VoteData> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VoteData> getData() {
        return data;
    }

    public void setData(List<VoteData> data) {
        this.data = data;
    }

    public static class VoteData {
        private String content;
        private String title;
        private int voteId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVoteId() {
            return voteId;
        }

        public void setVoteId(int voteId) {
            this.voteId = voteId;
        }
    }
}
