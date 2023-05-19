package com.example.moing.board;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moing.R;

import java.util.ArrayList;
import java.util.List;

public class VoteInfoAdapterFirst extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VoteInfo.VoteChoice> voteChoiceList;
    private List<VoteInfo.VoteChoice> voteSelected;
    private Context context;
    private OnItemClickListener clickListener = null;

    // 리사이클러뷰 안의 리사이클러뷰 관련
    // 두번째 어댑터와 연결
    VoteInfoAdapterSecond second_adapter;

    // 클릭 리스너 설정
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public VoteInfoAdapterFirst(List<VoteInfo.VoteChoice> voteChoiceList, List<VoteInfo.VoteChoice> voteSelected, Context context) {
        this.voteChoiceList = voteChoiceList;
        this.voteSelected = voteSelected;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return voteChoiceList.size();
    }

    public List<VoteInfo.VoteChoice> getSelectedItems() {
        return voteSelected;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_vote_first, parent, false);
        return new VoteInfoAdapterFirst.VoteInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VoteInfo.VoteChoice voteChoice = voteChoiceList.get(position);
        VoteInfoViewHolder vH = (VoteInfoViewHolder) holder;
        vH.tv_text.setText(voteChoice.getContent());

        // Checkbox 상태 변경
        vH.checkBox.setChecked(voteChoice.isChecked());
        if (voteChoice.isChecked()) {
            vH.checkBox.setBackgroundResource(R.drawable.board_checkbox_yes);
            vH.checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#F6F6F6")));
        } else {
            vH.checkBox.setBackgroundResource(R.drawable.board_checkbox_no);
            // 선택되지 않은 상태의 배경색 제거
            vH.checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#959698")));
        }

        /** 두번째 리싸이클러뷰 넣는 부분 **/
        GridLayoutManager llm2 = new GridLayoutManager(context, 4);
        llm2.setSmoothScrollbarEnabled(true);
        llm2.setAutoMeasureEnabled(true);

        // 안읽은 사람 리스트(GridLayout) 간격 설정
        int grid_spacing = (int) context.getResources().getDimension(R.dimen.grid_spacing);
        VoteNoReadGridSpacing voteNoReadGridSpacing = new VoteNoReadGridSpacing(4, grid_spacing);
        vH.recyclerView.addItemDecoration(voteNoReadGridSpacing);
        vH.recyclerView.setLayoutManager(llm2);

        // 예정 : Retrofit 연동시 해당 코드가 맞음!!
        // 예정 : List<String> userList = voteChoice.getVoteUserNickName();

        /** 아래 코드는 테스트 코드 **/
        List<String> userList = new ArrayList<>();
        userList.add("손현석");
        userList.add("곽승엽");
        /** 테스트 코드 부분 종료 **/

        second_adapter = new VoteInfoAdapterSecond(voteChoiceList, userList);
        vH.recyclerView.setAdapter(second_adapter);


        // 예정 : 투표한 사람 버튼 VISIBLE, INVISIBLE 여부


        /** 예정 : 사람 수 받아와서 텍스트에 설정하기!! **/
        //vH.count.setText(voteChoice.getNum());

        /** 예정 : 버튼 클릭시 카드뷰가 보여지게 구현! **/
        vH.btn_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int visibility = (vH.recyclerView.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition((ViewGroup) vH.itemView, new AutoTransition());
                vH.recyclerView.setVisibility(visibility);
                // 보이는 상태라면
                if(visibility == View.VISIBLE)
                    vH.btn_people.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                else
                    vH.btn_people.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
            }
        });
    }

    /** ViewHolder 작성 **/
    class VoteInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox checkBox;
        TextView tv_text, count;
        Button btn_people;
        RecyclerView recyclerView;

        public VoteInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.btn_check);
            tv_text = itemView.findViewById(R.id.tv_item_date);
            count = itemView.findViewById(R.id.tv_voteCount);
            btn_people = itemView.findViewById(R.id.btn_voteCheck);
            recyclerView = itemView.findViewById(R.id.recycle_second);

            itemView.setOnClickListener(this);
            checkBox.setOnClickListener(this);
        }

        /** 리싸이클러뷰 아이템 및 Checkbox 클릭을 동일하게 설정 **/
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && clickListener != null) {

                //CheckBox 상태 변경
                boolean isChecked = !voteChoiceList.get(pos).isChecked();
                voteChoiceList.get(pos).setChecked(isChecked);
                checkBox.setChecked(isChecked);

                if (isChecked) {
                    if (!voteSelected.contains(voteChoiceList.get(pos))) {
                        voteSelected.add(voteChoiceList.get(pos));
                    }
                    checkBox.setBackgroundResource(R.drawable.board_checkbox_yes);
                    checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#F6F6F6")));
                } else {
                    voteSelected.remove(voteChoiceList.get(pos));
                    checkBox.setBackgroundResource(R.drawable.board_checkbox_no);
                    // 선택되지 않은 상태의 배경색 제거
                    checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#959698")));
                }
                clickListener.onItemClick(pos);
            }
        }
    }
}
