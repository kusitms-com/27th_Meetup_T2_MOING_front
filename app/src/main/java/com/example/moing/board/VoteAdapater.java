package com.example.moing.board;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class VoteAdapater extends RecyclerView.Adapter<VoteAdapater.VoteViewHolder> {
    private List<Vote> voteList;
    private List<Vote> selectedVote;

    private Context context;
    // 항목 지우기 체크박스 버튼 보일지 말지 여부
    private boolean isButtonVisible = false;

    // 생성자
    public VoteAdapater(List<Vote> voteList, Context context) {
        this.voteList = voteList;
        this.selectedVote = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_vote, parent, false);
        return new VoteAdapater.VoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
        // 항목 지우기 버튼 시 Checkbox 버튼 가시성 설정
        if(isButtonVisible)
            holder.checkbox.setVisibility(View.VISIBLE);
        else
            holder.checkbox.setVisibility(View.GONE);

        // 체크 박스 여부 설정
//        if (holder.checkbox.isChecked()) {
//            selectedVote.add(voteList.get(position));
//            holder.checkbox.setBackgroundResource(R.drawable.board_checkbox_yes);
//        }
//        else {
//            selectedVote.remove(voteList.get(position));
//            holder.checkbox.setBackgroundResource(R.drawable.board_checkbox_no);
//        }
        holder.checkbox.setChecked(voteList.get(position).isSelected());

        // EditText 관련 코드
        holder.et_content.setText(voteList.get(position).getVoteContent());
        // 변경점 수정
        holder.et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                holder.btn_close.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.et_content.getText().length() != 0)
                    holder.btn_close.setVisibility(View.VISIBLE);

                voteList.get(holder.getAdapterPosition()).setVoteContent(holder.et_content.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((BoardMakeVote) context).onVoteEditTextChanged();
            }
        });

        /** EditText 공란 처리 **/
        holder.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.et_content.setText(null);
                voteList.get(holder.getAdapterPosition()).setVoteContent(holder.et_content.getText().toString());
            }
        });

        /** 체크박스 변경 리스너 **/
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    voteList.get(pos).setSelected(isChecked);
                    if (isChecked) {
                        selectedVote.add(voteList.get(pos));
                        holder.checkbox.setBackgroundResource(R.drawable.board_checkbox_yes);
                    } else {
                        selectedVote.remove(voteList.get(pos));
                        holder.checkbox.setBackgroundResource(R.drawable.board_checkbox_no);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return voteList.size();
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public List<Vote> getSelectedItems() {
        return selectedVote;
    }

    public void setSelected(int position, boolean isSelected) {
        if (position >= 0 && position < voteList.size()) {
            Vote voteItem = voteList.get(position);
            voteItem.setSelected(isSelected);
            notifyItemChanged(position);

            if (isSelected) {
                if (!selectedVote.contains(voteItem)) {
                    selectedVote.add(voteItem);
                }
            } else {
                selectedVote.remove(voteItem);
            }
        }
    }

    class VoteViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        EditText et_content;
        Button btn_close;
        CheckBox checkbox;

        public VoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            et_content = mView.findViewById(R.id.et_vote_content);
            btn_close = mView.findViewById(R.id.btn_vote_erase);
            checkbox = mView.findViewById(R.id.btn_check);
        }
    }

    // 항목 지우기 checkbox 가시성 변경 메서드
    public void setButtonVisible(boolean isVisible) {
        isButtonVisible = isVisible;
        notifyDataSetChanged();
    }

    // 외부에서 버튼 가시성 상태를 얻기 위한 메서드 추가
    public boolean isButtonVisible() {
        return isButtonVisible;
    }

}
