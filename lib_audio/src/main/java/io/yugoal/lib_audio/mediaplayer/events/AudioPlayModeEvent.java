package io.yugoal.lib_audio.mediaplayer.events;

import io.yugoal.lib_audio.mediaplayer.core.AudioController;

/**
 * @author caoyu
 * date  2019/10/5
 */
public class AudioPlayModeEvent {
    public AudioController.PlayMode mPlayMode;

    public AudioPlayModeEvent(AudioController.PlayMode playMode) {
        this.mPlayMode = playMode;
    }
}
