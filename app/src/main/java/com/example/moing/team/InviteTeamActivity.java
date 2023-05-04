package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moing.R;

public class InviteTeamActivity extends AppCompatActivity {
    ImageButton btn_back;
    EditText et_code;
    Button btn_check, btn_realCheck;
    TextView tv_code, tv3, tv4;
    public String checkCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_team);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_realCheck = (Button) findViewById(R.id.btn_realCheck);
        tv_code = (TextView) findViewById(R.id.tv_code);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        // 뒤로 가기 버튼
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 버튼 먼저 눌렀을 때 참여 코드 인증해달라고 Toast 문구 띄우기
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "참여 코드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 코드 입력 버튼 눌렀을 때 참여코드 뜨게 하기
        et_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tv3.setVisibility(View.INVISIBLE);
                    tv_code.setVisibility(View.VISIBLE);
                    btn_check.setClickable(false);
                    btn_check.setVisibility(View.INVISIBLE);
                    btn_realCheck.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // 한글자 이상 입력시 참여코드 뜨게 하기
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_realCheck.setBackgroundResource(R.drawable.button_round_black1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 참여코드 인증버튼 클릭
        btn_realCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // POST 통신 후 값이 맞다면 인증화면으로 넘어가고, 아니면 빨간색으로 해주는 처리 해줘야된다.
                // 지금은 다음 페이지 구현을 위해 임의로 값 지정하여 넘어간다 !!

                checkCode = et_code.getText().toString();
                //Toast.makeText(getApplicationContext(), checkCode, Toast.LENGTH_SHORT).show();
                if(checkCode.equals("hyunseok")) {
                    Intent intent = new Intent(getApplicationContext(), InviteSuccessTeamActivity.class);
                    startActivity(intent);
                }

                // 인증 결과가 틀린 경우
                else {
                    tv_code.setTextColor(ContextCompat.getColorStateList(InviteTeamActivity.this, R.color.secondary_grey_black_7));
                    et_code.setBackgroundResource(R.drawable.edittext_checkcode_result_false);
                    tv4.setVisibility(View.VISIBLE);
                    et_code.setText(null);
                    btn_realCheck.setBackgroundResource(R.drawable.button_round_black12);
                }
            }
        });
    }
}