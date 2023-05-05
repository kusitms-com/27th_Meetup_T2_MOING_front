package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.moing.R;

public class MakeTeamActivity2 extends AppCompatActivity {
    ImageButton btn_back;
    Button btn_next;
    // 소모임 목표
    String dream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team2);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        // 목표값 전달
        dream = intent.getStringExtra("major");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 다음으로 버튼 눌렀을 때
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeTeamActivity3.class);
                // 보낼 데이터들 아직 미구현.. 구현되면 바로 값 넣어서 보낼 예정!
                //intent.putExtra("major", major);
                startActivity(intent);
            }
        });
    }
}