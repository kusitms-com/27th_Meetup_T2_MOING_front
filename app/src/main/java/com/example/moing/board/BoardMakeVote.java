package com.example.moing.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class BoardMakeVote extends AppCompatActivity implements MakeVoteAdapater.OnEditTextChangedListener{

    RecyclerView recyclerView;
    Button btn_close, btn_erase_content, addContent, deleteContent, upload;
    CheckBox  anony, multi;
    EditText title, content;
    TextView titleCount, contentCount, tv_anony, tv_multi;
    List<MakeVote> makeVoteList = new ArrayList<>();
    List<MakeVote> deleteMakeVote = new ArrayList<>();
    private int addContentCount;
    private MakeVoteAdapater makeVoteAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_make_vote);

        // 투표 만들기 취소 버튼 & 클릭 리스너
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(closeClickListener);
        // 제목
        title = (EditText) findViewById(R.id.et_title);
        // 제목 글자 수
        titleCount = (TextView) findViewById(R.id.tv_titleCount);
        // 내용
        content = (EditText) findViewById(R.id.et_content);
        // 내용 글자 수
        contentCount = (TextView) findViewById(R.id.tv_contentCount);

        // 예정 : 항목지우기 클릭 리스너
        btn_erase_content = (Button) findViewById(R.id.btn_erase);
        btn_erase_content.setOnClickListener(contentEraseClickListener);
        btn_erase_content.setClickable(false);


        // 투표 항목 추가하기 & 리스너
        addContent = (Button) findViewById(R.id.imgbtn_add);
        addContent.setOnClickListener(addContentClickListener);
        addContentCount=0;

        // 선택한 항목 지우기
        deleteContent = (Button) findViewById(R.id.imgbtn_erase);
        deleteContent.setOnClickListener(deleteContentClickListener);

        // TextWatcher 등록
        setTextWatcher(title, titleCount, 15);
        setTextWatcher(content, contentCount, 300);

        recyclerView = findViewById(R.id.recycle_vote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // 리싸이클러뷰 스크롤 방지
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);

        // 리싸이클러뷰 어댑터 설정
        // adapter 설정
        makeVoteAdapater = new MakeVoteAdapater(makeVoteList, this, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(makeVoteAdapater);
        recyclerView.setHasFixedSize(true);

        // 익명, 복수 투표
        anony = (CheckBox) findViewById(R.id.btn_anony);
        multi = (CheckBox) findViewById(R.id.btn_multi);
        tv_anony = (TextView) findViewById(R.id.tv_anony);
        tv_multi = (TextView) findViewById(R.id.tv_multi);
        anony.setOnClickListener(anonyClickListener);
        multi.setOnClickListener(multiClickListener);

        // 업로드 버튼
        upload = (Button) findViewById(R.id.btn_upload);
        upload.setOnClickListener(uploadClickListener);
        upload.setClickable(false);
    }

    // 취소 버튼
    View.OnClickListener closeClickListener = v -> {
        finish();
    };

    /** EditText 입력 시 입력된 글자 수 변경 **/
    private void setTextWatcher(EditText editText, TextView countTextView, int maxLength) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String result = "(" + editable.length() + "/" + maxLength + ")";
                countTextView.setText(result);
                checkInputs();
            }
        });
    }

    /** 항목 추가하기 버튼 클릭 **/
    View.OnClickListener addContentClickListener = v -> {
        Log.d("CONTENTCLICKADD", String.valueOf(makeVoteList.size()));

        MakeVote makeVote = new MakeVote();
        makeVoteList.add(makeVote);
        addContentCount = makeVoteList.size();
        addContent.setText("항목 추가하기("+addContentCount+"/10)");
        makeVoteAdapater.notifyDataSetChanged();

        Log.d("CONTENTCLICKADD", String.valueOf(makeVoteList.size()));

        if(makeVoteList.size() >= 2) {
            btn_erase_content.setClickable(true);
            btn_erase_content.setTextColor(Color.parseColor("#F43A6F"));
        }

    };

    /** 선택한 항목 지우기 버튼 클릭 **/
    View.OnClickListener deleteContentClickListener = v -> {
        // 선택 항목 삭제
        deleteMakeVote = makeVoteAdapater.getSelectedItems();
        makeVoteList.removeAll(deleteMakeVote);
        makeVoteAdapater.notifyDataSetChanged();

        // 다시 항목 추가하기로 이동
        boolean isVisible = makeVoteAdapater.isButtonVisible();
        makeVoteAdapater.setButtonVisible(!isVisible);
        deleteContent.setVisibility(View.GONE);
        addContent.setVisibility(View.VISIBLE);
        addContentCount = makeVoteList.size();
        addContent.setText("항목 추가하기("+addContentCount+"/10)");
        btn_erase_content.setText("항목 지우기");

        if (makeVoteList.size() < 2) {
            btn_erase_content.setClickable(false);
            btn_erase_content.setTextColor(Color.parseColor("#333232"));
        }
        else {
            btn_erase_content.setClickable(true);
            btn_erase_content.setTextColor(Color.parseColor("#F43A6F"));
        }
    };

    /** 항목 지우기 버튼 클릭 **/
    View.OnClickListener contentEraseClickListener = v -> {
        boolean isVisible = makeVoteAdapater.isButtonVisible();
        makeVoteAdapater.setButtonVisible(!isVisible);

        // 선택한 항목 지우기로 이동
        if(!isVisible) {
            btn_erase_content.setText("돌아가기");
            btn_erase_content.setTextColor(Color.parseColor("#F6F6F6"));
            addContent.setVisibility(View.GONE);
            deleteContent.setVisibility(View.VISIBLE);
            deleteContent.setClickable(true);
            deleteContent.setTextColor(Color.parseColor("#F43A6F"));
        }
        // 항목 추가하기로 이동
        else {
            btn_erase_content.setTextColor(Color.parseColor("#F43A6F"));
            btn_erase_content.setText("항목 지우기");
            addContent.setVisibility(View.VISIBLE);
            deleteContent.setVisibility(View.GONE);
            addContentCount = makeVoteList.size();
            addContent.setText("항목 추가하기("+addContentCount+"/10)");
            //addContent.setTextColor(Color.parseColor("#F1F1F1"));
        }
    };

    /** 익명 투표 **/
    View.OnClickListener anonyClickListener = v -> {
        if(anony.isChecked()) {
            anony.setBackgroundResource(R.drawable.board_checkbox_yes);
            tv_anony.setTextColor(Color.parseColor("#F6F6F6"));
        } else {
            anony.setBackgroundResource(R.drawable.board_checkbox_no);
            tv_anony.setTextColor(Color.parseColor("#959698"));
        }
    };

    /** 복수 투표 **/
    View.OnClickListener multiClickListener = v -> {
        if(multi.isChecked()) {
            multi.setBackgroundResource(R.drawable.board_checkbox_yes);
            tv_multi.setTextColor(Color.parseColor("#F6F6F6"));
        }
        else {
            multi.setBackgroundResource(R.drawable.board_checkbox_no);
            tv_multi.setTextColor(Color.parseColor("#959698"));
        }
    };

    /** 업로드하기 버튼 **/
    View.OnClickListener uploadClickListener = v -> {
//        Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);

//        String s = "";
//        for (Vote vote : voteAdapater.getVoteList())
//            s += vote.getVoteContent();
//
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

        /** POST 요청 수행해야 한다. **/
    };

    // 각 EditText들의 null값 여부 확인
    public boolean isEditTextFilled() {
        if (title.getText().toString().isEmpty() || content.getText().toString().isEmpty()) {
            return false;
        }
        if (makeVoteAdapater.getVoteList().size() <= 0)
            return false;

        for (MakeVote makeVote : makeVoteList) {
            if (makeVote.getVoteContent() == null || makeVote.getVoteContent().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    // 입력값 확인
    public void checkInputs() {
        if (isEditTextFilled()) {
            upload.setClickable(true);
            upload.setTextColor(Color.parseColor("#202020"));
            upload.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        // upload버튼 비활성
        else {
            upload.setClickable(false);
            upload.setTextColor(ContextCompat.getColorStateList(BoardMakeVote.this, R.color.secondary_grey_black_10));
            upload.setBackgroundColor(Color.parseColor("#202020"));
        }
    }

    // EditText에 값이 존재하게 될 때
    @Override
    public void onEditTextChanged() {
        boolean isEditTextFilled = makeVoteAdapater.areEditTextsFilled();
        // upload 버튼 활성화 비활성화 여부
        if(isEditTextFilled) {
            checkInputs();
        }
        else {
            upload.setClickable(false);
            upload.setTextColor(ContextCompat.getColorStateList(BoardMakeVote.this, R.color.secondary_grey_black_10));
            upload.setBackgroundColor(Color.parseColor("#202020"));
        }
    }
}