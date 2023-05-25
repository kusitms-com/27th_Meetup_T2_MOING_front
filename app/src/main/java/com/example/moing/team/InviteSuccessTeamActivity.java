package com.example.moing.team;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.moing.MainActivity;
import com.example.moing.R;
import com.example.moing.s3.DownloadImageCallback;
import com.example.moing.s3.S3Utils;

public class InviteSuccessTeamActivity extends AppCompatActivity {
    ImageButton btn_back;
    TextView tv_moing;
    Button btn_join, btn_home;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_success_team);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        tv_moing = (TextView) findViewById(R.id.tv_moing);
        btn_join = (Button) findViewById(R.id.btn_join);
        btn_home = (Button) findViewById(R.id.btn_home);
        img = (ImageView) findViewById(R.id.iv_fire);

//        // S3 이미지 다운로드 -> 이미지 뷰에 설정
//        // 소모임 사진 설정
//        S3Utils.downloadImageFromS3(img, new DownloadImageCallback() {
//            @Override
//            public void onImageDownloaded(byte[] data) {
//                runOnUiThread(() -> Glide.with(InviteSuccessTeamActivity.this)
//                        .asBitmap()
//                        .load(data)
//                        .centerCrop()
//                        .transform(new RoundedCorners(24))
//                        .into(img));
//            }
//
//            @Override
//            public void onImageDownloadFailed() {
//
//            }
//        });

        // 뒤로 가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 모임에 따라 TextView 선언 해줘야 한다!!!!!

        // 바로 모임 입장하기
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 홈으로 돌아가기
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}