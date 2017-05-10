package com.freelib.multiitem.adapter.holder;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.item.ItemData;

/**
 * ViewHolder的管理类
 * 需要继承此类，实现ViewHolder的创建{@link #onCreateViewHolder}与绑定{@link #onBindViewHolder}
 *
 * @author free46000
 */
public abstract class ViewHolderManager<T, V extends BaseViewHolder> {
    private boolean fullSpan;
    private int spanSize;

    protected ViewModel viewModel = new BaseViewModel();

    /**
     * 创建ViewHolder
     * {@link android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder}
     */
    @NonNull
    public abstract V onCreateViewHolder(@NonNull ViewGroup parent);

    /**
     * 为ViewHolder绑定数据
     * {@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder}
     *
     * @param t 数据源
     */
    public abstract void onBindViewHolder(V holder, T t);

    /**
     * 为ViewHolder绑定数据，并根据params做出相应设置
     *
     * @param t      数据源
     * @param params {@link ViewHolderParams}
     */
    public void onBindViewHolder(@NonNull V holder, @NonNull T t, @NonNull ViewHolderParams params) {
        // TODO 如果以后有需要不直接在item view上设置Click事件，在MultiViewHolder增加itemHandlerView属性即可
        if (isClickable()) {
            holder.itemView.setOnClickListener(params.getClickListener());
            holder.itemView.setOnLongClickListener(params.getLongClickListener());
        }

        //如果数据源是ItemData，则执行定制化处理
        if (t instanceof ItemData) {
            bindView(holder, (ItemData) t);
        }
        onBindViewHolder(holder, t);
    }

    protected void bindView(V holder, ItemData data) {
        setVisibility(holder.itemView, data.getVisibility());
    }

    protected void setVisibility(View itemView, int visibility) {
        itemView.setVisibility(visibility);
    }


    public final int getPosition(@NonNull final ViewHolder holder) {
        return holder.getAdapterPosition();
    }

    /**
     * 通过资源id生成item itemView
     *
     * @param parent onCreateViewHolder中的参数
     * @return 返回item itemView
     */
    protected View getItemView(ViewGroup parent) {
        return viewModel.getItemView(parent, getItemLayoutId());
    }

    /**
     * item布局文件id
     *
     * @return layout资源id
     */
    @LayoutRes
    protected abstract int getItemLayoutId();

    /**
     * 在指定view中获取控件为id的view
     *
     * @param view 外层view
     * @param id   需要获取view的控件id
     * @return itemView
     */
    protected <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 在指定viewHolder中获取控件为id的view
     *
     * @return itemView
     */
    protected <T extends View> T getView(ViewHolder viewHolder, int id) {
        return getView(viewHolder.itemView, id);
    }

    /**
     * @param fullSpan 是否需要填满父布局（适用于表格布局）
     */
    public void setFullSpan(boolean fullSpan) {
        this.fullSpan = fullSpan;
    }

    /**
     * @return 是否填满父布局
     * @see StaggeredGridLayoutManager.LayoutParams#setFullSpan
     * @see GridLayoutManager#setSpanSizeLookup
     */
    public boolean isFullSpan() {
        return fullSpan;
    }

    /**
     * 根据spanCount获取当前所占span大小(适用于表格布局)<br>
     * 如果被设置过正整数则返回；如果是fullSpan则返回spanCount；其余返回1<br>
     * GridLayoutManager模式下，调整本方法返回值达到不同Item占用不同宽度的功能
     *
     * @param spanCount span总数量
     * @return 当前所占span大小
     * @see GridLayoutManager#setSpanSizeLookup
     */
    public int getSpanSize(int spanCount) {
        return spanSize > 0 ? spanSize : (isFullSpan() ? spanCount : 1);
    }

    /**
     * @param spanSize 所占span大小
     */
    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public boolean isClickable() {
        return true;
    }


    /**
     * 开启Data bind
     *
     * @see ViewModel
     */
    protected void enableDataBind() {
        viewModel = new DataBindViewModel();
    }

    interface ViewModel {
        View getItemView(ViewGroup parent, @LayoutRes int layoutId);
    }

    static class BaseViewModel implements ViewModel {

        @Override
        public View getItemView(ViewGroup parent, @LayoutRes int layoutId) {
            return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        }
    }

    static class DataBindViewModel implements ViewModel {

        @Override
        public View getItemView(ViewGroup parent, @LayoutRes int layoutId) {
            return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false).getRoot();
        }
    }

}