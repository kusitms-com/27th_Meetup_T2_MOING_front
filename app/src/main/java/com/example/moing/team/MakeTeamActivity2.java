package com.example.moing.team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moing.R;

import java.util.Calendar;

public class MakeTeamActivity2 extends AppCompatActivity {
    ImageButton btn_back;
    EditText et_name, et_num;
    Button btn_next,btn_start, btn_predict;
    TextView tv_nameCnt, tv_name, tv_num, tv_start, tv_warn, tv_predict;
    TextView tv_month1, tv_month2, tv_month3, tv_month4, tv_month5;
    CardView cardView;
    LinearLayout hiddenView;
    DatePickerDialog datePickerDialog;
    ImageView iv_pg;
    String dream, preText, name, cnt, data, predictDate;
    int teamCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team2);

        Intent intent = getIntent();
        // 목표값 전달
        dream = intent.getStringExtra("major");

        // 뒤로 가기
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(view -> finish());

        // 소모임 이름
        et_name = (EditText) findViewById(R.id.et_name);
        tv_nameCnt = (TextView) findViewById(R.id.tv_nameCount);
        tv_name = (TextView) findViewById(R.id.tv_name);

        // 소모임 구성원 수
        et_num = (EditText) findViewById(R.id.et_number);
        tv_num = (TextView) findViewById(R.id.tv_member);
        tv_warn = (TextView) findViewById(R.id.tv_warn1);

        // 소모임 시작일
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_start = (TextView) findViewById(R.id.tv_startDay);

        // 소모임 예상활동 기간
        btn_predict = (Button) findViewById(R.id.btn_predict);
        cardView = findViewById(R.id.base_cardView);
        hiddenView = findViewById(R.id.hidden_view);
        tv_month1 = findViewById(R.id.tv_month1);
        tv_month2 = findViewById(R.id.tv_month2);
        tv_month3 = findViewById(R.id.tv_month3);
        tv_month4 = findViewById(R.id.tv_month4);
        tv_month5 = findViewById(R.id.tv_month5);
        tv_predict = findViewById(R.id.tv_predict);

        // 다음으로 버튼, 프로그레스 바
        btn_next = (Button) findViewById(R.id.btn_next2);
        iv_pg = (ImageView) findViewById(R.id.iv_pg1);

        // onFocusChangeListener 등록
        // 소모임 이름
        setFocus(et_name, tv_name);
        // 소모임 구성원 수
        setFocus(et_num, tv_num);

        // 입력 완료 관련 메서드
        setKeyListener();

        // OnClickListener
        btn_start.setOnClickListener(onStartDateClickListener);
        btn_next.setOnClickListener(onCreateTeamNext);

        // 다음으로 버튼 비활성화
        btn_next.setClickable(false);

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
                name = s.toString();
                checkInputs();
            }
        });

        // 구성원 수 20명 넘었을 때 처리
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                preText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(preText)) return;

                if(et_num.isFocusable() && !s.toString().equals("")) {
                    try{
                        teamCnt = Integer.parseInt(et_num.getText().toString());
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                        return;
                    }

                    //20이 넘을 경우
                    if (teamCnt > 20) {
                        tv_warn.setVisibility(View.VISIBLE);
                        et_num.setBackgroundResource(R.drawable.edittext_checkcode_result_false);
                    }

                    else {
                        tv_warn.setVisibility(View.GONE);
                        et_num.setBackgroundResource(R.drawable.edittext_round_corner_rectangle);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                cnt = String.valueOf(teamCnt);
                checkInputs();
            }
        });

        // 버튼 Focus 설정
        btn_start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    tv_start.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.main_dark_200));
                }
                else
                    tv_start.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_7));
            }
        });

        // 예상활동 기간 선택
        btn_predict.setOnClickListener(v -> {
            tv_predict.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.main_dark_200));
            btn_predict.setSelected(true);

            // 보이는 상태라면
            if(hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                btn_predict.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
            }

            else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                btn_predict.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
                // 버튼 선택
                tv_month1.setOnClickListener(v1 -> {
                    changePredict(btn_predict, tv_month1);
                });

                tv_month2.setOnClickListener(v1 -> {
                    changePredict(btn_predict, tv_month2);
                });

                tv_month3.setOnClickListener(v1 -> {
                    changePredict(btn_predict, tv_month3);
                });

                tv_month4.setOnClickListener(v1 -> {
                    changePredict(btn_predict, tv_month4);
                });

                tv_month5.setOnClickListener(v1 -> {
                    changePredict(btn_predict, tv_month5);
                });
            }
        });

        // onCreate 끝
    }

    // 소모임 시작 날짜 설정
    View.OnClickListener onStartDateClickListener = view -> {
        Calendar calendar = Calendar.getInstance();
        int pYear = calendar.get(Calendar.YEAR); // 년
        int pMonth = calendar.get(Calendar.MONTH); // 월
        int pDay = calendar.get(Calendar.DAY_OF_MONTH); // 일

        datePickerDialog = new DatePickerDialog(MakeTeamActivity2.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        data = year + "/" + month + "/" + day;
                        Log.d("MAKETEAMACTIVITY2__", "시작일2 : " +  data);
                        btn_start.setText(data);
                        btn_start.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_3));
                    }
                }, pYear, pMonth, pDay);
        datePickerDialog.show();
        checkInputs();
    };

    // 다음으로 버튼 클릭
    View.OnClickListener onCreateTeamNext = view -> {
        Intent intent = new Intent(getApplicationContext(), MakeTeamActivity3.class);
        // 보낼 데이터들 아직 미구현.. 구현되면 바로 값 넣어서 보낼 예정!
        intent.putExtra("name", name); // 소모임 이름
        intent.putExtra("member", cnt); // 소모임 구성원 수
        intent.putExtra("startDate", data); // 소모임 시작일
        intent.putExtra("predict", predictDate); // 소모임 예상 활동 기간
        intent.putExtra("major", dream); // 소모임 목표
        startActivity(intent);
    };

    // EditText Focus 별 처리 메서드
    public void setFocus(EditText et, TextView tv) {
        et.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused) {
                tv.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.main_dark_200));
            }
            else {
                tv.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_7));
            }
        });
    }

    // 입력 완료에 따라 처리해주는 메서드
    public void setKeyListener() {
        View.OnKeyListener keyListener1 = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                    tv_name.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_7));
                   // tv_name.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_4));
                    return true;
                }
                return false;
            }
        };

        // 구성원 수 키코드 이벤트 처리
        View.OnKeyListener keyListener2 = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                   // tv_num.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_4));
                    btn_start.requestFocus();
                    //키보드 숨기기
                    hidekeyboard(et_num);
                }
                return false;
            }
        };
        et_name.setOnKeyListener(keyListener1);
        et_num.setOnKeyListener(keyListener2);
    }

    // 예상활동 기간 표시 메서드
    public void changePredict(Button b, TextView t) {
        predictDate = t.getText().toString();
        b.setText(predictDate);
        b.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_2));
        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
        hiddenView.setVisibility(View.GONE);
        b.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
        checkInputs();
        tv_predict.setTextColor(ContextCompat.getColorStateList(MakeTeamActivity2.this, R.color.secondary_grey_black_7));
        btn_predict.setSelected(false);
    }

    // 입력 완료 후 키보드 내려주는 메서드
    public void hidekeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    private void checkInputs() {
        if(et_name.length() >0 && et_num.length() >0 && (Integer.parseInt(et_num.getText().toString()) <= 20)
                && btn_start.length() >0 && btn_predict.length()>0) {
            btn_next.setBackgroundResource(R.drawable.button_round_black1);
            btn_next.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_12));
            btn_next.setClickable(true);
            iv_pg.setBackgroundResource(R.drawable.maketeam_progress4);
            // 프로그레스 바는 머지 한다음 수정할게요~!
        }
        else {
            btn_next.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_10));
            btn_next.setBackgroundResource(R.drawable.button_round_black12);
            btn_next.setClickable(false);
            iv_pg.setBackgroundResource(R.drawable.maketeam_progress3);
        }
    }
}