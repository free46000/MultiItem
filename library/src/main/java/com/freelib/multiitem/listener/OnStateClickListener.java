package com.freelib.multiitem.listener;

import com.freelib.multiitem.helper.StateViewHelper;
import com.freelib.multiitem.item.BaseItemState;

/**
 * 状态页中的点击Listener
 * Created by free46000 on 2017/4/23.
 *
 * @see BaseItemState
 * @see StateViewHelper
 */
@FunctionalInterface
public interface OnStateClickListener {
    void onStateClick();
}
