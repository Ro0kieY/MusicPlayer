package com.ro0kiey.musicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ro0kiey.musicplayer.listener.MusicChangedListener;
import com.ro0kiey.musicplayer.model.Music;
import com.ro0kiey.musicplayer.utils.DisplayUtil;
import com.ro0kiey.musicplayer.utils.ImageUtil;
import com.ro0kiey.musicplayer.widget.AlbumView;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private AlbumView mAlbumView;
    private List<Music> mMusicList = new ArrayList<>();
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        initMusicDatas();

        mLinearLayout = (LinearLayout)findViewById(R.id.play_layout);
        mAlbumView = (AlbumView)findViewById(R.id.album_view);
        mAlbumView.setMusicDataList(mMusicList);
        mAlbumView.setMusicChangedListener(new MusicChangedListener() {
            @Override
            public void onMusicChanged(int musicPicRes) {
                Drawable drawable = getBluredBackground(musicPicRes);
                mLinearLayout.setBackground(drawable);
            }
        });
    }

    private Drawable getBluredBackground(int musicPicRes) {
        float widthHeightRatio = (float) (DisplayUtil.getScreenWidth(this) * 1.0 / DisplayUtil.getScreenHeight(this) * 1.0);

        Bitmap bitmap = getBackgroundBitmap(musicPicRes);
        int cropBitmapWidth = (int) (widthHeightRatio * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);
        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth, bitmap.getHeight());
        /*模糊化*/
        final Bitmap bluredBitmap = ImageUtil.fastblur(cropBitmap, 0.02f , 8);
        Drawable bluredDrawable = new BitmapDrawable(getResources(), bluredBitmap);
        return bluredDrawable;

    }

    private Bitmap getBackgroundBitmap(int musicPicRes) {
        int screenWidth = DisplayUtil.getScreenWidth(this);
        int screenHeight = DisplayUtil.getScreenHeight(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / DisplayUtil.getScreenWidth(this);
        int sampleY = imageHeight / DisplayUtil.getScreenHeight(this);

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), musicPicRes, options);
    }

    private void initMusicDatas() {
        Music musicData1 = new Music(R.raw.music1, R.drawable.ic_music1, "寻", "三亩地");
        Music musicData2 = new Music(R.raw.music1, R.drawable.ic_music2, "Nightingale", "YANI");
        Music musicData3 = new Music(R.raw.music1, R.drawable.ic_music3, "Cornfield Chase", "Hans Zimmer");

        mMusicList.add(musicData1);
        mMusicList.add(musicData2);
        mMusicList.add(musicData3);

    }
}