package com.ro0kiey.musicplayer.model;

/**
 * Created by Ro0kieY on 2017/8/22.
 */

public class Music {

        /*音乐资源id*/
        private int mMusicRes;
        /*专辑图片id*/
        private int mMusicPicRes;
        /*音乐名称*/
        private String mMusicName;
        /*作者*/
        private String mMusicAuthor;

        public Music(int mMusicRes, int mMusicPicRes, String mMusicName, String mMusicAuthor) {
            this.mMusicRes = mMusicRes;
            this.mMusicPicRes = mMusicPicRes;
            this.mMusicName = mMusicName;
            this.mMusicAuthor = mMusicAuthor;
        }

        public int getMusicRes() {
            return mMusicRes;
        }

        public int getMusicPicRes() {
            return mMusicPicRes;
        }

        public String getMusicName() {
            return mMusicName;
        }

        public String getMusicAuthor() {
            return mMusicAuthor;
        }
}
