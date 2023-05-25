package com.example.moing.team;

import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("teamId") // 고유 id
    int teamId;
    @SerializedName("personnel") // 인원수
    int personnel;
    @SerializedName("approvalStatus") // 승인 여부
    boolean approvalStatus;
    @SerializedName("name") // 이름
    String name;
    @SerializedName("profileImg") // 대표 사진
    String profileImg;
    @SerializedName("startDate") // 시작 날짜
    String startDate;
    @SerializedName("endDate") // 종료 날짜
    String endDate;

    public Team(int teamId, String name, int personnel, String startDate, String endDate, String profileImg, boolean approvalStatus) {
        this.teamId = teamId;
        this.name = name;
        this.personnel = personnel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.profileImg = profileImg;
        this.approvalStatus = approvalStatus;
    }
}
