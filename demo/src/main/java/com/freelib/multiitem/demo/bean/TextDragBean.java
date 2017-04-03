package com.freelib.multiitem.demo.bean;

import com.freelib.multiitem.item.BaseItemData;
import com.freelib.multiitem.item.ItemDrag;

/**
 * 此处可以继承BaseItemData也可以实现ItemData{}
 * Created by free46000 on 2017/4/3.
 */
public class TextDragBean extends BaseItemData implements ItemDrag {
    private String text;
    private boolean isCanMove = true;
    private boolean isCanChangeRecycler = true;
    private boolean isCanDrag = true;

    public TextDragBean(String text) {
        this.text = text;
    }

    public TextDragBean(String text, boolean isCanMove) {
        this.text = text;
        this.isCanMove = isCanMove;
    }

    public TextDragBean(String text, boolean isCanMove, boolean isCanChangeRecycler) {
        this.text = text;
        this.isCanMove = isCanMove;
        this.isCanChangeRecycler = isCanChangeRecycler;
    }

    public TextDragBean(String text, boolean isCanMove, boolean isCanChangeRecycler, boolean isCanDrag) {
        this.text = text;
        this.isCanMove = isCanMove;
        this.isCanChangeRecycler = isCanChangeRecycler;
        this.isCanDrag = isCanDrag;
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

    @Override
    public boolean isCanMove() {
        return isCanMove;
    }

    public void setCanMove(boolean canMove) {
        isCanMove = canMove;
    }

    @Override
    public boolean isCanChangeRecycler() {
        return isCanChangeRecycler;
    }

    public void setCanChangeRecycler(boolean canChangeRecycler) {
        isCanChangeRecycler = canChangeRecycler;
    }

    @Override
    public boolean isCanDrag() {
        return isCanDrag;
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
    }
}
