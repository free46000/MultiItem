package com.freelib.multiitem.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * slide 动画
 * Created by free46000 on 2017/5/22.
 */
public class SlideInRightAnimation implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        };
    }
}
