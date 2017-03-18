package com.freelib.multiitem.demo.bean;

/**
 * Created by free46000 on 2017/3/19.
 */

public class ImageTextBean {
    private int img;
    private String text;

    public ImageTextBean(int img, String text) {
        this.img = img;
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
