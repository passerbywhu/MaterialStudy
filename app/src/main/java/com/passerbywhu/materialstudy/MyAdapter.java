package com.passerbywhu.materialstudy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hzwuwenchao on 2015/12/23.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context mContext;

    public MyAdapter(Context context) {
        this.mContext = context;
    }
    private String[] dataList;
    {
        dataList = new String[100];
        for (int i = 0; i < 100; i ++) {
            dataList[i] = i + "";
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(mContext);
        ViewHolder viewHolder = new ViewHolder(textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(position + "");
    }

    @Override
    public int getItemCount() {
        return dataList.length;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
