//package com.freelib.multiitem.item;
//
//import android.support.annotation.NonNull;
//
//import com.freelib.multiitem.adapter.holder.BindViewHolderManager;
//import com.freelib.multiitem.adapter.holder.ViewHolderManager;
//import com.freelib.multiitem.listener.OnStateClickListener;
//
///**
// * Created by free46000 on 2017/4/23.
// */
//public abstract class BaseItemState<T extends BaseItemState> extends BindViewHolderManager<T> implements ItemManager {
//    protected OnStateClickListener onStateClickListener;
//
//
//    public OnStateClickListener getOnStateClickListener() {
//        return onStateClickListener;
//    }
//
//    public void setOnStateClickListener(OnStateClickListener onStateClickListener) {
//        this.onStateClickListener = onStateClickListener;
//    }
//
//    @Override
//    public ViewHolderManager getViewHolderManager() {
//        return this;
//    }
//
//
//    @NonNull
//    @Override
//    public String getItemTypeName() {
//        return toString();
//    }
//
//
//}
