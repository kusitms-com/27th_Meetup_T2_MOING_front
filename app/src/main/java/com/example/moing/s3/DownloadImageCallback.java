package com.example.moing.s3;

/** downloadImageFromS3의 결과를 처리하기 위한 콜백 인터페이스 **/
public interface DownloadImageCallback {
    void onImageDownloaded(byte[] data);
    void onImageDownloadFailed();
}