package com.example.moing.board;

public class Vote {
    // 투표 항목
    private String voteContent;
    private boolean isSelected;

//    public Vote(String voteContent) {
//        this.voteContent = voteContent;
//        this.isSelected = false; // 초기값 false로 세팅
//    }ㄱ

    public void setVoteContent(String voteContent) {
        this.voteContent = voteContent;
    }

    public String getVoteContent() {
        return voteContent;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
