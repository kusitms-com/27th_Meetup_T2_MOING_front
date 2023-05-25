package com.example.moing.s3;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.IOException;

public class S3Utils {
    private static final String TAG = "S3Utils";

    private static final String ACCESS_KEY = "AKIA5L3BRFIIWLIJ62JQ";
    private static final String SECRET_KEY = "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";
    private static final String BUCKET_NAME = "moing-images";

    /** AWS S3에 파일 업로드를 수행하는 메소드  **/
    public static void uploadImageToS3(Context context, String fileName, File file) {

        // accessKey 와 secretKey 를 사용하는 기본 자격 증명 객체
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        // AWS S3 서비스와 상호 작용하기 위한 클라이언트 객체
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

        // Amazon S3 전송 관리를 쉽게 할 수 있도록 하는 고급 API - for Image File Upload
        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(context.getApplicationContext()).build();
        TransferNetworkLossHandler.getInstance(context.getApplicationContext());
        TransferObserver uploadObserver = transferUtility.upload(BUCKET_NAME, fileName, file);

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                // 업로드 상태 변화 처리
                if (state == TransferState.COMPLETED) {
                    // 업로드 성공
                    Log.d(TAG, "Upload Completed");
                } else if (state == TransferState.FAILED || state == TransferState.CANCELED) {
                    // 업로드 실패 또는 취소
                    Log.d(TAG, "Upload Failed or Canceled");
                }
            }

            @Override
            public void onProgressChanged(int id, long current, long total) {
                // 업로드 진행 상태 처리
                int percentDone = (int) ((current * 100) / total);
                Log.d(TAG, "Upload Progress: " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // 업로드 오류 처리
                Log.e(TAG, "Upload Error: " + ex.getMessage());
            }
        });
    }

    public static void downloadImageFromS3(String objectKey, DownloadImageCallback callback) {
        new Thread(() -> {

            // accessKey 와 secretKey 를 사용하는 기본 자격 증명 객체
            AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

            // AWS S3 서비스와 상호 작용하기 위한 클라이언트 객체
            AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

            try {
                // 이미지 다운로드
                S3Object s3Object = s3Client.getObject(new GetObjectRequest(BUCKET_NAME, objectKey));
                S3ObjectInputStream inputStream = s3Object.getObjectContent();

                // InputStream -> byte[] : Glide를 통해 이미지를 로딩하기 위해 변환
                byte[] data;
                try {
                    data = IOUtils.toByteArray(inputStream);
                    inputStream.close();

                    // 이미지 다운로드 성공 시 콜백 호출
                    callback.onImageDownloaded(data);

                } catch (IOException e) {
                    e.printStackTrace();
                    // 이미지 다운로드 실패 시 콜백 호출
                    callback.onImageDownloadFailed();
                }

            } catch (AmazonS3Exception e) {
                e.printStackTrace();
                Log.d(TAG, "존재하지 않는 이미지");
                // 이미지 다운로드 실패 시 콜백 호출
                callback.onImageDownloadFailed();
            }
        }).start();
    }
}



