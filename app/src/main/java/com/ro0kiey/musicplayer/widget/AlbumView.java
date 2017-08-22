package com.ro0kiey.musicplayer.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ro0kiey.musicplayer.R;
import com.ro0kiey.musicplayer.listener.MusicChangedListener;
import com.ro0kiey.musicplayer.model.Music;
import com.ro0kiey.musicplayer.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ro0kieY on 2017/8/21.
 */

public class AlbumView extends RelativeLayout {

    private ImageView mIvNeedle;
    private ViewPager mVpAlbum;

    private List<View> mAlbumList = new ArrayList<>();
    private List<Music> mMusicList = new ArrayList<>();
    private ViewPagerAdapter mViewPagerAdapter;
    private MusicChangedListener musicChangedListener;

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

        initAlbum();
        initNeedle();
    }

    private void initAlbum() {
        mVpAlbum = (ViewPager)findViewById(R.id.vp_album);
        mViewPagerAdapter = new ViewPagerAdapter();

        mVpAlbum.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentItem = 0;
            int lastPositionOffsetPixels = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //左滑
                if (lastPositionOffsetPixels > positionOffsetPixels){
                    if (positionOffset > 0.5){
                        notifyItemChanged(position - 1);
                    } else {
                        notifyItemChanged(position);
                    }
                } else if (lastPositionOffsetPixels < positionOffsetPixels){
                    if (positionOffset > 0.5){
                        notifyItemChanged(position + 1);
                    } else {
                        notifyItemChanged(position);
                    }
                }
                lastPositionOffsetPixels = positionOffsetPixels;
                if (musicChangedListener != null){
                    musicChangedListener.onMusicChanged(mMusicList.get(position).getMusicPicRes());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mVpAlbum.setAdapter(mViewPagerAdapter);

        RelativeLayout.LayoutParams params = (LayoutParams) mVpAlbum.getLayoutParams();
        int marginTop = (int) (DisplayUtil.SCALE_ALBUM_MARGIN_TOP * mScreenHeight);
        params.setMargins(0, marginTop, 0, 0);
        mVpAlbum.setLayoutParams(params);
    }

    private void notifyItemChanged(int position) {

    }

    private void initNeedle() {

        mIvNeedle = (ImageView)findViewById(R.id.iv_needle);

        int needleWidth = (int) (DisplayUtil.SCALE_NEEDLE_WIDTH * mScreenWidth);
        int needleHeight = (int) (DisplayUtil.SCALE_NEEDLE_HEIGHT * mScreenHeight);
        int marginLeft = (int) (DisplayUtil.SCALE_NEEDLE_MARGIN_LEFT * mScreenWidth);
        int marginTop = (int) (DisplayUtil.SCALE_NEEDLE_MARGIN_TOP * mScreenHeight * (-1));
        int pivotX = (int) (DisplayUtil.SCALE_NEEDLE_PIVOT_X * mScreenWidth);
        int pivotY = (int) (DisplayUtil.SCALE_NEEDLE_PIVOT_Y * mScreenHeight);

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

    public void setMusicDataList(List<Music> musicList){
        if (musicList == null){
            return;
        }
        mAlbumList.clear();
        mMusicList.clear();
        mMusicList.addAll(musicList);
        for (Music music : mMusicList){
            View album = LayoutInflater.from(getContext()).inflate(R.layout.layout_album, mVpAlbum, false);
            ImageView ivAlbum = (ImageView)album.findViewById(R.id.iv_album);
            ivAlbum.setImageDrawable(getAlbumDrawable(music.getMusicPicRes()));
            mAlbumList.add(album);
        }

        mViewPagerAdapter.notifyDataSetChanged();
    }

    private Drawable getAlbumDrawable(int musicPicRes) {

        int albumSize = (int) (DisplayUtil.SCALE_ALBUM_SIZE * mScreenWidth);
        int musicPicSize = (int) (DisplayUtil.SCALE_MUSIC_PIC_SIZE * mScreenWidth);

        Bitmap albumBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_album);
        Bitmap scaledAlbumBitmap = Bitmap.createScaledBitmap(albumBitmap, albumSize, albumSize, false);
        Bitmap scaledMusicPicBitmap = getMusicPicBitmap(musicPicSize, musicPicRes);

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

    private Bitmap getMusicPicBitmap(int musicPicSize, int musicPicRes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int sample = imageWidth / musicPicSize;
        int dstSample = 1;
        if (sample > dstSample){
            dstSample = sample;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = dstSample;
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), musicPicRes, options), musicPicSize, musicPicSize, true);
    }

    public void setMusicChangedListener(MusicChangedListener listener){
        this.musicChangedListener = listener;
    }

    public class ViewPagerAdapter extends  PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View albumView = mAlbumList.get(position);
            container.addView(albumView);
            return albumView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mAlbumList.get(position));
        }

        @Override
        public int getCount() {
            return mAlbumList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
