package com.freelib.multiitem.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseItemState;

/**
 * Created by free46000 on 2017/4/23.
 */
public class StateViewHelper {
    private RecyclerView recyclerView;
    private BaseItemState itemState;

    private RecyclerView.Adapter dataAdapter;
    private BaseItemAdapter stateAdapter;
    private boolean isShowState;

    /**
     * @param recyclerView
     * @param itemState
     */
    public StateViewHelper(@NonNull RecyclerView recyclerView, BaseItemState itemState) {
        this.recyclerView = recyclerView;
        this.dataAdapter = recyclerView.getAdapter();
        if (dataAdapter == null) {
            throw new IllegalArgumentException("请在设置完adapter后在初始化本实例！");
        }
        this.itemState = itemState;
    }

    public void show() {
        if (stateAdapter != null) {
            return;
        }
        stateAdapter = new BaseItemAdapter();
        stateAdapter.addDataItem(itemState);
        recyclerView.setAdapter(stateAdapter);
    }

    public void hide() {
        if (dataAdapter == null) {
            return;
        }
        stateAdapter = null;
        recyclerView.setAdapter(dataAdapter);
    }


}
