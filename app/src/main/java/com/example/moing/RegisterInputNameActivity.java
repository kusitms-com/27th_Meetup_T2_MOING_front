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

public class RegisterInputNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_input_name);

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

                nickNameTF.setVisibility(View.VISIBLE);
                nicknameBtn.setVisibility(View.INVISIBLE);
                nextLight.setVisibility(View.VISIBLE);
                nicknameTv.setVisibility(View.INVISIBLE);
                smallTv.setVisibility(View.VISIBLE);
                tv1.setTextColor(Color.parseColor("#FDFDFD"));
                tv2.setTextColor(Color.parseColor("#FDFDFD"));
            }
        });

        nextLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterInputNameActivity.this, RegisterInputAddressActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}