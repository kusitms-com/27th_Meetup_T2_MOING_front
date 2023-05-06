package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moing.R;

import java.util.Calendar;

public class MakeTeamActivity2 extends AppCompatActivity {
    ImageButton btn_back;
    EditText et_name, et_num;
    Button btn_next,btn_start;
    TextView tv_nameCnt;
    // 날짜
    DatePickerDialog datePickerDialog;
    // 소모임 목표
    String dream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team2);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_start = (Button) findViewById(R.id.btn_start);
        et_name = (EditText) findViewById(R.id.et_name);
        et_num = (EditText) findViewById(R.id.et_number);
        tv_nameCnt = (TextView) findViewById(R.id.tv_nameCount);

        // 소모임 목표 값 전달받기
        Intent intent = getIntent();
        // 목표값 전달
        dream = intent.getStringExtra("major");
        Log.d("MAKETEAMACTIVITY2", dream);


        // 뒤로 가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 소모임 이름 최대 10칸 입력 가능
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = "(" + s.length() + "/10)";
                tv_nameCnt.setText(result);
            }
        });

        // 소모임 이름 Enter키 입력 방지
        et_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==event.KEYCODE_ENTER) return true;
                return false;
            }
        });

        // 소모임 구성원 수 Enter키 입력 방지
        et_num.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==event.KEYCODE_ENTER) return true;
                return false;
            }
        });

        // 최대 구성원 수가 20명이 넘어갔을
//        if(Integer.parseInt(et_num.getText().toString()) >= 20)
//            Toast.makeText(getApplicationContext(), "구성원 수를 20명 이내로 입력해주세요.", Toast.LENGTH_SHORT).show();

        // 시작 날짜 설정
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR); // 년
                int pMonth = calendar.get(Calendar.MONTH); // 월
                int pDay = calendar.get(Calendar.DAY_OF_MONTH); // 일

                datePickerDialog = new DatePickerDialog(MakeTeamActivity2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month + 1;
                                String data = year + "/" + month + "/" + day;
                                btn_start.setText(data);
                            }
                        }, pYear, pMonth, pDay);
                datePickerDialog.show();
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