package com.passerbywhu.materialstudy.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hzwuwenchao on 2015/11/25.
 */
public abstract class DragSortRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends HeaderFooterRecyclerViewAdapter<T, VH> implements DragSortRecyclerView.OnItemTouch {
    public DragSortRecyclerViewAdapter(Context context) {
        super(context);
    }
}
