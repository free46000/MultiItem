package com.freelib.multiitem.animation;

import android.animation.Animator;
import android.view.View;

public interface BaseAnimation {

    /**
     * 为View设置动画并返回
     *
     * @param view View
     * @return 动画
     */
    Animator[] getAnimators(View view);

}
