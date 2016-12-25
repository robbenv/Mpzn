package com.mpzn.mpzn.base;

/**
 * Created by Icy on 2016/7/13.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mpzn.mpzn.utils.ImageManager;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private AlertDialog progressDialog;

    private AlertDialog dlg;

    public View view;


    public Context mContext;

    public ImageManager mImageManager;

    public LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Logger.d("onCreateView");
        view = getLayourView(inflater, container);
        mInflater=inflater;
        ButterKnife.bind(this, view);
        mContext=getActivity();
        mImageManager = new ImageManager(mContext);
        initHolder(view);
        initLayoutParams();
        initData();
        bindListener();
        return view;
    }

    public abstract View getLayourView(LayoutInflater inflater, ViewGroup container);

    public abstract void initHolder(final View view);

    public abstract void initLayoutParams();

    public abstract void initData();

    public abstract void bindListener();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * 得到根Fragment(多层Fragment跳转的时候需要用这个方法启动activity)
     *
     * @return
     */
    protected Fragment getRootFragment() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return this;
        }
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment;

    }

    /**
     * 短toast
     *
     * @param str
     */
    public void playShortToast(String str) {
        if (null == getActivity() || TextUtils.isEmpty(str))
            return;
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短toast
     *
     * @param res
     */
    public void playShortToast(int res) {
        if (null == getActivity())
            return;
        playShortToast(getResources().getString(res));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Logger.d("onActivityCreated");
    }
}
