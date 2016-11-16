package com.mpzn.mpzn.fragment.RegForType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseFragment;

/**
 * Created by Icy on 2016/10/20.
 */

public class RegForKFS extends BaseFragment {
    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_regforkfs, null);
        return view;
    }

    @Override
    public void initHolder(View view) {

    }

    @Override
    public void initLayoutParams() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void bindListener() {

    }
    public interface RegbtnEnableSetListener {
        void setRegbtnEnable(boolean enable, String name, int comMid);
    }

}
