package com.example.moing.team;

public class Team {
    String image;
    String title;
    String memberNum;
    String missionStart;
    String missionEnd;

    public Team(String title, String memberNum, String missionStart, String missionEnd) {
        this.title = title;
        this.memberNum = memberNum;
        this.missionStart = missionStart;
        this.missionEnd = missionEnd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getMissionStart() {
        return missionStart;
    }

    public void setMissionStart(String missionStart) {
        this.missionStart = missionStart;
    }

    public String getMissionEnd() {
        return missionEnd;
    }

    public void setMissionEnd(String missionEnd) {
        this.missionEnd = missionEnd;
    }
}
