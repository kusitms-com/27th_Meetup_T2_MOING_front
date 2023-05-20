package com.example.moing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeCommentResponse {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<NoticeComment> data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<NoticeComment> getData() {
        return data;
    }

    public NoticeCommentResponse(int statusCode, String message, List<NoticeComment> data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static class NoticeComment {
        @SerializedName("noticeCommentId")
        private int noticeCommentId;

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

        public int getNoticeCommentId() {
            return noticeCommentId;
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

        public NoticeComment(int noticeCommentId, String content, int userId, String nickName, String userImageUrl, String createdDate) {
            this.noticeCommentId = noticeCommentId;
            this.content = content;
            this.userId = userId;
            this.nickName = nickName;
            this.userImageUrl = userImageUrl;
            this.createdDate = createdDate;
        }
    }
}

