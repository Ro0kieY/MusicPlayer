package com.ro0kiey.musicplayer.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ro0kiey.musicplayer.utils.DisplayUtil;

/**
 * Created by Ro0kieY on 2017/8/24.
 */

public class RotateView extends android.support.v7.widget.AppCompatImageView {

    private static final long ROTATION_ANIMATION_DURATION = 25 * 1000;
    private static final long NEEDLE_ANIMATION_DURATION = 400;
    /*手柄起始角度*/
    public static final float ROTATION_INIT_NEEDLE = -30;

    public static final int NEEDLE_ON = 3;
    public static final int NEEDLE_OFF = 4;
    public static final int NEEDLE_TO_ON = 5;
    public static final int NEEDLE_TO_OFF = 6;

    private ObjectAnimator rotateAlbumAnimator;
    private ObjectAnimator rotateNeedleAnimator;

    public static int mNeedleStatus = NEEDLE_OFF;

    public RotateView(Context context) {
        this(context, null);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRotateAlbumAnimator();
        initSwitchONNeedleAnimator();
    }

    /**
     * 旋转唱片动画
     * @param imageView
     * @return
     */
    private ObjectAnimator getRotateAlbumAnimator(ImageView imageView){
        rotateAlbumAnimator = ObjectAnimator.ofFloat(imageView, "Rotation", 0, 360);
        rotateAlbumAnimator.setDuration(ROTATION_ANIMATION_DURATION);
        rotateAlbumAnimator.setInterpolator(new LinearInterpolator());
        rotateAlbumAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAlbumAnimator.setRepeatMode(ValueAnimator.RESTART);
        rotateAlbumAnimator.setStartDelay(NEEDLE_ANIMATION_DURATION);
        return rotateAlbumAnimator;
    }

    /**
     * 旋转唱针动画
     * @param imageView
     */
    private ObjectAnimator getSwitchOnNeedleAnimator(ImageView imageView){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "Rotation", ROTATION_INIT_NEEDLE, 0).setDuration(NEEDLE_ANIMATION_DURATION);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mNeedleStatus == NEEDLE_OFF){
                    mNeedleStatus = NEEDLE_TO_ON;
                } else if (mNeedleStatus == NEEDLE_ON){
                    mNeedleStatus = NEEDLE_TO_OFF;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mNeedleStatus == NEEDLE_TO_OFF){
                    mNeedleStatus = NEEDLE_OFF;
                } else if (mNeedleStatus == NEEDLE_TO_ON){
                    mNeedleStatus = NEEDLE_ON;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mNeedleStatus = NEEDLE_TO_OFF;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return objectAnimator;
    }

    /**
     * 初始化唱片动画
     */
    private void initRotateAlbumAnimator() {
        this.rotateAlbumAnimator = getRotateAlbumAnimator(this);
    }

    /**
     * 初始化唱针动画
     */
    private void initSwitchONNeedleAnimator() {
        this.rotateNeedleAnimator = getSwitchOnNeedleAnimator(this);
    }

    public void startNeedleAnim(){
        rotateNeedleAnimator.start();
    }

    public void pauseNeedleAnim(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rotateNeedleAnimator.pause();
        }
    }

    public void endNeedleAnim(){
        rotateNeedleAnimator.end();
    }

    public void reverseNeedleAnim(){
        rotateNeedleAnimator.reverse();
    }

    public void cancelNeedleAnim(){
        rotateNeedleAnimator.cancel();
    }

    public void resumeNeedleAnim(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rotateNeedleAnimator.resume();
        }
    }

    public void startAlbumAnim(){
        rotateAlbumAnimator.start();
    }

    public void pauseAlbumAnim(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rotateAlbumAnimator.pause();
        }
    }

    public void endAlbumAnim(){
        rotateAlbumAnimator.end();
    }

    public void reverseAlbumAnim(){
        rotateAlbumAnimator.reverse();
    }

    public void cancelAlbumAnim(){
        rotateAlbumAnimator.cancel();
    }

    public void resumeAlbumAnim(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rotateAlbumAnimator.resume();
        }
    }
}
