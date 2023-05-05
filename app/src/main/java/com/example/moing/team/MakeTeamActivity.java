package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moing.R;

public class MakeTeamActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn_back;
    Button btn_sports, btn_life, btn_test, btn_study, btn_book, btn_proceed, btn_next;
    ImageView img_pg;
    String major="";
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_sports = (Button) findViewById(R.id.btn_sports);
        btn_sports.setOnClickListener(this);
        btn_life = (Button) findViewById(R.id.btn_life);
        btn_life.setOnClickListener(this);
        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);
        btn_study = (Button) findViewById(R.id.btn_study);
        btn_study.setOnClickListener(this);
        btn_book = (Button) findViewById(R.id.btn_book);
        btn_book.setOnClickListener(this);
        btn_proceed = (Button) findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        img_pg = (ImageView) findViewById(R.id.iv_pg1);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            // 뒤로 가기 버튼
            case R.id.btn_back:
                finish();
                break;
            // 스포츠 버튼 클릭 시
            case R.id.btn_sports:
                setColorChange(btn_sports);
                break;
            // 생활습관 개선 버튼 클릭 시
            case R.id.btn_life:
                setColorChange(btn_life);
                break;
            // 시험/취업준비 클릭 시
            case R.id.btn_test:
                setColorChange(btn_test);
                break;
            // 스터디, 공부 클릭 시
            case R.id.btn_study:
                setColorChange(btn_study);
                break;
            // 독서 클릭 시
            case R.id.btn_book:
                setColorChange(btn_book);
                break;
            // 그 외 자기계발 클릭 시
            case R.id.btn_proceed:
                setColorChange(btn_proceed);
                break;
            // 다음으로 버튼 클릭 시
            case R.id.btn_next:
                if(major.length() > 0 && count == 1) {
                    Log.d("MAKETEAMACTIVITY", major);
                    Intent intent = new Intent(getApplicationContext(), MakeTeamActivity2.class);
                    intent.putExtra("major", major);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "카테고리 하나를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void setColorChange(Button b) {
        // 선택되어 있을 때 버튼을 눌렀다면...
        if (b.isSelected()) {
            b.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity.this, R.color.secondary_grey_black_7));
            b.setSelected(false); // 선택 아님으로 변경
            count--;
            Log.d("MAKETEAMACTIVITY", String.valueOf(count));
        }

        // 선택되어 있지 않을 때 버튼을 눌렀다면 ...
        else {
            //텍스트 색깔 하얗게
            b.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity.this, R.color.secondary_grey_black_1));
            // 테두리 하얗게
            b.setSelected(true); // 선택으로 변경

            // 객체의 text 전달
            major = b.getText().toString();
            // 다음으로 버튼을 하얗게
            count++;
            Log.d("MAKETEAMACTIVITY", String.valueOf(count));
        }

        if (count != 1) {
            btn_next.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity.this, R.color.secondary_grey_black_10));
            btn_next.setBackgroundResource(R.drawable.button_round_black12);
            img_pg.setBackgroundResource(R.drawable.maketeam_progress1);
        } else {
            btn_next.setBackgroundResource(R.drawable.button_round_black1);
            // 다음으로 버튼 텍스트를 검정색으로!
            btn_next.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity.this, R.color.secondary_grey_black_12));
            img_pg.setBackgroundResource(R.drawable.maketeam_progress2);
        }
    }

    public void onResume() {
        super.onResume();
        count=0;
        btn_next.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity.this, R.color.secondary_grey_black_10));
        btn_next.setBackgroundResource(R.drawable.button_round_black12);
        changeColorDark(btn_sports);
        changeColorDark(btn_life);
        changeColorDark(btn_test);
        changeColorDark(btn_study);
        changeColorDark(btn_book);
        changeColorDark(btn_proceed);
        img_pg.setBackgroundResource(R.drawable.maketeam_progress1);
    }

    public void changeColorDark(Button b) {
        if (b.isSelected()) {
            b.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity.this, R.color.secondary_grey_black_7));
            b.setSelected(false); // 선택 아님으로 변경
        }
    }
}