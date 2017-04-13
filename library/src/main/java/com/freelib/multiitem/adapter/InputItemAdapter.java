package com.freelib.multiitem.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.InputHolderManager;
import com.freelib.multiitem.item.HiddenItemInput;
import com.freelib.multiitem.item.ItemInput;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by free46000 on 2017/4/11.
 */
public class InputItemAdapter extends BaseItemAdapter {
    protected List<InputHolderManager> inputHolderManagers = new ArrayList<>();
    protected List<HiddenItemInput> hiddenItemInputs = new ArrayList<>();

    public void addHiddenItem(@NonNull HiddenItemInput... hiddenItems) {
        if (hiddenItems.length == 0) {
            return;
        }
        hiddenItemInputs.addAll(Arrays.asList(hiddenItems));
    }

//    public boolean isValueValidate()

    public JSONObject getInputJson() {
        return new JSONObject(getInputValueMap());
    }

    public Map<String, Object> getInputValueMap() {
        Map<String, Object> valueMap = new HashMap<>();
        Map<String, Object> itemValueMap;
        for (InputHolderManager inputHolderManager : inputHolderManagers) {
            itemValueMap = inputHolderManager.getValueMap();
            if (itemValueMap != null) {
                valueMap.putAll(itemValueMap);
            }
        }

        for (HiddenItemInput hiddenItem : hiddenItemInputs) {
            valueMap.putAll(hiddenItem.getValueMap());
        }

        return valueMap;
    }


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
