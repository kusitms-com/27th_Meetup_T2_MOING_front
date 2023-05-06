package com.example.moing;

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

public class RegisterInputAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_input_address);

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

                Intent intent = new Intent(RegisterInputAddressActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}