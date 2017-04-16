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

    /**
     * 添加隐藏域的Item，本Item用户不可见，{@link #getInputValueMap}时返回{key:value}
     *
     * @param key   String
     * @param value Object
     * @see #addHiddenItem(HiddenItemInput...)
     */
    public void addHiddenItem(@NonNull String key, Object value) {
        addHiddenItem(new HiddenItemInput(key, value));
    }

    /**
     * 添加隐藏域的Item，本Item用户不可见，{@link #getInputValueMap}时返回{key:value}
     *
     * @param hiddenItems String
     */
    public void addHiddenItem(@NonNull HiddenItemInput... hiddenItems) {
        if (hiddenItems.length == 0) {
            return;
        }
        hiddenItemInputs.addAll(Arrays.asList(hiddenItems));
    }

//    public boolean isValueValidate()

    /**
     * 获取所有Input Item和隐藏域的表单内容并组装
     *
     * @return 表单内容json
     * @see #getInputValueMap()
     * @see InputHolderManager#getValueMap()
     */
    public JSONObject getInputJson() {
        return new JSONObject(getInputValueMap());
    }

    /**
     * 获取所有Input Item和隐藏域的表单内容并组装
     *
     * @return 表单内容Map
     * @see #getInputValueMap()
     * @see InputHolderManager#getValueMap()
     */
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

    /**
     * 查找ItemInput并填充InputHolderManager集合
     *
     * @param position int
     * @param items    add和set的数据源
     */
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

    /**
     * 表单内容是否在初始化后发生改变，常见的如用户手动改变表单Item的某项值
     *
     * @return true:已改变；false:未改变
     * @see InputHolderManager#isValueChange()
     */
    public boolean isValueChange() {
        for (InputHolderManager inputHolderManager : inputHolderManagers) {
            if (inputHolderManager.isValueChange()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 表单是否验证有效，如Item为空验证
     *
     * @return true:有效；false:无效
     * @see InputHolderManager#isValueValid()
     */
    public boolean isValueValid() {
        for (InputHolderManager inputHolderManager : inputHolderManagers) {
            if (!inputHolderManager.isValueValid()) {
                return false;
            }
        }
        return true;
    }

}
