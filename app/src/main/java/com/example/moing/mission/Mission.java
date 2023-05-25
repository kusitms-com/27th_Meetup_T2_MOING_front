package com.example.moing.mission;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Mission {
    @SerializedName("userMissionId")
    int userMissionId;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("profileImg")
    String profileImg;
    @SerializedName("status")
    String status;
    @SerializedName("archive")
    String archive;
    @SerializedName("submitDate")
    String submitDate;

    public Mission(int userMissionId, String nickname, String profileImg, String status, String archive, String submitDate) {
        this.userMissionId = userMissionId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.status = status;
        this.archive = archive;
        this.submitDate = submitDate;
    }
}
