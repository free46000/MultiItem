package com.freelib.multiitem.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.HeadFootHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.listener.OnLoadMoreListener;

/**
 * 加载更多管理类
 * <p>
 * Created by free46000 on 2017/3/26.
 */
public abstract class LoadMoreManager extends HeadFootHolderManager implements ItemManager {
    protected BaseItemAdapter adapter;
    protected OnLoadMoreListener onLoadMoreListener;
    protected boolean isAutoLoadMore;
    protected View loadMoreView;
    protected View.OnClickListener loadMoreClickListener = view -> onLoadMore();
    protected boolean isLoadFinish;

    /**
     * 默认滑动到底部自动加载为true
     *
     * @param onLoadMoreListener 加载更多监听
     */
    public LoadMoreManager(OnLoadMoreListener onLoadMoreListener) {
        this(onLoadMoreListener, true);
    }

    /**
     * @param onLoadMoreListener 加载更多监听
     * @param isAutoLoadMore     滑动到底部是否自动加载
     */
    public LoadMoreManager(OnLoadMoreListener onLoadMoreListener, boolean isAutoLoadMore) {
        this.onLoadMoreListener = onLoadMoreListener;
        this.isAutoLoadMore = isAutoLoadMore;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        BaseViewHolder holder = super.onCreateViewHolder(parent);
        loadMoreView = holder.itemView;
        setOnLoadMoreClickListener(loadMoreClickListener);
        return holder;
    }

    /**
     * 为loadMoreView设置点击监听
     */
    private void setOnLoadMoreClickListener(View.OnClickListener loadMoreClickListener) {
        loadMoreView.setOnClickListener(loadMoreClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, Object o) {
        if (isNeeLoadMore(holder)) {
            if (isAutoLoadMore()) {
                //当可以加载更多数据并开启自动加载后调用
                onLoadMore();
            }
        } else {
            updateLoadInitView();
        }
    }

    /**
     * 避免第一次可见时去加载数据
     * 若head和foot的数量小于当前loadMore的位置则证明没有ItemData数据，即为RecyclerView加载数据前
     *
     * @param holder
     * @return 是否需要加载更多
     */
    protected boolean isNeeLoadMore(@NonNull BaseViewHolder holder) {
        int headFootCount = adapter.getHeadCount() + adapter.getFootCount();
        return headFootCount < holder.getItemPosition();
    }

    /**
     * 更新加载初始化时的视图<br>
     * 当RecyclerView加载数据前，即只含有head和foot视图时
     */
    protected abstract void updateLoadInitView();

    @Override
    protected abstract int getItemLayoutId();

    /**
     * 正在加载更多处理
     */
    protected void onLoadMore() {
        if (!isLoadFinish) {
            getOnLoadMoreListener().onLoadMore();
            setOnLoadMoreClickListener(null);
            updateLoadingMoreView();
        }
    }

    /**
     * 更新正在加载更多时的视图
     */
    protected abstract void updateLoadingMoreView();

    /**
     * 加载完成处理
     *
     * @param isLoadAll 是否加载全部
     */
    public void loadCompleted(boolean isLoadAll) {
        setOnLoadMoreClickListener(isLoadAll ? null : loadMoreClickListener);
        isLoadFinish = isLoadAll;
        updateLoadCompletedView(isLoadAll);
    }

    /**
     * 更新加载完成时的视图
     *
     * @param isLoadAll 是否加载全部
     */
    protected abstract void updateLoadCompletedView(boolean isLoadAll);

    /**
     * 加载失败处理
     */
    public void loadFailed() {
        setOnLoadMoreClickListener(loadMoreClickListener);
        updateLoadFailedView();
    }

    /**
     * 更新加载失败的视图
     */
    protected abstract void updateLoadFailedView();

    @Override
    public boolean isClickable() {
        return false;
    }

    @NonNull
    @Override
    public String getItemTypeName() {
        //防止被复用
        return toString();
    }

    @NonNull
    @Override
    public ViewHolderManager getViewHolderManager() {
        return this;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    /**
     * 设置加载更多回调
     *
     * @param onLoadMoreListener OnLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * @return 是否自动加载更多
     */
    public boolean isAutoLoadMore() {
        return isAutoLoadMore;
    }

    /**
     * 设置是否自动加载更多
     */
    public void setAutoLoadMore(boolean autoLoadMore) {
        isAutoLoadMore = autoLoadMore;
    }

    public void setAdapter(BaseItemAdapter adapter) {
        this.adapter = adapter;
    }
}
