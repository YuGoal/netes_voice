package io.yugoal.lib_share.share;

import android.content.Context;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author caoyu
 * date  2019/10/25
 * 分享管理类
 */
public class ShareManager {
    private static final ShareManager ourInstance = new ShareManager();

    public static ShareManager getInstance() {
        return ourInstance;
    }

    private ShareManager() {
    }
    /**
     * 要分享到的平台
     */
    private Platform mCurrentPlatform;


    /**
     * 第一个执行的方法,最好在程序入口入执行
     *
     * @param context
     */
    public static void initSDK(Context context) {
        ShareSDK.initSDK(context);
    }

    /**
     * 分享数据到不同平台
     */
    public void shareData(ShareData shareData, PlatformActionListener listener) {
        switch (shareData.mPlatformType) {
            case QQ:
                mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME);
                break;
            case QZone:
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME);
                break;
            case WeChat:
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case WechatMoments:
                mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME);
                break;
            default:
                break;
        }
        mCurrentPlatform.setPlatformActionListener(listener); //由应用层去处理回调,分享平台不关心。
        mCurrentPlatform.share(shareData.mShareParams);
    }
    /**
     * @author 应用程序需要的平台
     */
    public enum PlatofrmType {
        QQ, QZone, WeChat, WechatMoments;
    }

}
