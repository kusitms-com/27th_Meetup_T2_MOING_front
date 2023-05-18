package com.example.moing.board;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoteInfo {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private voteData data;

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

    public voteData getData() {
        return data;
    }

    public void setData(voteData data) {
        this.data = data;
    }

    public VoteInfo(int statusCode, String message, voteData data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    /** Data 클래스 **/
    public class voteData {
        @SerializedName("title")
        private String title;

        @SerializedName("memo")
        private String memo;

        @SerializedName("createdDate")
        private String createdDate;

        @SerializedName("userId")
        private int userId;

        @SerializedName("nickName")
        private String nickName;

        @SerializedName("userImageUrl")
        private String userImageUrl;

        @SerializedName("notReadUsersNickName")
        private List<String> notReadUsersNickName;

        @SerializedName("voteChoices")
        private List<VoteChoice> voteChoices;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserImageUrl() {
            return userImageUrl;
        }

        public void setUserImageUrl(String userImageUrl) {
            this.userImageUrl = userImageUrl;
        }

        public List<String> getNotReadUsersNickName() {
            return notReadUsersNickName;
        }

        public void setNotReadUsersNickName(List<String> notReadUsersNickName) {
            this.notReadUsersNickName = notReadUsersNickName;
        }

        public List<VoteChoice> getVoteChoices() {
            return voteChoices;
        }

        public void setVoteChoices(List<VoteChoice> voteChoices) {
            this.voteChoices = voteChoices;
        }

        public voteData(String title, String memo, String createdDate, int userId, String nickName,
                    String userImageUrl, List<String> notReadUsersNickName, List<VoteChoice> voteChoices) {
            this.title = title;
            this.memo = memo;
            this.createdDate = createdDate;
            this.userId = userId;
            this.nickName = nickName;
            this.userImageUrl = userImageUrl;
            this.notReadUsersNickName = notReadUsersNickName;
            this.voteChoices = voteChoices;
        }
    }

    /** 투표 선택 클래스 **/
    public static class VoteChoice {
        @SerializedName("content")
        private String content;

        @SerializedName("num")
        private int num;

        @SerializedName("voteUserNickName")
        private List<String> voteUserNickName;

        /** 클릭 여부 확인 **/
        private boolean isChecked;
        public boolean isChecked() {
            return isChecked;
        }
        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<String> getVoteUserNickName() {
            return voteUserNickName;
        }

        public void setVoteUserNickName(List<String> voteUserNickName) {
            this.voteUserNickName = voteUserNickName;
        }

        public VoteChoice(String content, int num, List<String> voteUserNickName) {
            this.content = content;
            this.num = num;
            this.voteUserNickName = voteUserNickName;
        }
    }
}
