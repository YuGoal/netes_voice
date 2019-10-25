package io.yugoal.yu.application;

import android.app.Application;

import io.yugoal.lib_audio.app.AudioHelper;
import io.yugoal.lib_share.share.ShareManager;

public class ImoocVoiceApplication extends Application {

    private static ImoocVoiceApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //音频SDK初始化
        AudioHelper.init(this);
        //分享SDK初始化
        ShareManager.initSDK(this);

    }

    public static ImoocVoiceApplication getInstance() {
        return mApplication;
    }
}
