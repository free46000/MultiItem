package com.freelib.multiitem.demo.bean;

import com.freelib.multiitem.item.BaseItemData;

/**
 * 注意：在正常业务中不需要继承BaseItemData，此处也可以实现ItemData接口，只是为了Drag功能
 * <p>
 * Created by free46000 on 2017/3/19.
 */
public class ImageTextBean extends BaseItemData {
    private int img;
    private String imgUrl;
    private String text;

    public ImageTextBean(int img, String text) {
        this.img = img;
        this.text = text;
    }

    public ImageTextBean(String imgUrl, String text) {
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return text;
    }
}
