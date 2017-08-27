package com.ro0kiey.musicplayer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ro0kiey.musicplayer.R;

/**
 * 主界面net tab图标的fragment
 */
public class NetFragment extends Fragment {


    public NetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_net, container, false);
    }

}
