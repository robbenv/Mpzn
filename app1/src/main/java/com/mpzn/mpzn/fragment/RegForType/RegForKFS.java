package com.mpzn.mpzn.fragment.RegForType;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/10/20.
 */

public class RegForKFS extends BaseFragment {

    @Bind(R.id.tv_jjcom)
    TextView tvJjcom;
    @Bind(R.id.et_kfsname)
    EditText etKfsname;
    private KfsRegbtnEnableSetListener mRegbtnEnableSetListener;

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
        etKfsname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegbtnEnableSetListener.setRegKfsbtnEnable(true, etKfsname.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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



    public interface KfsRegbtnEnableSetListener {
        void setRegKfsbtnEnable(boolean enable, String name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRegbtnEnableSetListener = (KfsRegbtnEnableSetListener) context;
        } catch (Exception e) {
//            throw new ClassCastException(context.toString()
//                    + "must implement RegbtnEnableSetListener");
        }
    }

}
