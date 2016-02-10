package com.passerbywhu.materialstudy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.LayoutParams;

import com.passerbywhu.materialstudy.recyclerview.DragSortRecyclerViewAdapter;

public class ActivityAdapter extends DragSortRecyclerViewAdapter<Class, ActivityAdapter.ViewHolder> {
    public ActivityAdapter(Context context) {
        super(context);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        return new ViewHolder(tv);
    }

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(getItem(position).getSimpleName());
    }

    @Override
    public void onMove(RecyclerView.ViewHolder from, RecyclerView.ViewHolder to) {
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder target) {
    }
}
