package com.freelib.multiitem.demo.input;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by free46000 on 2017/4/11.
 */
public class InputItemAdapter extends BaseItemAdapter {
    protected List<InputHolderManager> inputHolderManagers = new ArrayList<>();
//    protected List<ItemViewHolder> itemHiddenInputViewList

    //public void addHiddenItem(ItemHidden... itemHiddens)

//    public boolean isValueValidate()

//    public Map<String, Object> getInputValueMap() {
//        Map<String, Object> map = new HashMap<>();
//        for (ItemViewHolder iiv : itemInputViewMap.values()) {
//            Map<String, Object> valueMap = iiv.getValueMap();
//            if (!TextUtils.isEmpty(iiv.getKey()) && valueMap != null) {
//                map.putAll(valueMap);
//            }
//        }
//        for (ItemViewHolder iiv : itemHiddenInputViewList) {
//            map.putAll(iiv.getValueMap());
//        }
//        return map;
//    }


    @Override
    public void clearData() {
        super.clearData();
        inputHolderManagers.clear();
    }

    @Override
    protected void setItem(@NonNull List<? extends Object> dataItems) {
        super.setItem(dataItems);
        inputHolderManagers.clear();
        fillInputHolderManagers(0, dataItems);
    }

    @Override
    protected void addItem(int position, @NonNull List<? extends Object> items) {
        super.addItem(position, items);
        fillInputHolderManagers(position, items);
    }


    protected void fillInputHolderManagers(int position, @NonNull List<? extends Object> items) {
        for (Object item : items) {
            if (item instanceof ItemInput) {
                inputHolderManagers.add(position++, ((ItemInput) item).getViewHolderManager());
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewHolder.viewHolderManager instanceof InputHolderManager) {
            inputHolderManagers.add((InputHolderManager) viewHolder.viewHolderManager);
        }
        return viewHolder;
    }

    public boolean isValueChange() {
        for (InputHolderManager inputHolderManager : inputHolderManagers) {
            if (inputHolderManager.isValueChange()) {
                return true;
            }
        }
        return false;
    }


}
