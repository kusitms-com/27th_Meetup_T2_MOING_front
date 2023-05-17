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

import com.example.moing.MainActivity;
import com.example.moing.R;
import com.example.moing.Request.RegisterAddressRequest;
import com.example.moing.Response.RegisterAddressResponse;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInputAddressActivity extends AppCompatActivity {

    private RetrofitAPI retrofitAPI;
    private String AccessToken;
    private String NickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_input_address);

        ImageView arrowLeft = (ImageView) findViewById(R.id.arrowLeft);
        ImageView startLight = (ImageView) findViewById(R.id.startLight);
        ImageView startDark = (ImageView) findViewById(R.id.startDark);
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView regionInforTv = (TextView) findViewById(R.id.regionInforTv); // 00시 ~
        TextView regionInputTv = (TextView) findViewById(R.id.regionInputTv); // '서울시'
        TextView regionTv = (TextView) findViewById(R.id.regionTv); // 지역구 ~
        ImageView xIcon = (ImageView) findViewById(R.id.xIcon);
        TextView smallTv = (TextView) findViewById(R.id.smallTv); // 거의 ~
        TextView tv1 = (TextView) findViewById(R.id.tv1); // 주로 ~
        TextView tv2 = (TextView) findViewById(R.id.tv2); // 지역구 ~

        //retrofit 이용
        retrofitAPI = RetrofitClient.getApiService();

        // 이전 레이아웃에서 전달된 액세스 토큰 값을 받아옴
        Intent intent = getIntent();
        if (intent.hasExtra("access_token")) {
            String accessToken = intent.getStringExtra("access_token");
            System.out.println("토큰 : " + accessToken + "을 전달 받았습니다.");
        } else {
            System.out.println("액세스 토큰 값이 전달되지 않았습니다.");
        }

        if (intent.hasExtra("nickname")) {
            NickName = intent.getStringExtra("nickname");
            System.out.println("닉네임 : " + NickName + "을 전달 받았습니다.");
        } else {
            System.out.println("닉네임이 전달되지 않았습니다.");
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
                startDark.setVisibility(View.VISIBLE);
                startLight.setVisibility(View.INVISIBLE);
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
                    regionTv.setVisibility(View.VISIBLE);
                    regionInforTv.setVisibility(View.INVISIBLE);
                    regionInputTv.setVisibility(View.VISIBLE);
                    startDark.setVisibility(View.INVISIBLE);
                    startLight.setVisibility(View.VISIBLE);
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

        startLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 주소 정보를 가져옴
                String address = editText.getText().toString();

                // RegisterAddressRequest 객체를 생성하고 주소 정보를 담음
                RegisterAddressRequest request = new RegisterAddressRequest(AccessToken, address, NickName, "temp");
                request.setAddress(address);

                // API 요청을 보내고 응답을 처리하는 콜백 메서드를 정의함
                retrofitAPI.AdditionalInfo(request).enqueue(new Callback<RegisterAddressResponse>() {
                    @Override
                    public void onResponse(Call<RegisterAddressResponse> call, Response<RegisterAddressResponse> response) {
                        if (response.isSuccessful()) {
                            RegisterAddressResponse registerAddressResponse = response.body();

                            // API 요청이 성공적으로 처리되었을 때

                            if (AccessToken != null) { // 액세스 토큰 값이 유효할 경우에만 Intent...
                                Intent intent = new Intent(RegisterInputAddressActivity.this, MainActivity.class);
                                intent.putExtra("access_token", AccessToken);
                                System.out.println("토큰 : " + AccessToken + "을 전달하겠습니다.");
                                startActivity(intent);
                            } else {
                                System.out.println("액세스 토큰 값이 유효하지 않습니다.");
                            }

                            Intent intent = new Intent(RegisterInputAddressActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // API 요청이 실패했을 때
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterAddressResponse> call, Throwable t) {
                        // API 요청이 실패했을 때 수행할...
                    }
                });
            }
        });

    }
}