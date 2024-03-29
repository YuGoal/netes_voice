package io.yugoal.yu.model.friend;

import java.util.ArrayList;

import io.yugoal.lib_audio.mediaplayer.model.AudioBean;
import io.yugoal.yu.model.BaseModel;

/**
 * @文件描述：朋友实体
 */
public class FriendBodyValue extends BaseModel {

    public int type;
    public String avatr;
    public String name;
    public String fans;
    public String text;
    public ArrayList<String> pics;
    public String videoUrl;
    public String zan;
    public String msg;
    public AudioBean audioBean;
}
