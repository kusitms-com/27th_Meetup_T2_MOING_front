package com.example.moing.board;

public class Vote {
    // 투표 항목
    String voteContent;

    public Vote(String voteContent) {
        this.voteContent = voteContent;
    }

    public String getVoteContent() {
        return voteContent;
    }

    public void setVoteContent(String voteContent) {
        this.voteContent = voteContent;
    }
}
