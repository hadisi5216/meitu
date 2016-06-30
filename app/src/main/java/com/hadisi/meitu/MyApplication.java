package com.hadisi.meitu;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by wugang on 2016/6/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // TODO 您的其他初始化流程
        ApiStoreSDK.init(this, "89d9bfd4e5dbdc21a5aa27a1082a17d4");
        super.onCreate();
    }
}
