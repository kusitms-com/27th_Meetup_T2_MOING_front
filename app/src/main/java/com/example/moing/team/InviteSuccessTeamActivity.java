package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.moing.MainActivity;
import com.example.moing.R;

public class InviteSuccessTeamActivity extends AppCompatActivity {
    ImageButton btn_back;
    TextView tv_moing;
    Button btn_join, btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_success_team);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        tv_moing = (TextView) findViewById(R.id.tv_moing);
        btn_join = (Button) findViewById(R.id.btn_join);
        btn_home = (Button) findViewById(R.id.btn_home);

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