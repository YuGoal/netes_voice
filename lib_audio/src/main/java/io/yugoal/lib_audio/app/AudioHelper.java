package io.yugoal.lib_audio.app;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import io.yugoal.lib_audio.mediaplayer.core.AudioController;
import io.yugoal.lib_audio.mediaplayer.core.MusicService;
import io.yugoal.lib_audio.mediaplayer.db.GreenDaoHelper;
import io.yugoal.lib_audio.mediaplayer.model.AudioBean;
import io.yugoal.lib_audio.view.MusicPlayerActivity;

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

    //外部启动MusicService方法
    public static void startMusicService(ArrayList<AudioBean> audios) {
        MusicService.startMusicService(audios);
    }

    public static void addAudio(Activity activity, AudioBean bean) {
        AudioController.getInstance().addAudio(bean);
//        MusicPlayerActivity.start(activity);
    }

    public static void pauseAudio() {
        AudioController.getInstance().pause();
    }

    public static void resumeAudio() {
        AudioController.getInstance().resume();
    }

    public static Context getContext() {
        return mContext;
    }
}
