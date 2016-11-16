package com.mpzn.mpzn.fragment.RegForType;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.JJcomListForChooseAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.base.BaseFragment;
import com.mpzn.mpzn.entity.JJComlistForChooseEntity;
import com.mpzn.mpzn.http.API;
import com.mpzn.mpzn.views.DeleteableCardView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/10/20.
 */

public class RegForJJR extends BaseFragment {
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_jjcom)
    TextView tvJjcom;
    @Bind(R.id.sp_jjcom)
    EditText spJjcom;
    @Bind(R.id.tv_com_name)
    DeleteableCardView tvComName;
    @Bind(R.id.btn_choose)
    Button btnChoose;
    @Bind(R.id.et_name)
    EditText etName;

    private RegbtnEnableSetListener mRegbtnEnableSetListener;


    private List<JJComlistForChooseEntity.DataBean> jjComlistForChooseEntityData = new ArrayList<>();
    private JJcomListForChooseAdapter jJcomListForChooseAdapter;
    private PopupWindow searchListPopupWindow;
    private ListView listView;
    private JJComlistForChooseEntity.DataBean selectedData = new JJComlistForChooseEntity.DataBean();


    @Override
    public View getLayourView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_regforjjr, null);
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
        listView = new ListView(mContext);
        listView.setBackgroundResource(R.color.white);
        jJcomListForChooseAdapter = new JJcomListForChooseAdapter(mContext, jjComlistForChooseEntityData);
        listView.setAdapter(jJcomListForChooseAdapter);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setPadding(dip2px(5), 0, dip2px(5), 0);
        listView.setLayoutParams(layoutParams);

        searchListPopupWindow = new PopupWindow(mContext);
        ColorDrawable dw = new ColorDrawable(0xffffff);
        searchListPopupWindow.setBackgroundDrawable(dw);
        searchListPopupWindow.setContentView(listView);

    }

    @Override
    public void bindListener() {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString().trim();
                if (tvComName.getVisibility()==View.VISIBLE&&selectedData.getMid()!=0) {
                    mRegbtnEnableSetListener.setRegbtnEnable(true,name,selectedData.getMid());
                }else{
                    mRegbtnEnableSetListener.setRegbtnEnable(false,null,0);

                }

            }
        });
        tvComName.setBtnDeleteOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvComName.setVisibility(View.GONE);
                spJjcom.setEnabled(true);
                spJjcom.setText(tvComName.getText());
                spJjcom.requestFocus();
                spJjcom.setSelection(tvComName.getText().toString().length());
            }
        });
        spJjcom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRegbtnEnableSetListener.setRegbtnEnable(tvComName.getVisibility()==View.VISIBLE,null,0);
                    int width = MyApplication.mScreenWidth - dip2px(20);
                    searchListPopupWindow.setWidth(width);
//                    int measuredWidth = v.getMeasuredWidth();
//                    searchListPopupWindow.setWidth(measuredWidth);
                    searchListPopupWindow.setHeight(dip2px(200));
                } else {
                    searchListPopupWindow.dismiss();

                }
            }
        });

        spJjcom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    jJcomListForChooseAdapter.clearAll();
                    jJcomListForChooseAdapter.notifyDataSetChanged();
                    searchListPopupWindow.dismiss();
                    return;
                }else{
                    searchListPopupWindow.showAsDropDown(spJjcom, -dip2px(85), dip2px(10));

                }

                OkHttpUtils.get()
                        .url(API.JJCOM_LIST_GET)
                        .addParams("keywords", s.toString().trim())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                JJComlistForChooseEntity jjComlistForChooseEntity = new Gson().fromJson(response, JJComlistForChooseEntity.class);
                                if (jjComlistForChooseEntity.getCode() == 200) {
                                    jjComlistForChooseEntityData = jjComlistForChooseEntity.getData();
                                    jJcomListForChooseAdapter.updata(jjComlistForChooseEntityData);
                                }
                            }
                        });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<JJComlistForChooseEntity.DataBean> data = jJcomListForChooseAdapter.getData();
                selectedData = data.get(position);
                if (tvComName.getVisibility() == View.GONE) {
                    tvComName.setVisibility(View.VISIBLE);
                    spJjcom.setText("");
                    spJjcom.clearFocus();
                    spJjcom.setEnabled(false);
                    etName.requestFocus();
                }
                tvComName.setText(selectedData.getCompany_name());
                String name = etName.getText().toString();
                if (name.length()!=0) {
                    mRegbtnEnableSetListener.setRegbtnEnable(true,name,selectedData.getMid());
                }else{
                    mRegbtnEnableSetListener.setRegbtnEnable(false,null,0);
                }
            }
        });


    }



    public interface RegbtnEnableSetListener {
        void setRegbtnEnable(boolean enable, String name, int comMid);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRegbtnEnableSetListener = (RegbtnEnableSetListener) context;
        } catch (Exception e) {
//            throw new ClassCastException(context.toString()
//                    + "must implement RegbtnEnableSetListener");
        }
    }



}
