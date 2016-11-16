package com.mpzn.mpzn.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpzn.mpzn.R;
import com.mpzn.mpzn.base.BaseActivity;
import com.mpzn.mpzn.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Icy on 2016/10/18.
 */

public class MyDialog extends Dialog {

    private Context context;
    private ViewHolder dialog_view;
    private View.OnClickListener cancleListener;
    private View.OnClickListener commitListener;
    public  Button btnCommit;

    public MyDialog(Context context) {
        this(context, 0);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.layout_white_dialog, null);
        dialog_view = new ViewHolder(contentView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        addContentView(contentView, layoutParams);
        btnCommit=dialog_view.btnCommit;
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initlistener();

    }

    protected void initlistener() {
        cancleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        dialog_view.btnCancel.setOnClickListener(cancleListener);

    }

    public void setCancleListener(View.OnClickListener cancleListener) {
        if (cancleListener != null) {
            this.cancleListener = cancleListener;
            dialog_view.btnCancel.setOnClickListener(cancleListener);
        }
    }

    public void setCommitListener(View.OnClickListener commitListener) {
        if (commitListener != null) {
            this.commitListener = commitListener;
            dialog_view.btnCommit.setOnClickListener(commitListener);
        }
    }

    public void setContent(String content) {
        dialog_view.tvContent.setText(content);
    }

    public void setContentView(View contentView){
        dialog_view.tvContent.setVisibility(View.GONE);
        dialog_view.flContent.addView(contentView);
    }

    public void setContentFragment(BaseFragment fragment) {     //添加内容区域的fragment
        FragmentTransaction transaction = ((BaseActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content,fragment);
        transaction.commit();
    }

    public void setTitle(String title) {
        dialog_view.tvTitle.setText(title);
    }

    public void setCommit(String commit) {
        dialog_view.btnCommit.setText(commit);
    }


    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.btn_cancel)
        Button btnCancel;
        @Bind(R.id.btn_commit)
        Button btnCommit;
        @Bind(R.id.fl_content)
        FrameLayout flContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
