package com.mpzn.mpzn.http;

import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Icy on 2016/9/28.
 */

public abstract class MyStringCallBack extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
