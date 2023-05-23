package com.example.moing.Response;

import java.util.List;

public class MissionListResponse {
    private int statusCode;
    private String message;
    private List<MissionData> data;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<MissionData> getData() {
        return data;
    }

    public static class MissionData {
        private int missionId;
        private String title;
        private String dueTo;
        private String status;

        public int getMissionId() {
            return missionId;
        }

        public String getTitle() {
            return title;
        }

        public String getDueTo() {
            return dueTo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
