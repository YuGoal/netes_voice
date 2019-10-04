package io.yugoal.lib_audio.mediaplayer.app;

import android.content.Context;

/**
 * @author caoyu
 * date  2019/9/30
 */
public class AudioHelper {
    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
