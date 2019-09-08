package io.yugoal.lib_network.okhttp.response;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author caoyu
 * date  2019/9/7
 * 专门处理JSON的回调
 */
public class CommonJsonCallback implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
