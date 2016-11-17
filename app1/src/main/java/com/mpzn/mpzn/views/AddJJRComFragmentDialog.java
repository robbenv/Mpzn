package com.mpzn.mpzn.views;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mpzn.mpzn.R;
import com.mpzn.mpzn.adapter.JJcomListForChooseAdapter;
import com.mpzn.mpzn.application.MyApplication;
import com.mpzn.mpzn.entity.JJComlistForChooseEntity;
import com.mpzn.mpzn.entity.SimpleEntity;
import com.mpzn.mpzn.http.API;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/10/28.
 */

public class AddJJRComFragmentDialog extends DialogFragment {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_jjcom)
    TextView tvJjcom;
    @Bind(R.id.et_jjcom)
    EditText etJjcom;
    @Bind(R.id.tv_com_name)
    DeleteableCardView tvComName;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_commit)
    Button btnCommit;


    private List<JJComlistForChooseEntity.DataBean> jjComlistForChooseEntityData = new ArrayList<>();
    private JJcomListForChooseAdapter jJcomListForChooseAdapter;
    private PopupWindow searchListPopupWindow;
    private ListView listView;
    private JJComlistForChooseEntity.DataBean selectedData = new JJComlistForChooseEntity.DataBean();
    private Context mContext;
    private CommitListener commitListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.layout_addjjrcom_dialog, null);

        ButterKnife.bind(this, view);
        initData();
        bindListener();

        return view;
    }


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


    public void bindListener() {

        tvComName.setBtnDeleteOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvComName.setVisibility(View.GONE);
                etJjcom.setEnabled(true);
                btnCommit.setEnabled(false);
                etJjcom.setText(tvComName.getText());
                etJjcom.requestFocus();
                etJjcom.setSelection(tvComName.getText().toString().length());
            }
        });
        etJjcom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int measuredWidth = v.getMeasuredWidth();
                    searchListPopupWindow.setWidth(measuredWidth);
                    searchListPopupWindow.setHeight(dip2px(200));
                } else {
                    searchListPopupWindow.dismiss();

                }
            }
        });

        etJjcom.addTextChangedListener(new TextWatcher() {
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
                } else {
                    searchListPopupWindow.showAsDropDown(etJjcom,0,-dip2px(20));

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
                    etJjcom.setText("");
                    etJjcom.clearFocus();
                    etJjcom.setEnabled(false);
                    etJjcom.requestFocus();
                }
                tvComName.setText(selectedData.getCompany_name());
               btnCommit.setEnabled(true);

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCommit.setEnabled(false);
                OkHttpUtils.post()
                        .url(API.APPLYJJCOM_POST)
                        .addParams("token",MyApplication.getInstance().token)
                        .addParams("agent_id",selectedData.getMid()+"")
                        .addParams("action","add")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(mContext, "服务器未响应", Toast.LENGTH_SHORT).show();
                                btnCommit.setEnabled(true);
                            }


                            @Override
                            public void onResponse(String response, int id) {
                                SimpleEntity simpleEntity = new Gson().fromJson(response, SimpleEntity.class);

                                if(simpleEntity.getCode()==200){
                                    Toast.makeText(mContext, "申请成功", Toast.LENGTH_SHORT).show();
                                    commitListener.setCommitListner(true,selectedData.getCompany_name());
                                    dismiss();
                                }else{
                                    Toast.makeText(mContext, simpleEntity.getMessage(), Toast.LENGTH_SHORT).show();
                                    btnCommit.setEnabled(true);
                                }


                            }
                        });
            }
        });


    }
    public void setCommitListener(CommitListener commitListener){
        if(commitListener!=null){
            this.commitListener=commitListener;

        }

    }
    public interface CommitListener{
        void setCommitListner(boolean isSuccess,String ComName);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }




}
