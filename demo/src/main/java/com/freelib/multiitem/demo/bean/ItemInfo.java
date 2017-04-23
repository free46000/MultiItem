package com.freelib.multiitem.demo.bean;

/**
 * Created by free46000 on 2017/4/22.
 */
public class ItemInfo {
    private String name;
    private String value;

    public ItemInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
