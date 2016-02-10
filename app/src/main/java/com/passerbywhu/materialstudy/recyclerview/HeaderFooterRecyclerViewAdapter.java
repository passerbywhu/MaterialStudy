package com.passerbywhu.materialstudy.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hzwuwenchao on 2015/11/23.
 * 支持Footer和Header的RecyclerView Adapter
 * 暂时仅支持一个Header和一个Footer
 * 这个Adapter为其子Adapter封装了Header和Footer的细节。子Adapter只需要按照普通Adapter的操作即可。
 */
public abstract class HeaderFooterRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mWrapData;
    protected List<T> mRealData;
    private boolean isHeaderAdded = false;
    private boolean isFooterAdded = false;
    protected View mHeaderView;
    protected View mFooterView;
    protected static final int TYPE_HEADER = 1000;
    protected static final int TYPE_FOOTER = 1001;
    protected static final int TYPE_NORMAL = 1;
    protected Context mContext;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public HeaderFooterRecyclerViewAdapter(Context context) {
        mWrapData = new ArrayList<>();
        mRealData = new ArrayList<>();
        this.mContext = context;
    }

    public List getData() {
        return Collections.unmodifiableList(mRealData);
    }

    /**
     * 只允许remove非Header和非Footer的item
     * @param position
     * @return
     */
    public T removeItem(int position) {
        if (isHeaderAdded && position == 0) {
            return null;
        }
        if (isFooterAdded && position == mWrapData.size() - 1) {
            return null;
        }
        T result;
        result = mRealData.remove(getRealPosition(position));
        mWrapData.remove(position);
        return result;
    }

    public void addItem(int position, T t) {
        mRealData.add(Math.max(getRealPosition(position), 0), t);
        mWrapData.add(position, t);
    }

    public void setData(List list) {
        if (list == null) {
            return;
        }
        mRealData.clear();
        mRealData.addAll(list);
        mWrapData.clear();
        mWrapData.addAll(list);
        if (isHeaderAdded) {
            mWrapData.add(0, null);
        }
        if (isFooterAdded) {
            mWrapData.add(mWrapData.size(), null);
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mRealData.get(position);
    }

    //position >= 1
    private int getRealPosition(int position) {
        if (isHeaderAdded) {
            return position - 1;
        } else {
            return position;
        }
    }

    public void addHeaderView(View view) {
        if (isHeaderAdded) {
            return;
        }
        mWrapData.add(0, null);
        mHeaderView = view;
        isHeaderAdded = true;
    }

    public void addFooterView(View view) {
        if (isFooterAdded) {
            return;
        }
        mWrapData.add(mWrapData.size(), null);
        mFooterView = view;
        isFooterAdded = true;
    }

    @Deprecated
    @Override
    /**
     * Please use getDataSize instead
     */
    public int getItemCount() {
        return mWrapData.size();
    }

    protected int getDataSize() {
        return mRealData.size();
    }

    @Deprecated
    @Override
    /**
     * Please use onCreateItemViewHolder instead
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        } else {
            return onCreateItemViewHolder(parent ,viewType);
        }
    }

    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Deprecated
    @Override
    /**
     * Please use onBindItemViewHolder instead
     */
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == 0 && isHeaderAdded) {
            return;
        } else if (position == mWrapData.size() - 1 && isFooterAdded) {
            return;
        } else {
            onBindItemViewHolder((VH) holder, getRealPosition(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick((VH) holder, getRealPosition(position));
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getRealPosition(position), v);
                    }
                }
            });
        }
    }

    protected void onItemClick(VH holder, final int position) {
    }

    protected abstract void onBindItemViewHolder(VH holder, int position);

    @Deprecated
    @Override
    /**
     * Please use getViewType instead
     */
    public int getItemViewType(int position) {
        if (isHeaderAdded && position == 0) {
            return TYPE_HEADER;
        }
        if (isFooterAdded && position == mWrapData.size() - 1) {
            return TYPE_FOOTER;
        }
        return getViewType(getRealPosition(position));
    }

    protected int getViewType(int position) {
        return TYPE_NORMAL;
    }

    @Deprecated
    @Override
    /**
     * Please use getId instead
     */
    public long getItemId(int position) {
        if (isHeaderAdded && position == 0) {
            return 0;
        }
        if (isFooterAdded && position == mWrapData.size() - 1) {
            return mWrapData.size() - 1;
        }
        return getId(getRealPosition(position));
    }

    public long getId(int position) {
        return position;
    }
}
