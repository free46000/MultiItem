package com.freelib.multiitem.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.HeadFootHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManagerGroup;
import com.freelib.multiitem.adapter.holder.ViewHolderParams;
import com.freelib.multiitem.adapter.type.ItemTypeManager;
import com.freelib.multiitem.common.Const;
import com.freelib.multiitem.item.ItemUnique;
import com.freelib.multiitem.item.LoadMoreManager;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.freelib.multiitem.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的adapter<p>
 * 天然支持多类型视图 {@link ItemTypeManager}<br>
 * adapter与view holder创建绑定解耦{@link #register(Class, ViewHolderManager)}
 *
 * @author free46000  2017/03/15
 * @version v1.0
 */
public class BaseItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Object> dataItems = new ArrayList<>();
    private List<Object> headItems = new ArrayList<>();
    private List<Object> footItems = new ArrayList<>();
    private ItemTypeManager itemTypeManager;
    private LoadMoreManager loadMoreManager;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;


    public BaseItemAdapter() {
        itemTypeManager = new ItemTypeManager();
    }


    /**
     * 为数据源注册ViewHolder的管理类{@link ViewHolderManager}<br>
     * ViewHolder的管理类使adapter与view holder的创建绑定解耦<br>
     * 通过{@link ItemTypeManager}管理不同数据源和ViewHolder的关系<br>
     *
     * @param cls     数据源class
     * @param manager 数据源管理类
     * @param <T>     数据源
     * @param <V>     ViewHolder
     * @see #register(Class, ViewHolderManagerGroup)  为相同数据源注册多个ViewHolder的管理类
     */
    public <T, V extends BaseViewHolder> void register(@NonNull Class<T> cls, @NonNull ViewHolderManager<T, V> manager) {
        itemTypeManager.register(cls, manager);
    }

    /**
     * 为相同数据源注册多个ViewHolder的管理类{@link ViewHolderManagerGroup}<br>
     * 主要为相同数据源根据内部属性的值对应多个ViewHolderManager设计，常见的如聊天界面的消息<br>
     *
     * @param cls   数据源class
     * @param group 对应相同数据源的一组数据源管理类
     * @param <T>   数据源
     * @param <V>   ViewHolder
     * @see #register(Class, ViewHolderManager)  为数据源注册ViewHolder的管理类
     */
    public <T, V extends BaseViewHolder> void register(@NonNull Class<T> cls, @NonNull ViewHolderManagerGroup<T> group) {
        itemTypeManager.register(cls, group);
    }

    /**
     * 设置Item view点击监听
     */
    public void setOnItemClickListener(@NonNull OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置item view长按监听
     */
    public void setOnItemLongClickListener(@NonNull OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置Item list
     */
    public void setDataItems(@NonNull List<Object> dataItems) {
        this.dataItems = dataItems;
        notifyDataSetChanged();
    }

    /**
     * 添加Item
     */
    public void addDataItem(Object... items) {
        addDataItem(dataItems.size(), items);
    }

    /**
     * 在指定位置添加Item
     */
    public void addDataItem(int position, Object... items) {
        if (items != null && items.length > 0) {
            for (Object item : items) {
                dataItems.add(position, item);
            }
            notifyItemRangeInserted(position + getHeadCount(), items.length);
        }
    }

    /**
     * 移动Item的位置 包括数据源和界面的移动
     *
     * @param fromPosition Item之前所在位置
     * @param toPosition   Item新的位置
     */
    public void moveDataItem(int fromPosition, int toPosition) {
        //考虑到跨position移动的时候处理不能简单的Collections.swap
        // 当from小于to的时候由于先remove了from的元素，数组位置向前移了一位所以减一
        toPosition = fromPosition < toPosition ? toPosition - 1 : toPosition;
        dataItems.add(toPosition, dataItems.remove(fromPosition));

        notifyItemMoved(fromPosition + getHeadCount(), toPosition + getHeadCount());
    }


    /**
     * 移除Item 包括数据源和界面的移除
     *
     * @param position 需要被移除Item的position
     */
    public void removeDataItem(int position) {
        removeDataItem(position, 1);
    }

    /**
     * 改变Item 包括数据源和界面的移除
     *
     * @param position 需要被移除第一个Item的position
     * @param position 需要被移除Item的个数
     */
    public void removeDataItem(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            dataItems.remove(position);
        }
        notifyItemRangeRemoved(position + getHeadCount(), itemCount);
    }

    /**
     * 添加foot View，默认为充满父布局
     * <p>
     * {@link ViewHolderManager#isFullSpan()}
     * {@link ViewHolderManager#getSpanSize(int)}
     *
     * @param footView foot view
     * @see HeadFootHolderManager
     * @see ItemUnique
     */
    public void addFootView(View footView) {
        addFootItem(new ItemUnique(new HeadFootHolderManager(footView)));
    }

    /**
     * 添加foot Item ,如是表格不居中需要充满父布局请设置对应属性<br>
     * {@link ViewHolderManager#isFullSpan()}
     * {@link ViewHolderManager#getSpanSize(int)}
     *
     * @param footItem foot item
     * @see HeadFootHolderManager
     */
    public void addFootItem(Object footItem) {
        footItems.add(footItem);
    }

    /**
     * 添加head View，默认为充满父布局
     * <p>
     * {@link ViewHolderManager#isFullSpan()}
     * {@link ViewHolderManager#getSpanSize(int)}
     *
     * @param headView head view
     * @see HeadFootHolderManager
     * @see ItemUnique
     */
    public void addHeadView(View headView) {
        addHeadItem(new ItemUnique(new HeadFootHolderManager(headView)));
    }

    /**
     * 添加head Item ,如是表格不居中需要充满父布局请设置对应属性<br>
     * {@link ViewHolderManager#isFullSpan()}
     * {@link ViewHolderManager#getSpanSize(int)}
     *
     * @param headItem head item
     * @see HeadFootHolderManager
     */
    public void addHeadItem(Object headItem) {
        headItems.add(headItem);
    }

    /**
     * 开启loadMore，使列表支持加载更多<p>
     * 本方法原理是添加{@link #addFootItem(Object)} 并且对添加顺序敏感需要注意在最后调用本方法才可以将加载更多视图放在底部
     *
     * @param loadMoreManager LoadMoreManager
     */
    public void enableLoadMore(LoadMoreManager loadMoreManager) {
        this.loadMoreManager = loadMoreManager;
        loadMoreManager.setAdapter(this);
        addFootItem(loadMoreManager);
    }

    /**
     * 加载完成
     *
     * @see LoadMoreManager#loadCompleted(boolean)
     */
    public void setLoadCompleted(boolean isLoadAll) {
        if (loadMoreManager != null)
            loadMoreManager.loadCompleted(isLoadAll);
    }

    /**
     * 加载失败
     *
     * @see LoadMoreManager#loadFailed()
     */
    public void setLoadFailed() {
        if (loadMoreManager != null)
            loadMoreManager.loadFailed();
    }

    /**
     * @return 获取当前数据源List，不包含head和foot
     */
    public List<Object> getDataList() {
        return dataItems;
    }


    /**
     * @param position int
     * @return 返回指定位置Item
     */
    public Object getItem(int position) {
        if (position < headItems.size()) {
            return headItems.get(position);
        }

        position -= headItems.size();
        if (position < dataItems.size()) {
            return dataItems.get(position);
        }

        position -= dataItems.size();
        return footItems.get(position);
    }

    /**
     * 清空数据
     */
    public void clearAllData() {
        dataItems.clear();
        headItems.clear();
        footItems.clear();
    }

    /**
     * 清空Item数据不含head 和 foot
     */
    public void clearData() {
        dataItems.clear();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderManager provider = itemTypeManager.getViewHolderManager(viewType);
        BaseViewHolder viewHolder = provider.onCreateViewHolder(parent);
        viewHolder.viewHolderManager = provider;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object item = getItem(position);
        ViewHolderManager manager = holder.viewHolderManager;
        ViewHolderParams params = new ViewHolderParams()
                .setItemCount(getItemCount()).setClickListener(onItemClickListener)
                .setLongClickListener(onItemLongClickListener);
        manager.onBindViewHolder(holder, item, params);
        //赋值 方便以后使用
        holder.itemView.setTag(Const.VIEW_HOLDER_TAG, holder);
        holder.itemData = item;
    }

    @Override
    public int getItemViewType(int position) {
        int type = itemTypeManager.getItemType(getItem(position));
        if (type < 0)
            throw new RuntimeException("没有为" + getItem(position).getClass() + "找到对应的item view provider，是否注册了？");
        return type;
    }

    @Override
    public int getItemCount() {
        return dataItems.size() + getHeadCount() + getFootCount();
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        System.out.println("onViewAttachedToWindow:::" + holder.getItemPosition() + "==" + holder.getItemData());
        //当StaggeredGridLayoutManager的时候设置充满横屏
        if (holder.getViewHolderManager().isFullSpan() && holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        System.out.println("onAttachedToRecyclerView:::" + getItemCount());
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            //GridLayoutManager时设置每行的span
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                ViewHolderManager holderManager;

                @Override
                public int getSpanSize(int position) {
                    holderManager = itemTypeManager.getViewHolderManager(getItemViewType(position));
                    return holderManager.getSpanSize(gridManager.getSpanCount());
                }
            });
        }
    }

    /**
     * @return head view个数
     */
    public int getHeadCount() {
        return headItems.size();
    }

    /**
     * @return foot view个数
     */
    public int getFootCount() {
        return footItems.size();
    }

}
