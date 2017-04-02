package com.freelib.multiitem.demo.bean;

import com.freelib.multiitem.item.BaseItemData;

/**
 * 此处可以继承BaseItemData也可以实现ItemData{}
 * Created by free46000 on 2017/4/3.
 */
public class TextDragBean extends BaseItemData {
    private String text;

    public TextDragBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
