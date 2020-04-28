package com.example.travelog01.Adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog01.Adapter.DiaryAdapter;
import com.example.travelog01.Model.DiaryBean;
import com.example.travelog01.R;

public class DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//public class DiaryViewHolder extends RecyclerView.ViewHolder{
    private TextView mTitle, mContent, mLocDate;
    private DiaryAdapter.OnItemClickListener mOnItemClickListener;
    private DiaryBean mDiary;

    public DiaryViewHolder(View itemView, DiaryAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mOnItemClickListener = onItemClickListener;
        mTitle = (TextView) itemView.findViewById(R.id.diary_item_tv_title);
        mContent = (TextView) itemView.findViewById(R.id.diary_item_tv_content);
        mLocDate = (TextView) itemView.findViewById(R.id.diary_item_tv_date);
        //itemView.setOnClickListener(this);
    }

    public void setData(DiaryBean diary) {
        mDiary = diary;
        mLocDate.setText(diary.getDate());
        mTitle.setText(diary.getTitle());
        mContent.setText((diary.getContent().length() < 75) ? diary.getContent():diary.getContent().substring(0,74) + "...");
    }

    @Override
    public void onClick(View view) {
        mOnItemClickListener.onItemClick(mDiary);
    }
}
