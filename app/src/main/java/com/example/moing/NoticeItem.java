package com.example.moing;

public class NoticeItem {

    String notice_tv_user_name;
    String notice_tv_title;
    String notice_tv_content;
    String notice_tv_message_count;
    Integer notice_iv_user_image;
    Integer notice_iv_crown;
    Integer notice_iv_dot;
    Integer notice_iv_message;

    public NoticeItem(String notice_tv_user_name, String notice_tv_title, String notice_tv_content, String notice_tv_message_count, Integer notice_iv_user_image,
                      Integer notice_iv_crown, Integer notice_iv_dot, Integer notice_iv_message) {

        this.notice_tv_user_name = notice_tv_user_name;
        this.notice_tv_title = notice_tv_title;
        this.notice_tv_content = notice_tv_content;
        this.notice_tv_message_count = notice_tv_message_count;
        this.notice_iv_user_image = notice_iv_user_image;
        this.notice_iv_crown = notice_iv_crown;
        this.notice_iv_dot = notice_iv_dot;
        this.notice_iv_message = notice_iv_message;

    }

    public String getNotice_tv_user_name() { return notice_tv_user_name; }

    public String getNotice_tv_title() {
        return notice_tv_title;
    }

    public String getNotice_tv_content() { return notice_tv_content; }

    public String getNotice_tv_message_count() { return notice_tv_message_count; }

    public Integer getNotice_iv_user_image() {
        return notice_iv_user_image;
    }

    public Integer getNotice_iv_crown() { return notice_iv_crown; }

    public Integer getNotice_iv_dot() { return notice_iv_dot; }

    public Integer getNotice_iv_message() {
        return notice_iv_message;
    }

    public void setNotice_tv_user_name(String notice_tv_user_name) {
        this.notice_tv_user_name = notice_tv_user_name;
    }

    public void setNotice_tv_title(String notice_tv_title) {
        this.notice_tv_title = notice_tv_title;
    }

    public void setNotice_tv_content(String notice_tv_content) {
        this.notice_tv_content = notice_tv_content;
    }

    public void setNotice_tv_message_count(String notice_tv_message_count) {
        this.notice_tv_message_count = notice_tv_message_count;
    }

    public void setNotice_iv_user_image(Integer notice_iv_user_image) {
        this.notice_iv_user_image = notice_iv_user_image;
    }

    public void setNotice_iv_crown(Integer notice_iv_crown) {
        this.notice_iv_crown = notice_iv_crown;
    }

    public void setNotice_iv_dot(Integer notice_iv_dot) {
        this.notice_iv_dot = notice_iv_dot;
    }

    public void setNotice_iv_message(Integer notice_iv_message) {
        this.notice_iv_message = notice_iv_message;
    }

}

