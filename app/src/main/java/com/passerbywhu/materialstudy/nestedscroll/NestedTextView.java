package com.passerbywhu.materialstudy.nestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;

/**
 * Created by passe on 2017/6/19.
 */

public class NestedTextView extends AppCompatTextView implements NestedScrollingChild {
    private static final String TAG = "NestedScrollingChild";
    private NestedScrollingChildHelper mChildHelper;
    private GestureDetector mGestureDetector;
    private GestureDetector.OnGestureListener mOnGestureListener;
    private int[] consumed = new int[2];
    private int[] offsetWindow = new int[2];
    private Scroller mScroller;

    public NestedTextView(Context context) {
        this(context, null);
    }

    public NestedTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        mScroller = new Scroller(getContext());
        mOnGestureListener = new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.i(TAG, "onScroll distanceY is " + v1);
                float distanceY = v1;
                if (offsetWindow[1] != 0) {
                    distanceY -= offsetWindow[1];
                    offsetWindow[1] = 0;
                }
                boolean consumedAny = dispatchNestedPreScroll((int) v, (int) distanceY, consumed, offsetWindow);
                float remainY = distanceY;
                if (consumedAny) {
                    if (remainY < 0) {
                        remainY += consumed[1];
                    } else {
                        remainY -= consumed[1];
                    }
                }
                Log.i(TAG, "consumedY is " + consumed[1]);
                Log.i(TAG, "offsetWindowY is " + offsetWindow[1]);
                Log.i(TAG, "remainY is " + remainY);
                scrollBy(0, (int) remainY);
//                dispatchNestedScroll(0, (int) remainY, 0, 0, offsetWindow);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        };
        mGestureDetector = new GestureDetector(getContext(), mOnGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent y is " + event.getY());
//        if (offsetWindow[1] != 0) {
//            event.offsetLocation(0, offsetWindow[1]);
//            offsetWindow[1] = 0;
//            Log.i(TAG, "onTouchEvent y after offset is " + event.getY());
//        }
        final boolean handled = mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            stopNestedScroll();
        }
        return handled;
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }
}
