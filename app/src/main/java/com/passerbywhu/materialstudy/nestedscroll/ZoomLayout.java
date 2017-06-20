package com.passerbywhu.materialstudy.nestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by passe on 2017/6/19.
 */

public class ZoomLayout extends LinearLayoutCompat implements NestedScrollingParent {
    private static final String TAG = "NestedScrollingParent";
    private int originalImageHeight = 0;

    private NestedScrollingParentHelper mParentHelper;

    public ZoomLayout(Context context) {
        this(context, null);
    }

    public ZoomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.i(TAG, "onNestedScrollAccepted");
        Log.i(TAG, "child view is " + child);
        Log.i(TAG, "target view is " + target);
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.i(TAG, "onStopNestedScroll");
        Log.i(TAG, "child view is " + child);
        mParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.i(TAG, "onNestedPreScroll");
        Log.i(TAG, "target view is " + target);
        View view = getChildAt(0);
        if (originalImageHeight == 0) {
            originalImageHeight = view.getHeight();
        }
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        int height = view.getHeight();
        int consumedY = 0;
        if (dy < 0) { //向下滑动
            consumedY = Math.abs(dy) - (originalImageHeight - height) <= 0 ? Math.abs(dy) : (originalImageHeight - height);
            params.height = params.height + consumedY;
        } else {
            consumedY = Math.abs(dy) - height <= 0 ? Math.abs(dy) : height;
            params.height = params.height - consumedY;
        }
        view.setLayoutParams(params);
        //这里如果不主动设置target的top，而只设置ImageView的params的height的话，NestedSrcollingChildHelper算出来的offsetInWindow的值一直是0
        //因为NestedScrollingChildHelper计算的时机时，reLayout并没有完成。这个时候target的数据是没有变的。
        if (dy < 0) {
            target.setTop(target.getTop() + consumedY);
        } else {
            target.setTop(target.getTop() - consumedY);
        }
        consumed[1] = consumedY;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }
}
