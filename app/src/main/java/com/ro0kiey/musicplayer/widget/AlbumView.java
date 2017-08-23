package com.ro0kiey.musicplayer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ro0kiey.musicplayer.R;
import com.ro0kiey.musicplayer.adapter.ViewPagerAdapter;
import com.ro0kiey.musicplayer.listener.MusicChangedListener;
import com.ro0kiey.musicplayer.model.Music;
import com.ro0kiey.musicplayer.utils.AnimUtil;
import com.ro0kiey.musicplayer.utils.DisplayUtil;
import com.ro0kiey.musicplayer.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ro0kieY on 2017/8/21.
 */

public class AlbumView extends RelativeLayout {

    private static final String TAG = "Album";
    private ImageView mIvNeedle;
    private ViewPager mVpAlbum;
    private ImageView mIvBorder;

    private List<View> mAlbumList = new ArrayList<>();
    private List<Music> mMusicList = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;
    private MusicChangedListener musicChangedListener;

    private boolean isNeedleOn = false;
    private int mScreenWidth;
    private int mScreenHeight;

    public AlbumView(Context context) {
        this(context, null);
    }

    public AlbumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScreenWidth = DisplayUtil.getScreenWidth(context);
        mScreenHeight = DisplayUtil.getScreenHeight(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initBorder();
        initAlbum();
        initNeedle();
    }

