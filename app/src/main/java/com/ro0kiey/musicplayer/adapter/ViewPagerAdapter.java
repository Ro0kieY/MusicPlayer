package com.ro0kiey.musicplayer.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ro0kieY on 2017/8/23.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mAlbumList;

    public ViewPagerAdapter(List<View> mAlbumList) {
        this.mAlbumList = mAlbumList;
    }

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
