package com.example.moing.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moing.R;
import com.example.moing.Response.RegisterNameResponse;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInputNameActivity extends AppCompatActivity {

    private RetrofitAPI retrofitAPI;
    private String access;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_input_name);

        ImageView arrowLeft = (ImageView) findViewById(R.id.arrowLeft);
        ImageView nextLight = (ImageView) findViewById(R.id.nextLight);
        ImageView nextDark = (ImageView) findViewById(R.id.nextDark);
        ImageView nicknameBtn = (ImageView) findViewById(R.id.nicknameBtn);
        ImageView xIcon = (ImageView) findViewById(R.id.xIcon);
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView nicknameTv = (TextView) findViewById(R.id.nicknameTv);
        TextView nickNameTF = (TextView) findViewById(R.id.nickNameTF);
        TextView smallTv = (TextView) findViewById(R.id.smallTv); // 반가워요 ~
        TextView tv1 = (TextView) findViewById(R.id.tv1); // MOING에서 ~
        TextView tv2 = (TextView) findViewById(R.id.tv2); // 닉네임을 ~

        //retrofit 이용
        retrofitAPI = RetrofitClient.getApiService();

        // 이전 레이아웃에서 전달된 액세스 토큰 값을 받아옴
        Intent intent = getIntent();
        if (intent.hasExtra("access_token")) {
            String access = intent.getStringExtra("access_token");
            System.out.println("토큰 : " + access + "을 전달받았습니다.");
        } else {
            System.out.println("액세스 토큰 값이 전달되지 않았습니다.");
        }
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        xIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText.setText(null);
                nextDark.setVisibility(View.VISIBLE);
                nextLight.setVisibility(View.INVISIBLE);
                nicknameBtn.setVisibility(View.INVISIBLE);
                nicknameTv.setVisibility(View.INVISIBLE);

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    nicknameTv.setVisibility(View.VISIBLE);
                    nextDark.setVisibility(View.INVISIBLE);
                    nicknameBtn.setVisibility(View.VISIBLE);
                    xIcon.setVisibility(View.VISIBLE);
                    smallTv.setVisibility(View.INVISIBLE);
                    tv1.setTextColor(Color.parseColor("#66676A"));
                    tv2.setTextColor(Color.parseColor("#66676A"));

                } else {
                    smallTv.setVisibility(View.VISIBLE);
                    tv1.setTextColor(Color.parseColor("#FDFDFD"));
                    tv2.setTextColor(Color.parseColor("#FDFDFD"));

                }
            }
        });

        nicknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nickname = editText.getText().toString();

                Call<RegisterNameResponse> call = retrofitAPI.NameAvailable(nickname);
                call.enqueue(new Callback<RegisterNameResponse>() {
                    @Override
                    public void onResponse(Call<RegisterNameResponse> call, Response<RegisterNameResponse> response) {
                        if (response.isSuccessful()) {
                            RegisterNameResponse registerNameResponse = response.body();
                            String result = registerNameResponse.getData().getResult();

                            if (result.equals("이미 존재하는 닉네임입니다")) {
                                // 이미 존재하는 닉네임 처리 로직
                                nickNameTF.setText("이미 존재하는 닉네임입니다.");
                                nickNameTF.setVisibility(View.VISIBLE);

                            } else if (result.equals("가능한 닉네임입니다")) {

                                // 가능한 닉네임 처리 로직
                                nickNameTF.setVisibility(View.VISIBLE);
                                nextDark.setVisibility(View.INVISIBLE);
                                nextLight.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // 응답 실패 처리 로직
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterNameResponse> call, Throwable t) {
                        // 네트워크 오류 처리 로직
                    }
                });

                nicknameBtn.setVisibility(View.INVISIBLE);
                nextLight.setVisibility(View.VISIBLE);
                nicknameTv.setVisibility(View.INVISIBLE);
                smallTv.setVisibility(View.VISIBLE);
                tv1.setTextColor(Color.parseColor("#FDFDFD"));
                tv2.setTextColor(Color.parseColor("#FDFDFD"));
            }
        });

        // click next in
        nextLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent intent = new Intent(RegisterInputNameActivity.this, RegisterInputAddressActivity.class);
                startActivity(intent);
                */

                if (access != null) { // 액세스 토큰 값이 유효할 경우에만 Intent...
                    Intent intent = new Intent(RegisterInputNameActivity.this, RegisterInputAddressActivity.class);

                    intent.putExtra("access_token", access);
                    System.out.println("토큰 : " + access + "을 전달하겠습니다.");

                    String nickname = editText.getText().toString();
                    intent.putExtra("nickname", nickname);
                    System.out.println("닉네임 : " + nickname + "을 전달하겠습니다.");
                    startActivity(intent);
                } else {
                    System.out.println("액세스 토큰 값이 유효하지 않습니다.");
                }

            }
        });

    }

}