package com.mpzn.mpzn.views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mpzn.mpzn.R;

import static com.mpzn.mpzn.application.MyApplication.mContext;
import static com.mpzn.mpzn.application.MyApplication.mScreenWidth;
import static com.mpzn.mpzn.utils.ViewUtils.dip2px;

/**
 * Created by Icy on 2016/10/18.
 */

public class MyProgressDialog extends Dialog {
    private Context context;
    private TextView tvContent;
    private ProgressBar progressbar;

    private Handler handler = new Handler();

    private Runnable myRunnable= new Runnable() {
        public void run() {
            MyProgressDialog.this.dismiss();
        }
    };

    public MyProgressDialog(Context context) {
        this(context, 0);
    }

    public MyProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public void afterprogress(String msg){
        progressbar.setVisibility(View.GONE);
        dismiss();
        tvContent.setText(msg);
        show();
        handler.postDelayed(myRunnable,1200);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.layout_progress_dialog, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mScreenWidth * 2 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.setLayoutParams(params);


        tvContent = (TextView)contentView.findViewById(R.id.tv_content);
        progressbar = (ProgressBar)contentView.findViewById(R.id.progressBar);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        addContentView(contentView, layoutParams);
        this.setCancelable(false);


    }
    public void setContent(String content){
        tvContent.setText(content);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
    }

}
