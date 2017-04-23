package com.freelib.multiitem.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.item.BaseItemState;

/**
 * 状态页（如空白错误页等）辅助类
 * <p>
 * 状态页展示时会作为RecyclerView的唯一的Item展示在界面中
 * 注意：需要在RecyclerView设置完adapter后在初始化本实例
 * Created by free46000 on 2017/4/23.
 */
public class StateViewHelper {
    private RecyclerView recyclerView;
    private BaseItemState itemState;

    private RecyclerView.Adapter dataAdapter;
    private BaseItemAdapter stateAdapter;

    /**
     * 需要在RecyclerView设置完adapter后在初始化本实例
     *
     * @param recyclerView
     * @param itemState
     */
    public StateViewHelper(@NonNull RecyclerView recyclerView, @NonNull BaseItemState itemState) {
        this.recyclerView = recyclerView;
        //记住RecyclerView初始的Adapter
        this.dataAdapter = recyclerView.getAdapter();
        if (dataAdapter == null) {
            throw new IllegalArgumentException("请在设置完adapter后在初始化本实例！");
        }
        this.itemState = itemState;
    }

    /**
     * 展示状态页
     * <p>
     * 为RecyclerView设置新的stateAdapter，本adapter中保存唯一的状态页Item{@link BaseItemState}
     */
    public void show() {
        if (stateAdapter != null) {
            return;
        }
        stateAdapter = new BaseItemAdapter();
        stateAdapter.addDataItem(itemState);
        recyclerView.setAdapter(stateAdapter);
    }

    /**
     * 隐藏状态页
     * <p>
     * 将RecyclerView的Adapter设置为初始化时记住的Adapter
     */
    public void hide() {
        if (dataAdapter == null) {
            return;
        }
        stateAdapter = null;
        recyclerView.setAdapter(dataAdapter);
    }


}