    /**
     * 初始化唱片边界透明圆环
     */
    private void initBorder() {
        mIvBorder = (ImageView)findViewById(R.id.iv_border);

        int borderSize = (int) (DisplayUtil.SCALE_BORDER_SIZE * mScreenWidth);
        int borderMarginTop = (int) (DisplayUtil.SCALE_BORDER_MARGIN_TOP * mScreenHeight);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_border);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, borderSize, borderSize, false);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), scaledBitmap);

        RelativeLayout.LayoutParams params = (LayoutParams) mIvBorder.getLayoutParams();
        params.setMargins(0, borderMarginTop, 0, 0);

        mIvBorder.setImageDrawable(drawable);
    }

    /**
     * 初始化唱片ViewPager,内部是一个ImageView
     */
    private void initAlbum() {
        mVpAlbum = (ViewPager)findViewById(R.id.vp_album);
        mViewPagerAdapter = new ViewPagerAdapter(mAlbumList);

        //添加页面切换监听
        mVpAlbum.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;
            int lastPositionOffsetPixels = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled");
                if (isNeedleOn){
                    AnimUtil.switchOffNeedle(mIvNeedle);
                    isNeedleOn = false;
                }
                //左滑
                if (lastPositionOffsetPixels > positionOffsetPixels){
                    if (positionOffset > 0.5){
                        notifyItemChanged(position - 1);
                    } else {
                        notifyItemChanged(position);
                    }
                } else
                    //右滑
                    if (lastPositionOffsetPixels < positionOffsetPixels){
                        if (positionOffset > 0.5){
                            notifyItemChanged(position + 1);
                        } else {
                            notifyItemChanged(position);
                    }
                }
                lastPositionOffsetPixels = positionOffsetPixels;
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d(TAG, "onPageScrollStateChanged : IDLE ");
                        if (!isNeedleOn){
                            AnimUtil.switchOnNeedle(mIvNeedle);
                            AnimUtil.rotateAlbum(mVpAlbum);
                            isNeedleOn = true;
                        }
                        if (musicChangedListener != null){
                            musicChangedListener.onMusicChanged(mMusicList.get(currentPosition).getMusicPicRes());
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.d(TAG, "onPageScrollStateChanged : DRAGGING ");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.d(TAG, "onPageScrollStateChanged : SETTLING");
                        break;
                    default:
                        break;
                }
            }
        });

        mVpAlbum.setAdapter(mViewPagerAdapter);

        //定位唱片位置
        RelativeLayout.LayoutParams params = (LayoutParams) mVpAlbum.getLayoutParams();
        int marginTop = (int) (DisplayUtil.SCALE_ALBUM_MARGIN_TOP * mScreenHeight);
        params.setMargins(0, marginTop, 0, 0);

        mVpAlbum.setLayoutParams(params);

        int top = mVpAlbum.getTop();
        mVpAlbum.setPivotX(mScreenWidth / 2);
        mVpAlbum.setPivotY(mScreenHeight / 2);
    }

    /**
     * 初始化唱针
     */
    private void initNeedle() {

        mIvNeedle = (ImageView)findViewById(R.id.iv_needle);

        int needleWidth = (int) (DisplayUtil.SCALE_NEEDLE_WIDTH * mScreenWidth);
        int needleHeight = (int) (DisplayUtil.SCALE_NEEDLE_HEIGHT * mScreenHeight);
        int marginLeft = (int) (DisplayUtil.SCALE_NEEDLE_MARGIN_LEFT * mScreenWidth);
        int marginTop = (int) (DisplayUtil.SCALE_NEEDLE_MARGIN_TOP * mScreenHeight * (-1));
        int pivotX = (int) (DisplayUtil.SCALE_NEEDLE_PIVOT_X * mScreenWidth);
        int pivotY = (int) (DisplayUtil.SCALE_NEEDLE_PIVOT_Y * mScreenWidth);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_needle);
        //根据屏幕尺寸缩放
        Bitmap bitmap = Bitmap.createScaledBitmap(originBitmap, needleWidth, needleHeight, true);

        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mIvNeedle.getLayoutParams();
        //设置margin，定位至准确位置
        layoutParams.setMargins(marginLeft, marginTop, 0 ,0);

        mIvNeedle.setImageBitmap(bitmap);
        mIvNeedle.setLayoutParams(layoutParams);
        mIvNeedle.setRotation(DisplayUtil.ROTATION_INIT_NEEDLE);

        //设置旋转中心
        mIvNeedle.setPivotX(pivotX);
        mIvNeedle.setPivotY(pivotY);
    }


    private void notifyItemChanged(int position) {

    }

    public void setMusicDataList(List<Music> musicList){
        if (musicList == null){
            return;
        }
        mAlbumList.clear();
        mMusicList.clear();
        mMusicList.addAll(musicList);
        for (Music music : mMusicList){
            View album = LayoutInflater.from(getContext()).inflate(R.layout.layout_album_vpitem, mVpAlbum, false);
            ImageView ivAlbum = (ImageView)album.findViewById(R.id.iv_album);
            ivAlbum.setImageDrawable(getAlbumDrawable(music.getMusicPicRes()));
            mAlbumList.add(album);
        }

        mViewPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 通过RoundedBitamapDrawable和LayerDrawable合成唱片VDrawable
     * @param musicPicRes
     * @return
     */
    private Drawable getAlbumDrawable(int musicPicRes) {

        int albumSize = (int) (DisplayUtil.SCALE_ALBUM_SIZE * mScreenWidth);
        int musicPicSize = (int) (DisplayUtil.SCALE_MUSIC_PIC_SIZE * mScreenWidth);

        Bitmap albumBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_album);
        Bitmap scaledAlbumBitmap = Bitmap.createScaledBitmap(albumBitmap, albumSize, albumSize, false);
        Bitmap musicPicBitmap = ImageUtil.decodeSampledBitmapFromResource(getResources(), musicPicRes, musicPicSize, musicPicSize);
        Bitmap scaledMusicPicBitmap = Bitmap.createScaledBitmap(musicPicBitmap,musicPicSize, musicPicSize, false);

        BitmapDrawable bdAlbumPic = new BitmapDrawable(getResources(), scaledAlbumBitmap);
        RoundedBitmapDrawable rbdMusicPic = RoundedBitmapDrawableFactory.create(getResources(), scaledMusicPicBitmap);
        rbdMusicPic.setCircular(true);

        bdAlbumPic.setAntiAlias(true);
        rbdMusicPic.setAntiAlias(true);

        Drawable[] drawables = new Drawable[2];
        drawables[0] = rbdMusicPic;
        drawables[1] = bdAlbumPic;

        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        int musicPicMargin = (int) ((DisplayUtil.SCALE_ALBUM_SIZE - DisplayUtil
                .SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2);
        //调整专辑图片的四周边距，让其显示在正中
        layerDrawable.setLayerInset(0, musicPicMargin, musicPicMargin, musicPicMargin,
                musicPicMargin);

        return layerDrawable;
    }

    public void setMusicChangedListener(MusicChangedListener listener){
        this.musicChangedListener = listener;
        musicChangedListener.onMusicChanged(mMusicList.get(0).getMusicPicRes());
    }

}
