package com.freelib.multiitem.animation;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;


/**
 * 动画Loader，处理动画逻辑
 * Created by free46000 on 2017/5/22.
 */
public class AnimationLoader {
    protected int lastAnimIndex = -1;
    protected boolean isAnimEnable;
    protected boolean isShowAnimWhenFirst;
    protected BaseAnimation animation;
    protected long animDuration = 400L;
    protected Interpolator interpolator = new LinearInterpolator();


    public void clear() {
        lastAnimIndex = -1;
    }

    /**
     * 打开加载动画
     *
     * @param animation               BaseAnimation
     * @param isShowAnimWhenFirstLoad 是否只有在第一次展示的时候才使用动画
     */
    public void enableLoadAnimation(@NonNull BaseAnimation animation, boolean isShowAnimWhenFirstLoad) {
        this.isAnimEnable = true;
        this.isShowAnimWhenFirst = isShowAnimWhenFirstLoad;
        this.animation = animation == null ? new SlideInLeftAnimation() : animation;
    }


    /**
     * 根据条件开启动画
     *
     * @param holder BaseViewHolder
     */
    public void startAnimation(@NonNull BaseViewHolder holder) {
        //如果动画开关关闭则返回
        if (!isAnimEnable) {
            return;
        }

        //判断是否初次展示的时候展示动画
        if (!isShowAnimWhenFirst || holder.getItemPosition() > lastAnimIndex) {
            for (Animator anim : animation.getAnimators(holder.itemView)) {
                startAnim(anim);
            }
            lastAnimIndex = holder.getItemPosition();
        }
    }

    /**
     * 开启动画
     *
     * @param anim 动画
     */
    protected void startAnim(Animator anim) {
        anim.setDuration(animDuration).start();
        anim.setInterpolator(interpolator);
    }

    /**
     * 设置动画时长 默认400L
     *
     * @param animDuration long
     */
    public void setAnimDuration(long animDuration) {
        this.animDuration = animDuration;
    }

    /**
     * 设置动画加速器 默认{@link LinearInterpolator}
     *
     * @param interpolator Interpolator
     */
    public void setInterpolator(@NonNull Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public boolean isAnimEnable() {
        return isAnimEnable;
    }

    public void setAnimEnable(boolean animEnable) {
        isAnimEnable = animEnable;
    }

    public boolean isShowAnimWhenFirst() {
        return isShowAnimWhenFirst;
    }

    public void setShowAnimWhenFirst(boolean showAnimWhenFirst) {
        isShowAnimWhenFirst = showAnimWhenFirst;
    }

    public BaseAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(@NonNull BaseAnimation animation) {
        this.animation = animation;
    }

    public long getAnimDuration() {
        return animDuration;
    }


    public Interpolator getInterpolator() {
        return interpolator;
    }


}
