package com.ro0kiey.musicplayer.utils;

import android.content.Context;

/**
 * Created by Ro0kieY on 2017/8/21.
 */

public class DisplayUtil {

    private DisplayUtil() {
    }

    /*手柄起始角度*/
    public static final float ROTATION_INIT_NEEDLE = -30;

    /*截图屏幕宽高*/
    private static final float BASE_SCREEN_WIDTH = (float) 1080.0;
    private static final float BASE_SCREEN_HEIGHT = (float) 1920.0;

    /*唱针宽高、距离等比例*/
    public static final float SCALE_NEEDLE_WIDTH = (float) (303.6 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_MARGIN_LEFT = (float) (476.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_X = (float) (55.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_PIVOT_Y = (float) (55.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_NEEDLE_HEIGHT = (float) (454.3 / BASE_SCREEN_HEIGHT);
    public static final float SCALE_NEEDLE_MARGIN_TOP = (float) (55.0 / BASE_SCREEN_HEIGHT);

    /*唱盘边界透明圆环比例*/
    public static final float SCALE_BORDER_SIZE = (float) (840.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_BORDER_MARGIN_TOP = (float) (187.0 / BASE_SCREEN_HEIGHT);

    /*唱盘比例*/
    public static final float SCALE_ALBUM_SIZE = (float) (813.0 / BASE_SCREEN_WIDTH);
    public static final float SCALE_ALBUM_MARGIN_TOP = (float) (205.0 / BASE_SCREEN_HEIGHT);

    /*专辑图片比例*/
    public static final float SCALE_MUSIC_PIC_SIZE = (float) (533.0 / BASE_SCREEN_WIDTH);

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
