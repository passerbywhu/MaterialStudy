package com.passerbywhu.materialstudy.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzwuwenchao on 2015/11/23.
 */
public class HeaderFooterRecyclerView extends BaseRecyclerView {
    private View mHeaderView;
    private View mFooterView;

    private HeaderFooterRecyclerViewAdapter mAdapter;

    public HeaderFooterRecyclerView(Context context) {
        this(context, null);
    }

    public HeaderFooterRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderFooterRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static HeaderFooterRecyclerView getNewInstance(Context context) {
        return new HeaderFooterRecyclerView(context);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof HeaderFooterRecyclerViewAdapter)) {
            throw new IllegalArgumentException("Only Support HeaderFooterRecyclerViewAdapter, Because I'm too lazy to introduce a wrapper apapter");
        }
        super.setAdapter(adapter);
        mAdapter = (HeaderFooterRecyclerViewAdapter) adapter;
        if (mHeaderView != null) {
            mAdapter.addHeaderView(mHeaderView);
        }
        if (mFooterView != null) {
            mAdapter.addFooterView(mFooterView);
        }
    }

    public void setHeaderView(View view) {
        if (mAdapter != null) {
            throw new IllegalStateException("You should call this method before setAdapter");
        }
        this.mHeaderView = view;
    }

    public boolean isHeaderViewAdded() {
        if (mHeaderView != null) {
            return true;
        }
        return false;
    }

    public void setFooterView(View view) {
        if (mAdapter != null) {
            throw new IllegalStateException("You should call this method before setAdapter");
        }
        this.mFooterView = view;
    }

    public boolean isFooterViewAdded() {
        if (mFooterView != null) {
            return true;
        }
        return false;
    }
}
