package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.moing.R;

public class AddTeamActivity extends AppCompatActivity {

    Button btn_invite;
    Button btn_make;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        btn_invite = (Button)findViewById(R.id.btn_invite);
        btn_make = (Button)findViewById(R.id.btn_makeTeam);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        // 뒤로 가기 버튼 눌렀을 때
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 모임에 초대받았을 때
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_invite.setTextColor(Color.WHITE);
                Intent intent = new Intent(getApplicationContext(), InviteTeamActivity.class);
                startActivity(intent);
            }
        });

        // 직접 만들어보고 싶을 때
        btn_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_make.setTextColor(Color.WHITE);
                // Intent 문 필요.
            }
        });
    }
}