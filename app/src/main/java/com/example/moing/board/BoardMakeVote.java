package com.example.moing.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class BoardMakeVote extends AppCompatActivity {

    RecyclerView recyclerView;
    VoteAdapater voteAdapater;
    Button btn_close;
    EditText title, content;
    TextView titleCount, contentCount;
    private final int MAXTITLE = 15;
    private final int MAXCONTENT = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_make_vote);

        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(closeClickListener);
        title = (EditText) findViewById(R.id.et_title);
        titleCount = (TextView) findViewById(R.id.tv_titleCount);
        content = (EditText) findViewById(R.id.et_content);
        contentCount = (TextView) findViewById(R.id.tv_contentCount);

        // TextWatcher 등록
        setTextWatcher(title, titleCount, 15);
        setTextWatcher(content, contentCount, 300);


        recyclerView = findViewById(R.id.recycle_vote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // 리싸이클러뷰 스크롤 방지
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);

        // test data (삭제 예정)
        List<Vote> voteList = new ArrayList<>();
//        boardList.add(new Board("내용1입니다.","제목 1입니다.",1));
//        boardList.add(new Board("내용2입니다.","제목 2입니다.",2));
//        boardList.add(new Board("내용3입니다.","제목 3입니다.",3));

        // 리싸이클러뷰 어댑터 설정
        // adapter 설정
        voteAdapater = new VoteAdapater(voteList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voteAdapater);
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
                //checkInputs();
            }
        });
    }
}