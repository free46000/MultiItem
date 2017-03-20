package com.freelib.multiitem.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.ViewHolderManager;
import com.freelib.multiitem.adapter.holder.ViewHolderManagerGroup;
import com.freelib.multiitem.adapter.holder.ViewHolderParams;
import com.freelib.multiitem.adapter.type.ItemTypeManager;
import com.freelib.multiitem.common.Const;
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
    public <T, V extends BaseViewHolder> void register(@NonNull Class<T> cls, @NonNull ViewHolderManagerGroup group) {
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
    public void addDataItem(Object item) {
        addDataItem(dataItems.size(), item);
    }

    /**
     * 在指定位置添加Item
     */
    public void addDataItem(int position, Object item) {
        dataItems.add(position, item);
        notifyItemRangeInserted(position, 1);
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


        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 移除Item 包括数据源和界面的移除
     *
     * @param position 需要被移除Item的position
     */
    public void removeDataItem(int position) {
        dataItems.remove(position);
        notifyItemRemoved(position);
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
        int footPosition = position - getHeadCount() - dataItems.size();
        if (footPosition >= 0) {
            return footItems.get(footPosition);
        }
        return dataItems.get(position - headItems.size());
    }

    /**
     * 清空数据
     */
    public void clearData() {
        dataItems.clear();
        headItems.clear();
        footItems.clear();
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
        int type = itemTypeManager.getItemType(getItem(position).getClass());
        if (type < 0)
            throw new RuntimeException("没有为" + getItem(position).getClass() + "找到对应的item view provider，是否注册了？");
        return type;
    }

    @Override
    public int getItemCount() {
        return dataItems.size() + getHeadCount() + getFootCount();
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
