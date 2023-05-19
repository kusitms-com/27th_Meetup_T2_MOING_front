package com.example.moing.board;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoteCommentResponse {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<VoteComment> data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<VoteComment> getData() {
        return data;
    }

    public VoteCommentResponse(int statusCode, String message, List<VoteComment> data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static class VoteComment {
        @SerializedName("voteCommentId")
        private int voteCommentId;

        @SerializedName("content")
        private String content;

        @SerializedName("userId")
        private int userId;

        @SerializedName("nickName")
        private String nickName;

        @SerializedName("userImageUrl")
        private String userImageUrl;

        @SerializedName("createdDate")
        private String createdDate;

        public int getVoteCommentId() {
            return voteCommentId;
        }

        public String getContent() {
            return content;
        }

        public int getUserId() {
            return userId;
        }

        public String getNickName() {
            return nickName;
        }

        public String getUserImageUrl() {
            return userImageUrl;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public VoteComment(int voteCommentId, String content, int userId, String nickName, String userImageUrl, String createdDate) {
            this.voteCommentId = voteCommentId;
            this.content = content;
            this.userId = userId;
            this.nickName = nickName;
            this.userImageUrl = userImageUrl;
            this.createdDate = createdDate;
        }
    }
}
