package com.passerbywhu.materialstudy.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

/**
 * Created by hzwuwenchao on 2015/11/25.
 */
public class DragSortRecyclerView extends HeaderFooterRecyclerView {
    private ItemTouchHelper.Callback mCallback;
    private ItemTouchHelper mItemTouchHelper;
    private boolean allowMoveUp = false, allowMoveDown = false;
    private boolean allowSwipeStart = false, allowSwipeEnd = false;

    public interface OnItemTouch {
        /**
         * DragSortRecyclerView保证了当from和to是HeaderView或者是FooterView的时候不会调用Adapter的onMove
         * @param from
         * @param to
         */
        void onMove(ViewHolder from, ViewHolder to);

        /**
         * DragSortRecyclerView保证了当target是HeaderView或者是FooterView的时候不会调用Adapter的onSwipe
         * @param target
         */
        void onSwiped(ViewHolder target);
    }

    public void enableDrag() {
        allowMoveUp = true;
        allowMoveDown = true;
    }

    public void enableSwipe() {
        allowSwipeStart = true;
        allowSwipeEnd = true;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof OnItemTouch)) {
            throw new IllegalArgumentException("The adapter DragSortRecyclerView use must implement OnItemTouch interface");
        }
        super.setAdapter(adapter);
    }

    public interface OnItemStateChangedListener {
        void onSelectedChanged(ViewHolder position, int actionState);
        boolean onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive);
        boolean onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                boolean isCurrentlyActive);
    }

    public void setOnItemStateChangeListener(OnItemStateChangedListener mOnItemStateChangeListener) {
        this.mOnItemStateChangeListener = mOnItemStateChangeListener;
    }

    private OnItemStateChangedListener mOnItemStateChangeListener;

    public DragSortRecyclerView(Context context) {
        this(context, null);
    }

    public DragSortRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragSortRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public static DragSortRecyclerView getNewInstance(Context context) {
        return new DragSortRecyclerView(context);
    }


    private void init() {
        mCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
                return mCallback.makeMovementFlags(
                        (allowMoveUp ? ItemTouchHelper.UP : 0) |
                                (allowMoveDown ? ItemTouchHelper.DOWN : 0), (allowSwipeStart ? ItemTouchHelper.START : 0) | (allowSwipeEnd ? ItemTouchHelper.END : 0));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                if (isHeaderViewAdded() && (viewHolder.getAdapterPosition() == 0 || target.getAdapterPosition() == 0)) {
                    return false;
                } else if (isFooterViewAdded() && (viewHolder.getAdapterPosition() == getAdapter().getItemCount() - 1 || target.getAdapterPosition() == getAdapter().getItemCount() - 1)) {
                    return false;
                } else {
                    ((OnItemTouch) recyclerView.getAdapter()).onMove(viewHolder, target);
                    return true;
                }
            }

            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                if (isHeaderViewAdded() && viewHolder.getAdapterPosition() == 0) {
                    return;
                } else if (isFooterViewAdded() && viewHolder.getAdapterPosition() == getAdapter().getItemCount() - 1) {
                    return;
                }
                ((OnItemTouch) getAdapter()).onSwiped(viewHolder);
            }

            @Override
            public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (mOnItemStateChangeListener != null) {
                    mOnItemStateChangeListener.onSelectedChanged(viewHolder, actionState);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (mOnItemStateChangeListener == null || !mOnItemStateChangeListener.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)); {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (mOnItemStateChangeListener == null || !mOnItemStateChangeListener.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)) {
                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
            }
        };
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(this);
    }
}
