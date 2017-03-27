package com.freelib.multiitem.demo.viewholder;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.demo.R;
import com.freelib.multiitem.item.LoadMoreManager;
import com.freelib.multiitem.listener.OnLoadMoreListener;

/**
 * 加载更多视图管理类
 * Created by free46000 on 2017/3/26.
 */
public class LoadMoreHolderManager extends LoadMoreManager {

    public LoadMoreHolderManager(OnLoadMoreListener onLoadMoreListener) {
        super(onLoadMoreListener);
    }

    public LoadMoreHolderManager(OnLoadMoreListener onLoadMoreListener, boolean isAutoLoadMore) {
        super(onLoadMoreListener, isAutoLoadMore);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_load_more;
    }

    @Override
    protected void updateLoadInitView() {
        ((TextView) getView(loadMoreView, R.id.text)).setText("");
    }

    @Override
    protected void updateLoadingMoreView() {
        ((TextView) getView(loadMoreView, R.id.text)).setText(R.string.loading_more);
    }

    @Override
    protected void updateLoadCompletedView(boolean isLoadAll) {
        ((TextView) getView(loadMoreView, R.id.text))
                .setText(isLoadAll ? R.string.load_all : R.string.load_has_more);
    }

    @Override
    protected void updateLoadFailedView() {
        ((TextView) getView(loadMoreView, R.id.text)).setText(R.string.load_failed);
    }
}
