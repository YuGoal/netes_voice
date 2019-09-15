package io.yugoal.lib_network.okhttp.listener;

/**
 * @author caoyu
 * date  2019/9/7
 */
public interface DisposeDataListener {
    /**
     * 请求成功回调事件处理
     */
    void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件处理
     */
    void onFailure(Object reasonObj);

}
