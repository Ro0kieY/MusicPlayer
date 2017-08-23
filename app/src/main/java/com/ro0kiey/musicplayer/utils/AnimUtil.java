package com.ro0kiey.musicplayer.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ViewAnimator;

/**
 * Created by Ro0kieY on 2017/8/23.
 */

public class AnimUtil {

    private AnimUtil() {
    }

    /**
     * 唱针滑离唱片
     * @param target
     */
    public static void switchOffNeedle(ImageView target){
        ObjectAnimator.ofFloat(target, "Rotation", 0, DisplayUtil.ROTATION_INIT_NEEDLE).setDuration(400).start();
    }

    /**
     * 唱针滑入唱片
     * @param target
     */
    public static void switchOnNeedle(ImageView target){
        ObjectAnimator.ofFloat(target, "Rotation", DisplayUtil.ROTATION_INIT_NEEDLE, 0).setDuration(400).start();
    }

    /**
     * 旋转唱片
     * @param view
     */
    public static void rotateAlbum(View view){
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        rotateAnimation.setDuration(10 * 1000);
        view.startAnimation(rotateAnimation);
    }
}
