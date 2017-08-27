package com.ro0kiey.musicplayer.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ro0kiey.musicplayer.R;
import com.ro0kiey.musicplayer.adapter.MyPagerAdapter;
import com.ro0kiey.musicplayer.fragment.FriendsFragment;
import com.ro0kiey.musicplayer.fragment.MusicFragment;
import com.ro0kiey.musicplayer.fragment.NetFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private ImageView mIvHome;
    private ImageView mIvNet;
    private ImageView mIvMusic;
    private ImageView mIvFriends;
    private ImageView mIvSearch;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private long lastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolBar();
        initViewPager();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mIvHome = (ImageView)findViewById(R.id.toolbar_home);
        mIvMusic = (ImageView)findViewById(R.id.toolbar_music);
        mIvNet = (ImageView)findViewById(R.id.toolbar_net);
        mIvFriends = (ImageView)findViewById(R.id.toolbar_friends);
        mIvSearch = (ImageView)findViewById(R.id.toolbar_search);
        tabs.add(mIvMusic);
        tabs.add(mIvNet);
        tabs.add(mIvFriends);
        mIvHome.setOnClickListener(this);
        mIvMusic.setOnClickListener(this);
        mIvNet.setOnClickListener(this);
        mIvFriends.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_skin:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置Toolbar
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * 设置ViewPager
     */
    private void initViewPager() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        MusicFragment musicFragment = new MusicFragment();
        NetFragment netFragment = new NetFragment();
        FriendsFragment friendsFragment = new FriendsFragment();
        myPagerAdapter.addFragment(musicFragment);
        myPagerAdapter.addFragment(netFragment);
        myPagerAdapter.addFragment(friendsFragment);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++){
            if (position == i){
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.toolbar_music:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.toolbar_net:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.toolbar_friends:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.toolbar_search:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - lastTime > 1000){
                Toast.makeText(this, "双击返回桌面", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            } else {

            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
