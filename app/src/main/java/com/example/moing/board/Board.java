package com.example.moing.board;

import com.google.gson.annotations.SerializedName;

public class Board {
    @SerializedName("content") // 내용
    String content;
    @SerializedName("title") // 제목
    String title;
    @SerializedName("voteId") // 고유 id
    int voteId;

    public Board(String content, String title, int voteId) {
        this.content = content;
        this.title = title;
        this.voteId = voteId;
    }

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
