package com.example.moing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.example.moing.NoticeItem;
import com.example.moing.R;

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder> {

    private Intent intent;
    private Context context;
    private ArrayList<NoticeItem> mNoticeList = new ArrayList<NoticeItem>();

    @NonNull
    // @NotNull
    @Override
    public NoticeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull /*@NotNull*/ ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_notice, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull /*@NotNull*/ NoticeRecyclerAdapter.ViewHolder holder, int position) {
        NoticeItem noticeItem = mNoticeList.get(position);
        holder.onBind(noticeItem);

        /* holder.buttonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), DonationStoreDetailActivity.class);
                v.getContext().startActivity(intent);
            }
        }); */

    }

    public void setNoticeList(Context context, ArrayList<NoticeItem> list){
        this.context = context;
        this.mNoticeList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView notice_tv_user_name;
        TextView notice_tv_title;
        TextView notice_tv_content;
        TextView notice_tv_message_count;
        ImageView notice_iv_user_image;
        ImageView notice_iv_crown;
        ImageView notice_iv_dot;
        ImageView notice_iv_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notice_tv_user_name = (TextView) itemView.findViewById(R.id.notice_tv_user_name);
            notice_tv_title = (TextView) itemView.findViewById(R.id.notice_tv_title);
            notice_tv_content = (TextView) itemView.findViewById(R.id.notice_tv_content);
            notice_tv_message_count = (TextView) itemView.findViewById(R.id.notice_tv_message_count);
            notice_iv_user_image = (ImageView) itemView.findViewById(R.id.notice_iv_user_image);
            notice_iv_crown = (ImageView) itemView.findViewById(R.id.notice_iv_crown);
            notice_iv_dot = (ImageView) itemView.findViewById(R.id.notice_iv_dot);
            notice_iv_message = (ImageView) itemView.findViewById(R.id.notice_iv_message);
        }

        void onBind(NoticeItem item){

            notice_tv_user_name.setText(item.getNotice_tv_user_name());
            notice_tv_title.setText(item.getNotice_tv_title());
            notice_tv_content.setText(item.getNotice_tv_content());
            notice_tv_message_count.setText(item.getNotice_tv_message_count());
            Glide.with(context).load(item.getNotice_iv_user_image()).into(notice_iv_user_image);
            Glide.with(context).load(item.getNotice_iv_crown()).into(notice_iv_crown);
            Glide.with(context).load(item.getNotice_iv_dot()).into(notice_iv_dot);
            Glide.with(context).load(item.getNotice_iv_message()).into(notice_iv_message);

        }
    }

}