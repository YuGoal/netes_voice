package io.yugoal.lib_audio.app;

import android.content.Context;

import io.yugoal.lib_audio.mediaplayer.db.GreenDaoHelper;

/**
 * @author caoyu
 * date  2019/9/30
 */
public class AudioHelper {
    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        GreenDaoHelper.initDatabase();
    }

    public static Context getContext() {
        return mContext;
    }
}
