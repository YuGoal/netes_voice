package io.yugoal.lib_network.okhttp.listener;

/**
 * @author caoyu
 * date  2019/9/9
 */
public interface DisposeDownloadListener extends DisposeDataListener {
    void onProgress(int progress);
}
