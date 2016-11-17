package com.mpzn.mpzn.fragment.RegForType;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/10/20.
 */

public class RegForJJCom extends BaseFragment {
    @Bind(R.id.et_jjcom)
    EditText etJjcom;

    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_regforjjcom, null);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
