package com.example.moing.mission;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.moing.R;

public class MissionClickActivity extends AppCompatActivity {

    private SubsamplingScaleImageView picture;
    private Dialog dialog;
    private ImageButton back_dialog;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_click);

        // 사진 확대 기능
        picture = (SubsamplingScaleImageView) findViewById(R.id.mission_pic);

        /** 이미지 setting 예시 **/
//        imageView.setImage(ImageSource.resource(R.drawable.monkey));
//        imageView.setImage(ImageSource.asset("map.png"))
//        imageView.setImage(ImageSource.uri("/sdcard/DCIM/DSCM00123.JPG"));

        // 뒤로 가기
        back = findViewById(R.id.btn_close);
        // 사진 팝업 버튼
        dialog = new Dialog(MissionClickActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.mission_picture_dialog);
        // 다이얼로그에서 뒤로가기 버튼
        back_dialog = dialog.findViewById(R.id.btn_back);


        back.setOnClickListener(backClickListener);
        picture.setOnClickListener(pictureClickListener);
        // 다이얼로그 뒤로 가기 버튼
        back_dialog.setOnClickListener(dialogBackClickListener);
    }

    // 뒤로 가기 버튼 클릭 시
    View.OnClickListener backClickListener = v -> { finish();};

    // 사진 클릭 시
    View.OnClickListener pictureClickListener = v -> {
        dialog.show();
    };

    // 다이얼로그에서 뒤로 나오기
    View.OnClickListener dialogBackClickListener = v -> {
      dialog.dismiss();
    };
}