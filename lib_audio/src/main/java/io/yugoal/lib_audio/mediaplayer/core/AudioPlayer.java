package io.yugoal.lib_audio.mediaplayer.core;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.yugoal.lib_audio.mediaplayer.app.AudioHelper;
import io.yugoal.lib_audio.mediaplayer.events.AudioCompleteEvent;
import io.yugoal.lib_audio.mediaplayer.events.AudioErrorEvent;
import io.yugoal.lib_audio.mediaplayer.events.AudioLoadEvent;
import io.yugoal.lib_audio.mediaplayer.events.AudioPauseEvent;
import io.yugoal.lib_audio.mediaplayer.events.AudioReleaseEvent;
import io.yugoal.lib_audio.mediaplayer.events.AudioStartEvent;
import io.yugoal.lib_audio.mediaplayer.model.AudioBean;

/**
 * @author caoyu
 * date  2019/9/30
 * 播放音频
 * 对外发送各种事件
 */
public class AudioPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, AudioFocusManager.AudioFocusListener {
    private static final String TAG = "AudioPlayer";
    private static final int TIME_MSG = 0x01;
    private static final int TIME_INVAL = 100;

    //真正负责播放的核心MediaPlayer子类
    private CustomMediaPlayer mMediaPlayer;
    private WifiManager.WifiLock mWifiLock;

    //音频焦点监听器
    private AudioFocusManager mAudioFocusManager;
    private boolean isPausedByFocusLossTransient;
    //播放进度更新handler
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_MSG:
                    break;
            }
        }
    };

    public AudioPlayer() {
        init();
    }

    private void init() {
        mMediaPlayer = new CustomMediaPlayer();
        // 使用唤醒锁
        mMediaPlayer.setWakeMode(AudioHelper.getContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnErrorListener(this);

        // 初始化wifi锁
        mWifiLock = ((WifiManager) AudioHelper.getContext()
                .getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, TAG);
        // 初始化音频焦点管理器
        mAudioFocusManager = new AudioFocusManager(AudioHelper.getContext(), this);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        EventBus.getDefault().post(new AudioCompleteEvent());
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        EventBus.getDefault().post(new AudioErrorEvent());
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        start();
    }

    public void setVolumn(float left, float right) {
        if (mMediaPlayer != null) mMediaPlayer.setVolume(left, right);
    }

    @Override
    public void audioFocusGrant() {
        //再次获取音频焦点
        setVolumn(1.0f, 1.0f);
        if (isPausedByFocusLossTransient) {
            resume();
        }
        isPausedByFocusLossTransient = false;
    }

    @Override
    public void audioFocusLoss() {
        //永久失去焦点
        pause();
    }

    @Override
    public void audioFocusLossTransient() {
        //短暂失去焦点
        pause();
        isPausedByFocusLossTransient = true;
    }

    @Override
    public void audioFocusLossDuck() {
        //瞬间失去焦点
        setVolumn(0.5f, 0.5f);
    }


    /**
     * 提供给外部加载
     *
     * @param audioBean
     */
    public void load(AudioBean audioBean) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(audioBean.getMUrl());
            mMediaPlayer.prepareAsync();
            //发送加载音频事件，UI类型处理事件
            EventBus.getDefault().post(new AudioLoadEvent(audioBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        // 获取音频焦点,保证我们的播放器顺利播放
        if (!mAudioFocusManager.requestAudioFocus()) {
            Log.e(TAG, "获取音频焦点失败");
        }
        mMediaPlayer.start();
        // 启用wifi锁
        mWifiLock.acquire();
        //更新进度
        handler.sendEmptyMessage(TIME_MSG);
        //发送start事件，UI类型处理事件
        EventBus.getDefault().post(new AudioStartEvent());
    }

    /**
     * 对外提供的播放方法
     */
    public void resume() {
        if (CustomMediaPlayer.Status.STOPPED == getStatus()) {
            start();
        }
    }

    /**
     * 对外暴露pause方法
     */
    public void pause() {
        if (CustomMediaPlayer.Status.STARTED == getStatus()) {
            mMediaPlayer.pause();
            //关闭wifi锁
            if (mWifiLock.isHeld()) {
                mWifiLock.release();
            }
            //取消音频焦点
            if (mAudioFocusManager.requestAudioFocus()) {
                mAudioFocusManager.abandonAudioFocus();
            }
            //停止发送进度消息
            //mHandler.removeCallbacksAndMessages(null);
            //发送暂停事件,UI类型事件
            EventBus.getDefault().post(new AudioPauseEvent());
        }
    }

    /**
     * 销毁唯一mediaplayer实例,只有在退出app时使用
     */
    public void release() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.release();
        mMediaPlayer = null;
        // 取消音频焦点
        if (mAudioFocusManager != null) {
            mAudioFocusManager.abandonAudioFocus();
        }
        if (mWifiLock.isHeld()) {
            mWifiLock.release();
        }
        mWifiLock = null;
        mAudioFocusManager = null;
        handler.removeCallbacksAndMessages(null);
        //发送销毁播放器事件,清除通知等
        EventBus.getDefault().post(new AudioReleaseEvent());
    }

    /**
     * 获取播放器状态
     *
     * @return 播放器状态
     */
    private CustomMediaPlayer.Status getStatus() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getmState();
        }
        return CustomMediaPlayer.Status.STOPPED;
    }
}
