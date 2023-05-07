package com.example.moing.team;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moing.R;

public class MakeTeamActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team3);


        // 구현 예정 - Intent 를 통한 이전 값 전달 받기

        // 뒤로 가기
        ImageButton btnBack = findViewById(R.id.make_team3_btn_back);
        btnBack.setOnClickListener(view -> finish());

        // 소모임 소개
        EditText etIntroduce = findViewById(R.id.make_team3_et_introduce);
        TextView tvIntroduce = findViewById(R.id.make_team3_tv_introduce);
        TextView tvIntroduceCnt = findViewById(R.id.make_team3_tv_introduce_count);

        // 소모임장 각오
        EditText etResolution = findViewById(R.id.make_team3_et_resolution);
        TextView tvResolution = findViewById(R.id.make_team3_tv_resolution);
        TextView tvResolutionCnt = findViewById(R.id.make_team3_tv_resolution_count);

        // onFocusChangeListener, TextWatcher 등록
        setFocus(etIntroduce,tvIntroduce);
        setTextWatcher(etIntroduce,tvIntroduceCnt,300);
        setFocus(etResolution,tvResolution);
        setTextWatcher(etResolution,tvResolutionCnt,100);

        // 구현 예정 - S3를 이용한 사진 업로드
        // 구현 예정 - 생성하기 버튼 및 ProgressBar


    }

    /** EditText focus 시 제목 색상 변경 **/
    private void setFocus(EditText editText, TextView textView){
        editText.setOnFocusChangeListener((view, isFocused) ->
                textView.setTextColor(isFocused?getResources().getColor(R.color.main_dark_200):getResources().getColor(R.color.secondary_grey_black_7)));
    }

    /** EditText 입력 시 입력된 글자 수 변경 **/
    private void setTextWatcher(EditText editText, TextView countTextView, int maxLength){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String result = "(" + editable.length() + "/" + maxLength + ")";
                countTextView.setText(result);
            }
        });
    }
}