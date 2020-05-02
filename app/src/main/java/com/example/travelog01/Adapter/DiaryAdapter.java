package com.example.travelog01.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog01.Adapter.ViewHolder.DiaryViewHolder;
import com.example.travelog01.Model.DiaryBean;
import com.example.travelog01.R;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryViewHolder> {
    private ArrayList<DiaryBean> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
/*
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }*/

    public DiaryAdapter(ArrayList<DiaryBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    public DiaryAdapter(Context context, OnItemClickListener onItemClickListener) {
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(ArrayList<DiaryBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_diary_item, parent, false), mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mList == null){
            return 0;
        }else{
            return mList.size();
        }

    }

    public interface OnItemClickListener{
        void onItemClick(DiaryBean diary);
        //void onItemClick(View v, int pos);
    }
}
