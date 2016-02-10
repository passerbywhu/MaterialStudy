package com.passerbywhu.materialstudy.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzwuwenchao on 2015/11/23.
 */
public class BaseRecyclerView extends RecyclerView {
    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }

    public static BaseRecyclerView getNewInstance(Context context) {
        return new BaseRecyclerView(context);
    }

    public boolean isLastItemFullVisible() {
        final Adapter adapter = getAdapter();
        if (null == adapter || adapter.getItemCount() == 0) {
            return true;
        }
        int lastVisiblePosition;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return true;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
            lastVisiblePosition = lm.findLastVisibleItemPosition();
            if (lastVisiblePosition == adapter.getItemCount() - 1) {
                View lastVisibleItem = lm.findViewByPosition(lastVisiblePosition);
                if (lastVisibleItem != null) {
                    return lastVisibleItem.getBottom() <= getBottom();
                }
            }
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            lastVisiblePosition = lm.findLastVisibleItemPosition();
            if (lastVisiblePosition == adapter.getItemCount() - 1) {
                View lastVisibleItem = lm.findViewByPosition(lastVisiblePosition);
                if (lastVisibleItem != null) {
                    return lastVisibleItem.getBottom() <= getBottom();
                }
            }
        }
        return false;
    }

    public boolean isFirstItemFullVisible() {
        final Adapter adapter = getAdapter();
        if (null == adapter || adapter.getItemCount() == 0) {
            return true;
        }
        int firstVisiblePosition;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return true;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
            firstVisiblePosition = lm.findFirstVisibleItemPosition();
            if (firstVisiblePosition == 0) {
                View firstVisibleItem = lm.findViewByPosition(firstVisiblePosition);
                if (firstVisibleItem != null) {
                    return firstVisibleItem.getTop() >= 0;
                }
            }
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            firstVisiblePosition = lm.findFirstVisibleItemPosition();
            if (firstVisiblePosition == 0) {
                View firstVisibleItem = lm.findViewByPosition(firstVisiblePosition);
                if (firstVisibleItem != null) {
                    return firstVisibleItem.getTop() >= 0;
                }
            }
        }
        return false;
    }
}
